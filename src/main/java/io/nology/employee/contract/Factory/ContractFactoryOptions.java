package io.nology.employee.contract.Factory;

import java.time.LocalDate;

import io.nology.employee.contracttype.entity.ContractType;
import io.nology.employee.employee.entity.Employee;

public class ContractFactoryOptions {
    
    Employee employee;
    ContractType contractType;
    LocalDate startDate;
    LocalDate endDate;


    private ContractFactoryOptions(Builder builder)
    {
        this.employee = builder.employee;
        this.contractType = builder.contractType;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder
    {
        private Employee employee;
        private ContractType contractType;
        private LocalDate startDate;
        private LocalDate endDate;

        public Employee employee() {
            return employee;
        }

        public Builder employee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public Builder contractType(ContractType contractType) {
            this.contractType = contractType;
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public ContractFactoryOptions build()
        {
            return new ContractFactoryOptions(this);
        }
    }

}
