package io.nology.employee.contract;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.employee.common.exceptions.NotFoundException;
import io.nology.employee.contract.DTOS.ContractResponseDTO;
import io.nology.employee.contract.DTOS.CreateContractDTO;
import io.nology.employee.contract.DTOS.UpdateContractDTO;
import io.nology.employee.contract.entity.Contract;




@RestController 
@RequestMapping("/contracts")
public class ContractController {
    
    private final ContractService contractService;

    public ContractController(ContractService contractService)
    {
        this.contractService = contractService;
    }

    @GetMapping()
    public ResponseEntity<List<ContractResponseDTO>> getContracts() 
    {
        return ResponseEntity.ok(ContractResponseDTO.fromEntity(this.contractService.getAllContracts()));
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<List<ContractResponseDTO>> getContractsByEmployee(@PathVariable Long id) {
        List<Contract> contracts = contractService.getAllContractsOfEmployee(id);

        return ResponseEntity.ok(ContractResponseDTO.fromEntity(contracts));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ContractResponseDTO> getContractById(@PathVariable Long id) 
    {
        Contract contract = contractService.getContractById(id).orElseThrow(() -> 
            new NotFoundException("Contract of ID " + id + " not found.")
        );

        return ResponseEntity.ok(ContractResponseDTO.fromEntity(contract));
    }

    @GetMapping("/active/{id}")
    public ResponseEntity<ContractResponseDTO> getActiveOnEmployee(@PathVariable Long id) {
        Contract contract = contractService.getActiveContractOnEmployee(id);

        System.out.println(contract);

        return ResponseEntity.ok(contract != null?ContractResponseDTO.fromEntity(contract):null);
    }
    

    @PostMapping()
    public ResponseEntity<ContractResponseDTO> create(@RequestBody CreateContractDTO data) {
        Contract created = this.contractService.create(data);

        return new ResponseEntity<>(ContractResponseDTO.fromEntity(created), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ContractResponseDTO> update(@PathVariable Long id, @RequestBody UpdateContractDTO data)
    {
        Contract updated = this.contractService.update(id, data);

        return ResponseEntity.ok(ContractResponseDTO.fromEntity(updated));
    }
    
    
}
