package com.asseco.cas.interfaces;

import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cass.persist.EntityRepository;

import java.util.List;

public interface ParameterItemRepository extends EntityRepository<ParameterItem> {

    ParameterItem update(ParameterItem parameterItem);

    ParameterItem findById(Long idParameter);

    List<ParameterItem> findAllParameterFromList(String paramListName);

    List<ParameterItem> findAllParameterFromList(Long idParameterList);

    void delete(Long idParameterList, ParameterItem parameterItem);

    ParameterItem getParameterFromListByName(String listName, String parameterKey);

    List<ParameterItem> readList();
}
