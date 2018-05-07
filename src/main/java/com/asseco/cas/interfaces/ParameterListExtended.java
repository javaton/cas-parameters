package com.asseco.cas.interfaces;

import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;


//Da bi mogao da pristupim dodatnim metodama, a da ih ne definisem u  prvom interfejsu

public interface ParameterListExtended extends ParameterListRepository {

    ParameterItem saveParameterToList(Long idList, ParameterItem parameterItem);

    ParameterList update(ParameterList parameter);

    ParameterItem updateParameterInList (Long idList, ParameterItem parameterItem);

    void deleteFromList(Long idList, ParameterItem parameterItem);

    ParameterItem getParameterItem(Long idList, Long idItem);

}
