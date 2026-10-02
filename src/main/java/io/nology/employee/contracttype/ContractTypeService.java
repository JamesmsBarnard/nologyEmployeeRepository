package io.nology.employee.contracttype;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import io.nology.employee.common.exceptions.NotFoundException;
import io.nology.employee.contracttype.DTOS.CreateContractTypeDTO;
import io.nology.employee.contracttype.entity.ContractType;

@Service 
public class ContractTypeService {

    private final ContractTypeRepository contractTypeRepository;
    private final ModelMapper modelMapper;

    public ContractTypeService(ContractTypeRepository contractTypeRepository, ModelMapper modelMapper)
    {
        this.contractTypeRepository = contractTypeRepository;
        this.modelMapper = modelMapper;
    }

    public List<ContractType> getContractTypes() {
        return this.contractTypeRepository.findAll();
    }

    public ContractType create(CreateContractTypeDTO data)
    {
        ContractType newType = modelMapper.map(data, ContractType.class);

        this.contractTypeRepository.save(newType);

        return newType;
    }  

    public ContractType getContractTypeById(long id)
    {
        ContractType ct = this.contractTypeRepository.findById(id).orElseThrow(() -> 
            new NotFoundException("Contract type of id " + id + " not found.")
        );
        
        return ct;
    }
    
}
