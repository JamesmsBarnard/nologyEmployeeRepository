package io.nology.employee.Contract;

import java.time.LocalDate;
import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import io.nology.employee.contract.Factory.ContractFactory;
import io.nology.employee.contract.Factory.ContractFactoryOptions;
import io.nology.employee.contract.entity.Contract;
import io.nology.employee.contracttype.entity.ContractType;
import io.nology.employee.contracttype.factory.ContractTypeFactory;
import io.nology.employee.employee.entity.Employee;
import io.nology.employee.employee.factory.EmployeeFactory;
import io.nology.employee.employee.factory.EmployeeFactoryOptions;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/cleanup.sql", executionPhase=Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
public class ContractE2ETester {
    
    @LocalServerPort 
    private int port;

    private final EmployeeFactory employeeFactory;
    private final ContractFactory contractFactory;
    private final ContractTypeFactory contractTypeFactory;

    @Autowired
    public ContractE2ETester(EmployeeFactory employeeFactory, ContractFactory contractFactory, ContractTypeFactory contractTypeFactory)
    {
        this.employeeFactory = employeeFactory;
        this.contractFactory = contractFactory;
        this.contractTypeFactory = contractTypeFactory;
    }

    @BeforeEach
    void setUp()
    {
        RestAssured.port = port;
    }

    @Test
    public void getAllContracts_whenContractsEmpty_returnsEmpty()
    {
        given().when().get("/contracts")
            .then().statusCode(HttpStatus.OK.value())
            .body("$", hasSize(0));
    }

    @Test
    public void getAllContracts_whenContractsInDB_returnsAllContracts()
    {
        Employee e = this.employeeFactory.create();
        ContractType c = this.contractTypeFactory.create("Permanant");

        for(int i = 0; i < 10; i++)
        {
            this.contractFactory.create(ContractFactoryOptions.builder().contractType(c).employee(e).build());
        }

        given().when().get("/contracts")
            .then().statusCode(HttpStatus.OK.value())
            .body("$", hasSize(10))
            .body(matchesJsonSchemaInClasspath("schemas/contract-list-schema.json"));

    }

    @Test
    public void getContractById_validId_returnsContract()
    {
        Employee e = this.employeeFactory.create(EmployeeFactoryOptions.builder().firstName("firstName").build());
        ContractType c = this.contractTypeFactory.create("Permanant");

        Contract contract = this.contractFactory.create(ContractFactoryOptions.builder().contractType(c).employee(e).build());

        given().when().get("/contracts/" + contract.getId())
            .then().statusCode(HttpStatus.OK.value())
            .body("id", equalTo(contract.getId().intValue()))
            .body(matchesJsonSchemaInClasspath("schemas/contract-schema.json"));
            
    }

    @Test 
    public void getContractById_invalidId_returnsNotFoundException()
    {
        given().when().get("/contracts/" + 1l)
            .then().statusCode(HttpStatus.NOT_FOUND.value())
            .body("status", equalTo(404))
            .body("error", equalTo("Not Found"))
            .body("message", containsString("1"))
            .body(matchesJsonSchemaInClasspath("schemas/api-error-response-schema.json"));
    }

    @Test
    public void updateContract_validData_returnsUpdatedContract()
    {
        Employee e = this.employeeFactory.create(EmployeeFactoryOptions.builder().firstName("firstName").build());
        ContractType c = this.contractTypeFactory.create("Permanant");
        LocalDate sd = LocalDate.of(2020, 1, 1);

        Contract contract = this.contractFactory.create(ContractFactoryOptions.builder().employee(e).contractType(c).startDate(sd).build());

        HashMap<String, String> data = new HashMap<>();
        data.put("startDate", "2020-02-01");

        given().contentType(ContentType.JSON).body(data)
            .patch("/contracts/" +  contract.getId())
            .then().statusCode(HttpStatus.OK.value())
            .body("startDate", equalTo("2020-02-01"))
            .body(matchesJsonSchemaInClasspath("schemas/contract-schema.json"));
    }

    //get employee's contracts

    @Test
    public void getEmployeeContracts_validId_returnsEmployeesContracts()
    {
        Employee e = this.employeeFactory.create(EmployeeFactoryOptions.builder().build());
        ContractType c = this.contractTypeFactory.create("Permanant");

        Contract contract = this.contractFactory.create(ContractFactoryOptions.builder().employee(e).contractType(c).build());


        given().when().get("/contracts/employee/" + e.getId())
            .then().statusCode(HttpStatus.OK.value())
            .body("$", hasSize(1))
            .body("id", hasItem(contract.getId().intValue()))
            .body(matchesJsonSchemaInClasspath("schemas/contract-list-schema.json"));

    }

    @Test
    public void getEmployeeContracts_invalidId_returnsNotFoundException()
    {
        given().when().get("/contracts/employee/1")
            .then().statusCode(HttpStatus.NOT_FOUND.value())
            .body("status", equalTo(404))
            .body("error", equalTo("Not Found"))
            .body("message", containsString("1"))
            .body(matchesJsonSchemaInClasspath("schemas/api-error-response-schema.json"));
    }

    //get active contract

    @Test
    public void getActiveContracts_validContract_returnsContract()
    {
        Employee e = employeeFactory.create();
        ContractType ct = contractTypeFactory.create("contract");

        LocalDate sd = LocalDate.of(2020, 1, 1);

        LocalDate ed = LocalDate.of(LocalDate.now().getYear() + 2, 1, 1);

        Contract contract = contractFactory.create(ContractFactoryOptions.builder()
            .employee(e)
            .contractType(ct)
            .startDate(sd)
            .endDate(ed)
            .build()
        );

        given().when().get("/contracts/active/" + e.getId())
            .then().statusCode(HttpStatus.OK.value())
            .body("contractType.type", equalTo("contract"))
            .body(matchesJsonSchemaInClasspath("schemas/contract-schema.json"));
    }

    @Test
    public void getActiveContracts_invalidEmployee_returnsNotFound()
    {
        given().when().get("/contracts/active/1")
            .then().statusCode(HttpStatus.NOT_FOUND.value())
            .body("status", equalTo(404))
            .body("error", equalTo("Not Found"))
            .body("message", containsString("1"))
            .body(matchesJsonSchemaInClasspath("schemas/api-error-response-schema.json"));
    }

}
