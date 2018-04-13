package com.asseco.cas.parameters.domain;

import com.asseco.cass.ais.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="CC1PARAMETER_LIST",uniqueConstraints = {@UniqueConstraint(columnNames = {"UUID"})})
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class ParameterList extends BaseEntity {

    private static final long serialVersionUID = -4966954954652370907L;

    public enum ParameterListEnum{INITIAL, ACTIVE, ARCHIVED};

    @Column(name="PARAMETER_NAME",unique=true, length=300)
    private String name;
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER, mappedBy="parameterList")
    protected Set<Parameter> parameters = new HashSet<Parameter>();
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


    //Kada ovo treba da se prebaci u JSON dolazi do beskonacne rekurzije, i izbacuje Error
    @JsonIgnore
    public Set<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(Set<Parameter> parameters) {
        this.parameters = parameters;
    }



    public ParameterListEnum getStateCode() {
        return stateCode;
    }

    public void setStateCode(ParameterListEnum stateCode) {
        this.stateCode = stateCode;
    }

    public boolean addParameter(Parameter parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException("Parameter is null!");
        }
        if (parameter.getId() != null) {
            throw new UnsupportedOperationException("Identification of parameter is not null!");
        }
        if (parameter.getParameterList() != null) {
            throw new UnsupportedOperationException("Parameter is already added to a parameter list!");
        }
        if (this.parameters.add(parameter)){
            parameter.setParameterList(this);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeParameter(Parameter parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException("Parameter is null!");
        }
        if (this.parameters.remove(parameter)){
            parameter.setParameterList(null);
            return true;
        } else {
            return false;
        }
    }


}
