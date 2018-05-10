package com.asseco.cas.parameters.domain;

import com.asseco.cass.ais.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="PARAMETER_LIST",uniqueConstraints = {@UniqueConstraint(columnNames = {"UUID"})})
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class ParameterList extends BaseEntity {

    private static final long serialVersionUID = -4966954954652370907L;

    public enum ParameterListEnum{
        INITIAL (0),
        ACTIVE (1),
        ARCHIVED (2);

        private int value;
        ParameterListEnum(int value){
            this.value=value;
        }

        public int getValue(){
            return value;
        }
    }

    @Column(name="PARAMETER_NAME",unique=true, length=300)
    private String name;
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER, mappedBy="parameterList")
    protected Set<ParameterItem> parameterItems = new HashSet<ParameterItem>();
    @Column(name="STATE_CODE", length=10)
    @Enumerated(EnumType.STRING)
    private ParameterListEnum stateCode;

    public ParameterList() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //@JsonIgnore
    public Set<ParameterItem> getParameterItems() {
        return parameterItems;
    }

    public void setParameterItems(Set<ParameterItem> parameterItems) {
        this.parameterItems = parameterItems;
    }



    public ParameterListEnum getStateCode() {
        return stateCode;
    }

    public void setStateCode(ParameterListEnum stateCode) {
        this.stateCode = stateCode;
    }

    public boolean addParameter(ParameterItem parameterItem) {
        if (parameterItem == null) {
            throw new IllegalArgumentException("ParameterItem is null!");
        }
        if (parameterItem.getId() != null) {
            throw new UnsupportedOperationException("Identification of parameterItem is not null!");
        }
        if (parameterItem.getParameterList() != null) {
            throw new UnsupportedOperationException("ParameterItem is already added to a parameterItem list!");
        }
        if (this.parameterItems.add(parameterItem)){
            parameterItem.setParameterList(this);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeParameter(ParameterItem parameterItem) {
        if (parameterItem == null) {
            throw new IllegalArgumentException("ParameterItem is null!");
        }
        if (this.parameterItems.remove(parameterItem)){
            parameterItem.setParameterList(null);
            return true;
        } else {
            return false;
        }
    }


}
