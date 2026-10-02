package io.nology.employee.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.employee.common.dtos.PageQueryParams;
import io.nology.employee.common.dtos.PageResponse;
import io.nology.employee.common.exceptions.NotFoundException;
import io.nology.employee.employee.DTOS.CreateEmployeeDTO;
import io.nology.employee.employee.DTOS.EmployeeResponseDTO;
import io.nology.employee.employee.DTOS.UpdateEmployeeDTO;
import io.nology.employee.employee.entity.Employee;
import jakarta.validation.Valid;


@RestController 
@RequestMapping("/employees")
public class EmployeeController {
    
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService)
    {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<EmployeeResponseDTO>> getAllEmployees(@Valid @ModelAttribute PageQueryParams params)
    {
        PageRequest pageRequest = PageRequest.of(params.getPage() - 1, params.getSize(), Sort.by("id"));
        
        //Page<Employee> employees = employeeService.getEmployees(pageRequest); 

        Specification<Employee> spec = EmployeeSpecifications.hasFirstNameLike(params.getSearch());

        Page<Employee> employees = employeeService.getEmployees(spec, pageRequest);

        params.validatePageNumber(employees);

        PageResponse<EmployeeResponseDTO> response = PageResponse.assemble(employees, EmployeeResponseDTO::fromEntity);

        System.out.println("params: ");
        System.out.println(params.getPage());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id)
    {
        Employee employee = employeeService.getById(id).orElseThrow(() -> 
            new NotFoundException("Employee of ID " + id + " not found.")
        );

        return ResponseEntity.ok(EmployeeResponseDTO.fromEntity(employee));
    }
    

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> newEmployee(@RequestBody @Valid CreateEmployeeDTO data) {
        Employee newEmployee = employeeService.createEmployee(data);

        System.out.println(data.getFirstName());

        return new ResponseEntity<>(EmployeeResponseDTO.fromEntity(newEmployee), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id, @RequestBody UpdateEmployeeDTO data)
    {
        Employee updated = this.employeeService.updateEmployee(id, data).orElseThrow(() -> new NotFoundException("Employee of ID " + id + " not found."));

        return ResponseEntity.ok(EmployeeResponseDTO.fromEntity(updated));
    }
    
}
