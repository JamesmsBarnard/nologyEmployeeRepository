package io.nology.employee.employee.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import io.nology.employee.employee.EmployeeRepository;
import io.nology.employee.employee.entity.Employee;

@Component
@Profile({"dev", "test"})
public class EmployeeFactory {
    private final Faker faker = new Faker();
    private final EmployeeRepository repo;

    public EmployeeFactory(EmployeeRepository repo)
    {
        this.repo = repo;
    }

    public boolean repoEmpty()
    {
        return this.repo.count() == 0;
    }

    public Employee create()
    {
        EmployeeFactoryOptions opts = EmployeeFactoryOptions.builder().build();
        return create(opts);
    }

    public Employee create(EmployeeFactoryOptions options)
    {
        Employee newEmployee = new Employee();

        String firstName = options.firstName != null ? options.firstName : faker.name().firstName();
        String middleName = options.middleName != null ? options.middleName : faker.name().firstName();
        String lastName = options.lastName != null ? options.lastName : faker.name().lastName();
        String email = options.email != null ? options.email :  faker.internet().emailAddress(firstName + "." + lastName);

        newEmployee.setFirstName(firstName);
        newEmployee.setMiddleName(middleName);
        newEmployee.setLastName(lastName);
        newEmployee.setEmail(email);
        

        newEmployee.setAddress(options.address != null ? options.address : faker.address().fullAddress());
        newEmployee.setPhoneNumber(options.phoneNumber != null ? options.phoneNumber : faker.phoneNumber().cellPhone());
        
        //moved to contract

        // LocalDate startDate = options.startDate != null
        //         ? options.startDate
        //         :  faker.date()
        //             .past(1000, TimeUnit.DAYS)
        //             .toInstant()
        //             .atZone(ZoneId.systemDefault())
        //             .toLocalDate();

        // LocalDate endDate = options.endDate != null
        //         ? options.endDate
        //         :  faker.date()
        //             .future(1000, TimeUnit.DAYS)
        //             .toInstant()
        //             .atZone(ZoneId.systemDefault())
        //             .toLocalDate();
        
        // newEmployee.setStartDate(startDate);
        // newEmployee.setEndDate(endDate);

        this.repo.saveAndFlush(newEmployee);
        return newEmployee;
        
    }

    public List<Employee> create(EmployeeFactoryOptions options, int times)
    {
        ArrayList<Employee> employees = new ArrayList<>();

        for(int i = 0; i < times; i++)
        {
            Employee e = create(options);
            employees.add(e);
        }

        return employees;
    }

    public List<Employee> create(int times)
    {
        ArrayList<Employee> employees = new ArrayList<>();

        for(int i = 0; i < times; i++)
        {
            Employee e = create();
            employees.add(e);
        }

        return employees;
    }

    public List<Employee> getAllEmployees()
    {
        return this.repo.findAll();
    }
}
