package com.asseco.cas.interfaces;

import com.asseco.cas.parameters.domain.Parameter;
import com.asseco.cas.parameters.exceptions.checked.ParameterListNotFoundException;

import java.util.ArrayList;
import java.util.List;

public interface ParameterInterface<T> {

    void save(Parameter parameter);

    Parameter update(Parameter parameter);

    void delete(Long idParameterList, Parameter parameter) throws ParameterListNotFoundException;

    Parameter findById(Long idParameter);

    List<Parameter> findAllParameterFromList(String paramListName);

    List<Parameter> findAllParameterFromList(Long idParameterList);

    Parameter getParameterFromListByName(String listName, String parameterKey);

    //SAMO ZA TESTIRANJE
    ArrayList<T> readList();

}
