package io.nology.employee.contract.Factory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import io.nology.employee.contract.ContractRepository;
import io.nology.employee.contract.entity.Contract;
import io.nology.employee.contracttype.entity.ContractType;
import io.nology.employee.employee.entity.Employee;

@Component 
@Profile({"dev","test"})
public class ContractFactory {

    private final ContractRepository contractRepository;
    private final Faker faker = new Faker();

    public ContractFactory(ContractRepository contractRepository)
    {
        this.contractRepository = contractRepository;
    }

    public Contract create(Employee employee, ContractType contractType)
    {
        ContractFactoryOptions opts = ContractFactoryOptions.builder().employee(employee).contractType(contractType).build();
        return create(opts);
    }

    public Contract create(ContractFactoryOptions options)
    {
        Contract contract = new Contract();

        if(options.employee == null)
        {
            throw new IllegalStateException("Options must have an Employee");
        }

        if(options.contractType == null)
        {
            throw new IllegalStateException("Options must have a Type");
        }


        contract.setEmployee(options.employee);

        contract.setContractType(options.contractType);

        LocalDate startDate = options.startDate != null
                ? options.startDate
                :  faker.date()
                    .past(1000, TimeUnit.DAYS)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

        LocalDate endDate = options.endDate != null
                ? options.endDate
                :  faker.date()
                    .future(1000, TimeUnit.DAYS)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

        contract.setStartDate(startDate);
        contract.setEndDate(endDate);

        this.contractRepository.saveAndFlush(contract);

        return contract;
    }
}
