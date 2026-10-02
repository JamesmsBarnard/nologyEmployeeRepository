package io.nology.employee.employee.DTOS;

import java.util.List;

import io.nology.employee.employee.entity.Employee;

public record EmployeeResponseDTO (Long id, String firstName, String middleName, String lastName, String email, String address, String phoneNumber){

    public static EmployeeResponseDTO fromEntity(Employee employee)
    {
        return new EmployeeResponseDTO(
                employee.getId(),
                employee.getFirstName(), 
                employee.getMiddleName(), 
                employee.getLastName(), 
                employee.getEmail(), 
                employee.getAddress(), 
                employee.getPhoneNumber()
        );
    }

    public static List<EmployeeResponseDTO> fromEntity(List<Employee> employees)
    {
        return  employees.stream().map(employee -> EmployeeResponseDTO.fromEntity(employee)).toList();
    }
    
}
