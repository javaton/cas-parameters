package com.asseco.cas.interfaces;

import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cass.application.ApplicationException;

import java.util.List;

public interface ParameterInterface {

    void save(ParameterItem parameterItem);

    ParameterItem update(ParameterItem parameterItem);

    void delete(Long idParameterList, ParameterItem parameterItem) throws ApplicationException;

    ParameterItem findById(Long idParameter);

    List<ParameterItem> findAllParameterFromList(String paramListName);

    List<ParameterItem> findAllParameterFromList(Long idParameterList);

    ParameterItem getParameterFromListByName(String listName, String parameterKey);

    public List<ParameterItem> readList();
}
