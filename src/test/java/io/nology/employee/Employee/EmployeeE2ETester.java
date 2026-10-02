package io.nology.employee.Employee;

import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

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
public class EmployeeE2ETester {
    
    @LocalServerPort
    private int port;

    private final EmployeeFactory employeeFactory;

    @Autowired
    public EmployeeE2ETester(EmployeeFactory employeeFactory)
    {
        this.employeeFactory = employeeFactory;
    }

    @BeforeEach
    void setUp()
    {
        RestAssured.port = port;
    }

    @Test
    public void getAllEmployees_whenNoEmployees_returnsEmpty()
    {
        given().when().get("/employees")
            .then().statusCode(HttpStatus.OK.value())
            .body("data", hasSize(0))
            .body("totalPages", equalTo(0))
            .body("currentPage", equalTo(1))
            .body("nextPage", nullValue())
            .body("previousPage", nullValue())
            .body("totalResults", equalTo(0))
            .body(matchesJsonSchemaInClasspath("schemas/employee-page-schema.json"));
    }

    @Test
    public void getAllEmployees_whenEmployeesInDB_returnsAllEmployees()
    {
        EmployeeFactoryOptions eOpt1 = EmployeeFactoryOptions.builder().firstName("firstName").build();
        EmployeeFactoryOptions eOpt2 = EmployeeFactoryOptions.builder().firstName("lastName").build();
        
        this.employeeFactory.create(eOpt1);
        this.employeeFactory.create(eOpt2);

        given().when().get("/employees")
            .then().statusCode(HttpStatus.OK.value())
            .body("data", hasSize(2))
            .body("totalPages", equalTo(1))
            .body("currentPage", equalTo(1))
            .body("nextPage", nullValue())
            .body("previousPage", nullValue())
            .body("totalResults", equalTo(2))
            .body(matchesJsonSchemaInClasspath("schemas/employee-page-schema.json"));
    }

    @Test
    public void getById_validId_returnsEmployee()
    {
        EmployeeFactoryOptions options = EmployeeFactoryOptions.builder()
            .firstName("firstName")
            .lastName("lastName")
            .build();
        
        Employee e = this.employeeFactory.create(options);

        Long id = e.getId();

        given().when().get("/employees/" + id)
                .then().statusCode(HttpStatus.OK.value())
                .body("firstName", equalTo("firstName"))
                .body("lastName", equalTo("lastName"))
                .body(matchesJsonSchemaInClasspath("schemas/employee-schema.json"));
    }

    @Test
    public void getById_invalidId_returnsNotFoundException()
    {
        given().when().get("/employees/" + 1l)
                .then().statusCode(HttpStatus.NOT_FOUND.value())
                .body("status", equalTo(404))
                .body("error", equalTo("Not Found"))
                .body("message", containsString("1"))
                .body(matchesJsonSchemaInClasspath("schemas/api-error-response-schema.json"));
    }

    @Test 
    public void updateEmployee_validData_returnsEmployee()
    {
        EmployeeFactoryOptions eOpt1 = EmployeeFactoryOptions.builder().build();
        Employee e = this.employeeFactory.create(eOpt1);

        HashMap<String, String> data = new HashMap<>();
        data.put("firstName", "newName");

        given().contentType(ContentType.JSON).body(data).log().all()
            .patch("/employees/" + e.getId())
            .then().statusCode(HttpStatus.OK.value())
            .body("firstName", equalTo("newName"))
            .body(matchesJsonSchemaInClasspath("schemas/employee-schema.json"));
    }

    @Test 
    public void updateEmployee_invalidId_returnsNotFound()
    {
        EmployeeFactoryOptions eOpt1 = EmployeeFactoryOptions.builder().build();
        Employee e = this.employeeFactory.create(eOpt1);

        HashMap<String, String> data = new HashMap<>();
        data.put("firstame", "newName");

        given().contentType(ContentType.JSON).body(data).log().all()
            .patch("/employees/" + e.getId() + 1)
            .then().statusCode(HttpStatus.NOT_FOUND.value())
            .body("message", equalTo("Employee of ID " + e.getId() + 01 + " not found."))
            .body(matchesJsonSchemaInClasspath("schemas/api-error-response-schema.json"));
    }

    //Pagination Tests

