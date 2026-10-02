package io.nology.employee.contract.entity;

import java.time.LocalDate;

import io.nology.employee.common.BaseEntity;
import io.nology.employee.contracttype.entity.ContractType;
import io.nology.employee.employee.entity.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "Contract")
public class Contract extends BaseEntity{

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "employee_id", nullable=false)
    private Employee employee;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private ContractType contractType;

    @Column (nullable=false)
    private LocalDate startDate;

    @Column (nullable=true)
    private LocalDate endDate;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}
