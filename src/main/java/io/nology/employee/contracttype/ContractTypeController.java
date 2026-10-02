package io.nology.employee.contracttype;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.employee.contracttype.DTOS.CreateContractTypeDTO;
import io.nology.employee.contracttype.entity.ContractType;


@RestController
@RequestMapping("/contractType")
public class ContractTypeController {
    
    private final ContractTypeService contractTypeService;

    public ContractTypeController(ContractTypeService contractTypeService)
    {
        this.contractTypeService = contractTypeService;
    }


    @GetMapping
    public ResponseEntity<List<ContractType>> getContractTypes() {
        List<ContractType> ContractTypes =  this.contractTypeService.getContractTypes();

        return ResponseEntity.ok(ContractTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractType> getContractTypeById(@PathVariable Long id) {
        ContractType ContractTypes =  this.contractTypeService.getContractTypeById(id);

        return ResponseEntity.ok(ContractTypes);
    }

    @PostMapping
    public ResponseEntity<ContractType> create(@RequestBody CreateContractTypeDTO data) {
        ContractType newContractType = this.contractTypeService.create(data);

        return ResponseEntity.ok(newContractType);
    }
    
    

}
