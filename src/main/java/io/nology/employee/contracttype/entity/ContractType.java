package io.nology.employee.contracttype.entity;

import io.nology.employee.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity 
@Table(name = "ContractType")
public class ContractType extends BaseEntity{
    
    @Column
    private String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
