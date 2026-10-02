package io.nology.employee.employee.entity;

// import java.time.LocalDate;

import io.nology.employee.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "Employees")
public class Employee extends BaseEntity {

    //Personal Details

    @Column(nullable = false)
    private String firstName;

    @Column
    private String middleName;

    @Column(nullable = false)
    private String lastName;

    //Contact Details

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    // //Status

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "type_id", nullable = false)
    // private EmploymentType contractType;

    // @Column (nullable = false)
    // private LocalDate startDate;

    // @Column (nullable = true)
    // private LocalDate endDate;
    

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // public EmploymentType getContractType() {
    //     return contractType;
    // }

    // public void setContractType(EmploymentType contractType) {
    //     this.contractType = contractType;
    // }

    // public LocalDate getStartDate() {
    //     return startDate;
    // }

    // public void setStartDate(LocalDate startDate) {
    //     this.startDate = startDate;
    // }

    // public LocalDate getEndDate() {
    //     return endDate;
    // }

    // public void setEndDate(LocalDate endDate) {
    //     this.endDate = endDate;
    // }


}
