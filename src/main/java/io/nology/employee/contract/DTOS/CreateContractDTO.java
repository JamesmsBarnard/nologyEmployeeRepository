package io.nology.employee.contract.DTOS;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class CreateContractDTO {
    
    @NotNull 
    private Long employeeId;

    @NotNull 
    private Long contractTypeId;

    @NotNull 
    private LocalDate startDate;

    @NotNull 
    private LocalDate endDate;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getContractTypeId() {
        return contractTypeId;
    }

    public void setContractTypeId(Long contractTypeID) {
        this.contractTypeId = contractTypeID;
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
