package com.asseco.cas.interfaces;

import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;

import java.util.List;

public interface ParameterListFacade {

    List<ParameterList> findAll();

    ParameterList findById(Long idParameterList);

    ParameterList store(ParameterList parameterList);

    ParameterItem saveParameterToList(Long idList, ParameterItem parameterItem);

    ParameterList update(ParameterList parameter);

    ParameterItem updateParameterInList (Long idList, ParameterItem parameterItem);

    void remove(Long idList);

}
