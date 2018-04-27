package com.asseco.cas.parameters.domain;

import com.asseco.cass.ais.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="PARAMETER_ITEM",uniqueConstraints = {@UniqueConstraint(columnNames = {"ID_PARAMETER_LIST", "PARAMETER_KEY"})})
public class ParameterItem extends BaseEntity {

    private static final long serialVersionUID = -1150328291610116275L;
    @Column(name="PARAMETER_KEY", length=50)
    private String key;
    @Column(name="PARAMETER_VALUE", length=300)
    private String value;
    @Column(name="DESCRIPTION", length=2000)
    private String description;
    @ManyToOne
    @JoinColumn (name="ID_PARAMETER_LIST")
    private ParameterList parameterList;

    public ParameterItem() {
        super();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public ParameterList getParameterList() {
        return parameterList;
    }

    public void setParameterList(ParameterList parameterList) {
        this.parameterList = parameterList;
    }

}
