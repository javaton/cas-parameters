package com.asseco.cas.interfaces;

import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cass.persist.EntityRepository;

import java.util.List;

public interface ParameterItemRepository extends EntityRepository<ParameterItem> {

    //ParameterItem update(ParameterItem parameterItem);

    List<ParameterItem> findAllParameterFromList(String paramListName);

    List<ParameterItem> findAllParameterFromList(Long idParameterList);

    //void delete(Long idParameterList, ParameterItem parameterItem);

    void delete (Long idParameterList, Long idParameter);

    ParameterItem getParameterFromListByName(String listName, String parameterKey);


    ParameterItem saveParameterToList(Long idList, ParameterItem parameterItem);

    //sporna metoda sa update iz item-a
    ParameterItem updateParameterInList (Long idList, ParameterItem parameterItem);
}
