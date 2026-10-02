package io.nology.employee.contracttype;

import org.springframework.data.jpa.repository.JpaRepository;

import io.nology.employee.contracttype.entity.ContractType;

public interface ContractTypeRepository extends JpaRepository<ContractType, Long> {
    
}
