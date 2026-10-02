package io.nology.employee.contract;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import io.nology.employee.common.exceptions.InvalidDataExcetion;
import io.nology.employee.common.exceptions.NotFoundException;
import io.nology.employee.contract.DTOS.CreateContractDTO;
import io.nology.employee.contract.DTOS.UpdateContractDTO;
import io.nology.employee.contract.entity.Contract;
import io.nology.employee.contracttype.ContractTypeRepository;
import io.nology.employee.contracttype.entity.ContractType;
import io.nology.employee.employee.EmployeeRepository;
import io.nology.employee.employee.entity.Employee;

@Service
public class ContractService {

    private final ContractRepository repo;
    private final EmployeeRepository employeeRepository;
    private final ContractTypeRepository contractTypeRepository;
    private final ModelMapper modelMapper;

    public ContractService(ContractRepository repo, EmployeeRepository employeeRepository, ContractTypeRepository contractTypeRepository, ModelMapper modelMapper)
    {
        this.repo = repo;
        this.employeeRepository = employeeRepository;
        this.contractTypeRepository = contractTypeRepository;
        this.modelMapper = modelMapper;
    }

    public List<Contract> getAllContracts() {
        return this.repo.findAll();
    }

    public Optional<Contract> getContractById(Long id)
    {
        return this.repo.findById(id);
    }

    public Contract create(CreateContractDTO data) {
        List<Contract> contracts = getAllContractsOfEmployee(data.getEmployeeId());

        for(Contract c : contracts)
        {
            if((c.getEndDate().isAfter(data.getStartDate()) && c.getStartDate().isBefore(data.getStartDate()))  ||  (c.getEndDate().isAfter(data.getEndDate()) && c.getStartDate().isBefore(data.getEndDate())))
            {
                throw new InvalidDataExcetion("The dates on a contract can not overlap with another contract on the same employee");
            }
        }

        Contract newContract = this.modelMapper.map(data, Contract.class);

        newContract.setEmployee(resolveEmployee(data.getEmployeeId()));
        newContract.setContractType(resolveContractType(data.getContractTypeId()));

        this.repo.save(newContract);

        return newContract;
    }

    private Employee resolveEmployee(Long eId)
    {
        Optional<Employee> e = this.employeeRepository.findById(eId);
           
        if(e.isEmpty())
        {
            throw new NotFoundException("Employee of id " + eId + " not found");
        }

        return e.get();
    }

    private ContractType resolveContractType(Long ctId)
    {
        Optional<ContractType> ct = this.contractTypeRepository.findById(ctId);
           
        if(ct.isEmpty())
        {
            throw new NotFoundException("Contract Type of id " + ctId + " not found");
        }

        return ct.get();
    }

    public Contract update(Long id, UpdateContractDTO data) {
        Optional<Contract> toUpdate = this.repo.findById(id);

        if(toUpdate.isEmpty())
        {
            throw new NotFoundException("Contract of id " + id + " not found.");
        }

        Contract contract = toUpdate.get();

        modelMapper.map(data, contract);


        if(data.getEmployeeId() != null)
        {
            contract.setEmployee(resolveEmployee(data.getEmployeeId()));
        }
        
        if(data.getContractTypeId() != null)
        {
            contract.setContractType(resolveContractType(data.getContractTypeId()));
        }

        this.repo.save(contract);

        return contract;
    }

    public List<Contract> getAllContractsOfEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> 
            new NotFoundException("Employee of id " + id + " not found.")
        );

        return repo.findByEmployeeId(employee);
    }

    public Contract getActiveContractOnEmployee(Long id) {
        List<Contract> contracts = getAllContractsOfEmployee(id);

        LocalDate today = LocalDate.now();

        System.out.println(contracts);

        if(!contracts.isEmpty())
        {
            for(Contract c: contracts)
            {
                if(c.getStartDate().isBefore(today) && c.getEndDate().isAfter(today))
                {
                    return c;
                }
            }
        }

        return null;
    }

}
