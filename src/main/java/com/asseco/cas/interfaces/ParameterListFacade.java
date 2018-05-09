package com.asseco.cas.interfaces;

import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;

import java.util.List;

public interface ParameterListFacade {

    List<ParameterList> findAll();

    ParameterList findById(Long idParameterList);

    ParameterList store(ParameterList parameterList);

    ParameterList update(ParameterList parameter);

    void remove(Long idList);

}
