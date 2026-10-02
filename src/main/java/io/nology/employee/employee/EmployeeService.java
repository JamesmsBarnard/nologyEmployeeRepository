package io.nology.employee.employee;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.nology.employee.employee.DTOS.CreateEmployeeDTO;
import io.nology.employee.employee.DTOS.UpdateEmployeeDTO;
import io.nology.employee.employee.entity.Employee;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper)
    {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Page<Employee> getEmployees(Specification<Employee> spec, Pageable pageable) 
    {
        return this.employeeRepository.findAll(spec, pageable);
    }

    public Employee createEmployee(CreateEmployeeDTO data)
    {
        Employee newEmployee = modelMapper.map(data, Employee.class);

        this.employeeRepository.save(newEmployee);

        return newEmployee;
    }

    public Optional<Employee> getById(Long id) {
        return this.employeeRepository.findById(id);
    }

    public Optional<Employee> updateEmployee(Long id, UpdateEmployeeDTO data) 
    {
        Optional<Employee> toUpdate = this.employeeRepository.findById(id);

        if(!toUpdate.isPresent())
        {
            System.out.println("return nothing");
            return toUpdate;
        }


        Employee employee = toUpdate.get();

        modelMapper.map(data, employee);


        this.employeeRepository.save(employee);
        
        return Optional.of(employee);
    }

    // private EmploymentType resolveType(Long id)
    // {
    //     return this.employmentTypeRepository.findById(id).get();
    // }
    
}
