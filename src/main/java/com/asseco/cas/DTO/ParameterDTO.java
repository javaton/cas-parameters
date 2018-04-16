package com.asseco.cas.DTO;

public class ParameterRepresentation {

    private Long id;
    private String key;
    private String value;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public ParameterRepresentation(){}

    public ParameterRepresentation(Long id, String key, String value, String description){
        this.id = id;
        this.key = key;
        this.value = value;
        this.description = description;
    }





}
