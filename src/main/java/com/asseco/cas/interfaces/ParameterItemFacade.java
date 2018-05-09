package com.asseco.cas.interfaces;

import com.asseco.cas.parameters.domain.ParameterItem;

import java.util.List;

public interface ParameterItemFacade {

    //ParameterItem findById(Long idParameter);

    //boolean store(ParameterItem parameterItem);

    //ParameterItem update(ParameterItem parameterItem);

    void delete(Long idParameterList, ParameterItem parameterItem);

    void delete(Long idParameterList, Long idParameter);

    ParameterItem getParameterFromListByName(String listName, String parameterKey);

    ParameterItem saveParameterToList(Long idList, ParameterItem parameterItem);

    ParameterItem updateParameterInList (Long idList, ParameterItem parameterItem);

}
