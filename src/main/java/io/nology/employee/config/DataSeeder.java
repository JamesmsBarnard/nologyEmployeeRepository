package io.nology.employee.config;

import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.nology.employee.contract.Factory.ContractFactory;
import io.nology.employee.contract.Factory.ContractFactoryOptions;
import io.nology.employee.contracttype.factory.ContractTypeFactory;
import io.nology.employee.employee.entity.Employee;
import io.nology.employee.employee.factory.EmployeeFactory;
import io.nology.employee.employee.factory.EmployeeFactoryOptions;

@Component
@Profile("dev")
public class DataSeeder implements CommandLineRunner {
    
    private final ContractTypeFactory contractTypeFactory;
    private final EmployeeFactory employeeFactory;
    private final ContractFactory contractFactory;
    private final Random random = new Random();

    public DataSeeder(ContractTypeFactory contractTypeFactory, EmployeeFactory employeeFactory, ContractFactory contractFactory)
    {
        System.out.println("dataSeeder");
        this.contractTypeFactory = contractTypeFactory;
        this.employeeFactory = employeeFactory;
        this.contractFactory = contractFactory;
    }

    @Override
    public void run(String... args) throws Exception
    {
        System.out.println("goes to seeder.");

        if(this.contractTypeFactory.repoEmpty())
        {
            this.contractTypeFactory.create("Contract");
            this.contractTypeFactory.create("Permanent");
        }

        if(this.employeeFactory.repoEmpty())
        {
            EmployeeFactoryOptions opt1 = EmployeeFactoryOptions.builder().build();
            EmployeeFactoryOptions opt2 = EmployeeFactoryOptions.builder().build();

            this.employeeFactory.create(opt1, 10);
            this.employeeFactory.create(opt2, 10);

            for (Employee emp : this.employeeFactory.getAllEmployees()) {
                ContractFactoryOptions cOpt;
                cOpt = ContractFactoryOptions.builder()
                        .employee(emp)
                        .contractType(this.contractTypeFactory.getById(random.nextLong(2) + 1))
                        .build();

                this.contractFactory.create(cOpt);
            }
        }
    }

}
