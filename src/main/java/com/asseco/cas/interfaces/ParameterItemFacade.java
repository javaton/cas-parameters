package com.asseco.cas.interfaces;

import com.asseco.cas.parameters.domain.ParameterItem;

import java.util.List;

public interface ParameterItemFacade {

    void delete(Long idParameterList, Long idParameter);

    ParameterItem getParameterFromList(Long listId, Long itemId);

    ParameterItem saveParameterToList(Long idList, ParameterItem parameterItem);

    ParameterItem updateParameterInList (Long idList, ParameterItem parameterItem);

}
