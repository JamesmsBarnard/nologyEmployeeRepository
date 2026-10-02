package io.nology.employee.contracttype.factory;

import java.util.List;
import java.util.Random;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.nology.employee.contracttype.ContractTypeRepository;
import io.nology.employee.contracttype.entity.ContractType;

@Component 
@Profile({"dev", "test"})
public class ContractTypeFactory {
    
    private final ContractTypeRepository repo;
    Random rand = new Random();
    //private final Faker faker = new Faker();

    public ContractTypeFactory(ContractTypeRepository repo)
    {
        this.repo = repo;
    }

    public ContractType create(ContractTypeFactoryOptions options)
    {
        ContractType t = new ContractType();
        t.setType(options.type != null ? options.type : "went wrong");

        return this.repo.saveAndFlush(t);
    }

    // public ContractType create()
    // {
    //     ContractTypeFactoryOptions emptyOptions = 
    // }

    public ContractType create(String type)
    {
        ContractTypeFactoryOptions options = ContractTypeFactoryOptions.builder().type(type).build();
        return create(options);
    }

    public boolean repoEmpty()
    {
        return this.repo.count() == 0;
    }

    public List<ContractType> getAll()
    {
        return this.repo.findAll();
    }

    public ContractType getById(Long id)
    {
        return this.repo.findById(id).get();
    }



}
