package com.asseco.cas.interfaces;

import com.asseco.cas.parameters.domain.ApplicationParameterList;
import com.asseco.cas.parameters.domain.Parameter;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;

import java.util.ArrayList;
import java.util.List;

public interface ParameterListInterface {

    ParameterList save(ParameterList parameter);

    ParameterList update(ParameterList parameter);

    void delete(ParameterList parameterList);

    List<ParameterList> findAll();

    ParameterList findById(Long idParameterList);

    List<ParameterList> findByName(String parameterListName);

    List<ApplicationParameterList> findAllApplicationLists();

    List<SystemParameterList> findAllSystemLists();

    //SAMO ZA TESTIRANJE
    ArrayList<Parameter> readList ();
}
