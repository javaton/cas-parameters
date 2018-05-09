package com.asseco.cas.interfaces;

import com.asseco.cas.parameters.domain.ApplicationParameterList;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.asseco.cass.persist.EntityRepository;


import java.util.List;

public interface ParameterListRepository extends EntityRepository<ParameterList> {


    List<ParameterList> findAll();

    ParameterList findById(Long idParameterList);

    ParameterList update(ParameterList parameterList);

    void remove(Long idList);



//_____________________________________

//trenutno se ne koriste
    List<ParameterList> findByName(String parameterListName);

    List<ApplicationParameterList> findAllApplicationLists();
    List<SystemParameterList> findAllSystemLists();
//_____________________________________

}
