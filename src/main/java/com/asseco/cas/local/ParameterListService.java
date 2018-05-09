package com.asseco.cas.local;


import com.asseco.cas.interfaces.ParameterListRepository;
import com.asseco.cas.parameters.domain.ApplicationParameterList;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Profile("local")
public class ParameterListService implements ParameterListRepository {

    LocalService lc;

    public ParameterListService(){
        lc = LocalService.getLocalService();
    }

    @Override
    public List<ParameterList> findAll() {

        if(lc.getParameterValuesList()!=null)
            return lc.getParameterValuesList().stream()
                    .collect(Collectors.toList());

        return null;
    }

    @Override
    public ParameterList findById(Long idParameterList) {



        if (lc.getParameterValuesList() != null) {
            try {
                return lc.getParameterValuesList().stream()
                        .filter(e -> e.getId().equals(idParameterList))
                        .findAny()
                        .get();
            } catch (NoSuchElementException e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public ParameterList store(ParameterList parameterList) {
        boolean check = true;
        for(ParameterList p : lc.getParameterValuesList()){
            if (p.getId().equals(parameterList.getId()))
                check = false;
        }

        if(check) {
            try {
                lc.getParameterValuesList().add(parameterList);
                return parameterList;
            } catch (Exception e){return null;}
        }
        return null;
    }

    @Override
    public ParameterList update(ParameterList parameterList) {

        for (ParameterList pList : lc.getParameterValuesList()){
            if (pList.getId().equals(parameterList.getId())){
                pList.setName(parameterList.getName());
                pList.setVersion(parameterList.getVersion());
                pList.setStateCode(parameterList.getStateCode());
                return pList;
            }
        }


        return null;
    }

    @Override
    public void remove(Long idList) {
        for (Iterator<ParameterList> it = lc.getParameterValuesList().iterator(); it.hasNext(); ) {
            ParameterList pList = it.next();
            if ((pList.getId()).equals(idList)) {
                it.remove();
            }
        }
    }











    @Override
    public void remove(ParameterList parameterList) {

    }

    @Override
    public List<ParameterList> findByName(String parameterListName) {
        return null;
    }

    @Override
    public ParameterList findByUuid(String s) {
        return null;
    }

    @Override
    public List<ApplicationParameterList> findAllApplicationLists() {
        return null;
    }

    @Override
    public List<SystemParameterList> findAllSystemLists() {
        return null;
    }

}
