package io.nology.employee.employee;

import org.springframework.data.jpa.domain.Specification;

import io.nology.employee.employee.entity.Employee;

public class EmployeeSpecifications {
    
    public static Specification<Employee> hasFirstNameLike(String search)
    {
        return (root, query, cb) -> {
            if(search == null || search.isBlank()) 
            {
                return null;
            }
            
            String pattern = "%" + search.toLowerCase() + "%";

            return cb.or(
                cb.like(cb.lower(root.get("firstName")), pattern),
                cb.like(cb.lower(root.get("lastName")), pattern)
            );
        };
    }
}
