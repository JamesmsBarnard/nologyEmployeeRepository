package io.nology.employee.contract.DTOS;

import java.time.LocalDate;
import java.util.List;

import io.nology.employee.contract.entity.Contract;
import io.nology.employee.contracttype.entity.ContractType;
import io.nology.employee.employee.entity.Employee;

public record ContractResponseDTO(Long id, Employee employee, ContractType contractType, LocalDate startDate, LocalDate endDate){
    
     public static ContractResponseDTO fromEntity(Contract contract)
     {
        return new ContractResponseDTO(
            contract.getId(), 
            contract.getEmployee(),
            contract.getContractType(),
            contract.getStartDate(),
            contract.getEndDate()
        );
     }

     public static List<ContractResponseDTO> fromEntity(List<Contract> contracts)
     {
        return contracts.stream().map(contract -> ContractResponseDTO.fromEntity(contract)).toList();
     }

}
