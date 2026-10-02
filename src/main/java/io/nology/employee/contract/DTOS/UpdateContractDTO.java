package io.nology.employee.contract.DTOS;

import java.time.LocalDate;

public class UpdateContractDTO {

    private Long employeeId;

    private Long contractTypeId;

    private LocalDate startDate;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getContractTypeId() {
        return contractTypeId;
    }

    public void setContractTypeID(Long contractTypeId) {
        this.contractTypeId = contractTypeId;
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

    private LocalDate endDate;
    
}