    @Test 
    public void getAll_default_returnsFirstTenEmployees(){
        this.employeeFactory.create(15);

        given().when().get("/employees")
            .then().statusCode(HttpStatus.OK.value())
            .body("data", hasSize(10))
            .body("totalPages", equalTo(2))
            .body("currentPage", equalTo(1))
            .body("nextPage", equalTo(2))
            .body("previousPage", nullValue())
            .body("totalResults", equalTo(15))
            .body(matchesJsonSchemaInClasspath("schemas/employee-page-schema.json"));
    }

    @Test 
    public void getAll_getFive_returnsFirstFiveEmployees()
    {
        this.employeeFactory.create(15);

        given().when().get("/employees?size=5")
            .then().statusCode(HttpStatus.OK.value())
            .body("data", hasSize(5))
            .body("totalPages", equalTo(3))
            .body("currentPage", equalTo(1))
            .body("nextPage", equalTo(2))
            .body("previousPage", nullValue())
            .body("totalResults", equalTo(15))
            .body(matchesJsonSchemaInClasspath("schemas/employee-page-schema.json"));
    }

    @Test 
    public void getAll_secondPage_returnsSecondTenEmployees(){
        this.employeeFactory.create(20);

        given().when().get("/employees?page=2")
            .then().statusCode(HttpStatus.OK.value())
            .body("data", hasSize(10))
            .body("totalPages", equalTo(2))
            .body("currentPage", equalTo(2))
            .body("nextPage", nullValue())
            .body("previousPage", equalTo(1))
            .body("totalResults", equalTo(20))
            .body(matchesJsonSchemaInClasspath("schemas/employee-page-schema.json"));
    }

    @Test 
    public void getAll_middlePage_bothNextAndPreviousPage()
    {
        this.employeeFactory.create(15);

        given().when().get("/employees?size=5&page=2")
            .then().statusCode(HttpStatus.OK.value())
            .body("data", hasSize(5))
            .body("totalPages", equalTo(3))
            .body("currentPage", equalTo(2))
            .body("nextPage", equalTo(3))
            .body("previousPage", equalTo(1))
            .body("totalResults", equalTo(15))
            .body(matchesJsonSchemaInClasspath("schemas/employee-page-schema.json"));
    }

    @Test 
    public void getAll_getPagePastTotal_returnsUnprocessableContent()
    {
        this.employeeFactory.create(5);

        given().when().get("/employees?size=5&page=2")
            .then().statusCode(HttpStatus.UNPROCESSABLE_CONTENT.value())
            .body("message", equalTo("Page 2 is too high. Total pages is 1"))
            .body(matchesJsonSchemaInClasspath("schemas/api-error-response-schema.json"));
    }

    //search tests

    @Test 
    public void getAll_searchValidData_returnsMatching()
    {
        this.employeeFactory.create(EmployeeFactoryOptions.builder().firstName("correct").build());
        this.employeeFactory.create(EmployeeFactoryOptions.builder().firstName("wrong").build());

        given().when().get("/employees?search=correct")
            .then().statusCode(HttpStatus.OK.value())
            .body("data", hasSize(1))
            .body("data.firstName", hasItem("correct"))
            .body(matchesJsonSchemaInClasspath("schemas/employee-page-schema.json"));
    }

    @Test 
    public void getAll_searchIncorrectData_returnsEmptyArray()
    {
        this.employeeFactory.create(EmployeeFactoryOptions.builder().firstName("correct").build());
        this.employeeFactory.create(EmployeeFactoryOptions.builder().firstName("wrong").build());

        given().when().get("/employees?search=incorrect")
            .then().statusCode(HttpStatus.OK.value())
            .body("data", hasSize(0))
            .body(matchesJsonSchemaInClasspath("schemas/employee-page-schema.json"));
    }

    @Test 
    public void getAll_blankSearch_returnsAllEmployees()
    {
        this.employeeFactory.create(EmployeeFactoryOptions.builder().firstName("one").build());
        this.employeeFactory.create(EmployeeFactoryOptions.builder().firstName("two").build());

        given().when().get("/employees?search=")
            .then().statusCode(HttpStatus.OK.value())
            .body("data", hasSize(2))
            .body("data.firstName", hasItem("one"))
            .body("data.firstName", hasItem("two"))
            .body(matchesJsonSchemaInClasspath("schemas/employee-page-schema.json"));
    }

}
