package io.nology.employee.employee.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public class CreateEmployeeDTO {
    
    @NotBlank
    @Pattern(regexp = ".*\\S.*", message = "Name cannot be empty")
    private String firstName;

    private String middleName;

    @NotBlank
    private String lastName;

    @NotNull 
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9().]+$",  message = "Phone number must only be numbers")
    private String phoneNumber;

    @NotBlank
    private String address;

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

    

}
