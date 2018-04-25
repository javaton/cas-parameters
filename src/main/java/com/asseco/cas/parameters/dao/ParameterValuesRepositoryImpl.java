package com.asseco.cas.parameters.dao;

import com.asseco.cas.interfaces.ParameterListInterface;
import com.asseco.cas.parameters.domain.ApplicationParameterList;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.asseco.cass.persist.EntityRepositoryImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ParameterValuesRepositoryImpl<P extends ParameterList> extends EntityRepositoryImpl<P> implements ParameterListInterface {

    private EntityManager getRepository(){
        return em;
    }

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
        return null;
        //return getRepository().executeQuery(queryString);
    }

    public List<ParameterList> findByName(String parameterListName){
        String queryString =  " select pl from ParameterList pl where pl.name='"+parameterListName+"'" ;
//        return getRepository().executeQuery(queryString);
        return null;
    }

    public List<ApplicationParameterList> findAllApplicationLists(){
        String queryString =  " select apl from ApplicationParameterList apl order by apl.name " ;
//        return getRepository().executeQuery(queryString);
        return null;
    }

    public List<SystemParameterList> findAllSystemLists(){
        String queryString =  " select spl from SystemParameterList spl order by spl.name " ;
//        return getRepository().executeQuery(queryString);
        return null;
    }

    //SAMO ZA TESTIRANJE
    public ArrayList<ParameterItem> readList (){
        return null;
    }

    @Override
    protected Class<P> getEntityClass() {
        return null;
    }
}
