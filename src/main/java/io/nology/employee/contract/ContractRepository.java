package io.nology.employee.contract;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.nology.employee.contract.entity.Contract;
import io.nology.employee.employee.entity.Employee;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    
    @Query("SELECT u FROM Contract u WHERE u.employee = ?1")
    List<Contract> findByEmployeeId(Employee employee);

}
