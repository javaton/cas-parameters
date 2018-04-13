package com.asseco.cas.parameters.dao;

import com.asseco.cas.interfaces.ParameterListInterface;
import com.asseco.cas.parameters.domain.ApplicationParameterList;
import com.asseco.cas.parameters.domain.Parameter;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.asseco.cass.ais.dao.AisDao;

import java.util.ArrayList;
import java.util.List;

public class ParameterListDAO extends AisDao implements ParameterListInterface {

    public ParameterList save(ParameterList parameter) {
        getRepository().persist(parameter);
        return parameter;
    }

    public ParameterList update(ParameterList parameter){
        parameter = getRepository().merge(parameter);
        return parameter;
    }

    public void delete(ParameterList parameterList){
        getRepository().remove(parameterList);
    }

    public List<ParameterList> findAll() {
        String queryString =  " select pl from ParameterList pl order by pl.name " ;
        return getRepository().executeQuery(queryString);
    }

    public ParameterList findById(Long idParameterList){
        return getRepository().findById(ParameterList.class, idParameterList);
    }

    public List<ParameterList> findByName(String parameterListName){
        String queryString =  " select pl from ParameterList pl where pl.name='"+parameterListName+"'" ;
        return getRepository().executeQuery(queryString);
    }

    public List<ApplicationParameterList> findAllApplicationLists(){
        String queryString =  " select apl from ApplicationParameterList apl order by apl.name " ;
        return getRepository().executeQuery(queryString);
    }

    public List<SystemParameterList> findAllSystemLists(){
        String queryString =  " select spl from SystemParameterList spl order by spl.name " ;
        return getRepository().executeQuery(queryString);
    }

    //SAMO ZA TESTIRANJE
    public ArrayList<Parameter> readList (){
        return null;
    }

}
