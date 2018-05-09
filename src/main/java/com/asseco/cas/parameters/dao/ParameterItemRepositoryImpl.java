package com.asseco.cas.parameters.dao;

import com.asseco.cas.interfaces.ParameterItemRepository;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cass.application.ApplicationException;
import com.asseco.cass.persist.EntityRepositoryImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@EnableAutoConfiguration
@Service
@Profile("database")
public class ParameterItemRepositoryImpl<P extends ParameterItem> extends EntityRepositoryImpl<ParameterItem> implements ParameterItemRepository {

    private EntityManager getRepository(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("parametersPU");
        em = emf.createEntityManager();
        return em;
    }
    /**
     * Function that save ParameterItem
     * @param parameterItem
     */

    /* ovde treba da bude store
    public void save(ParameterItem parameterItem) {
        getRepository().persist(parameterItem);
    }*/

    public ParameterItem store(ParameterItem parameterItem) {
        getRepository().persist(parameterItem);
        return parameterItem;
    }


    /**
     * Function that update ParameterItem
     * @param parameterItem
     * @return
     */
    public ParameterItem update(ParameterItem parameterItem) {
        return getRepository().merge(parameterItem);
    }

    /**
     * Function that remove ParameterItem
     * @param parameterItem
     * @throws ApplicationException
     */
    public void delete(Long idParameterList, ParameterItem parameterItem) throws ApplicationException {
//        ParameterList parameterList = getRepository().findById(ParameterList.class, idParameterList);
//
//        if(parameterList == null){
//            throw new ParameterListNotFoundException("ParameterItem list not found!!");
//        }
//
//        if(parameterList.getParameterItems() != null){
//            parameterList.getParameterItems().remove(parameterItem);
//        }

        getRepository().remove(parameterItem);
    }

    @Override
    public void delete(Long idParameterList, Long idParameter) {
        ////Kroz DELETE ne moze da stigne objekat
    }


    /**
     * Function that finds all parameterItems from given ParameterList
     * @param paramListName
     * @return
     */
    /*public List<ParameterItem> findAllParameterFromList(String paramListName) {
        String query = "select p from ParameterItem p "
                + "where p.parameterList.name =" + "'" + paramListName + "'";
        System.out.println(query);
        Query q = getRepository().createQuery(query);
        return q.getResultList();
    }
    *//**
     * Function that finds all parameterItems from given ParameterList
     * @param idParameterList
     * @return
     *//*
    public List<ParameterItem> findAllParameterFromList(Long idParameterList) {
        String query = "select p from ParameterItem p "
                + "where p.parameterList.id =" + "'" + idParameterList + "'";
        System.out.println(query);
        Query q = getRepository().createQuery(query);
        return q.getResultList();
    }*/

    public ParameterItem getParameterFromListByName(String listName, String parameterKey){
        String queryString = " select p from ParameterItem p " +
                " where p.key = :keyParam and " +
                "       p.parameterList.name = :listNameParam ";

        Map<String, String> params = new HashMap<String, String>();
        params.put("keyParam", parameterKey);
        params.put("listNameParam", listName);
        return null;
//        List<ParameterItem> list = getRepository().findByNamedParams(queryString, params);
//
//        if(list == null || list.isEmpty()){
//            return null;
//        }
//
//        return list.get(0);
    }

    @Override
    protected Class getEntityClass() {
        return ParameterItem.class;
    }


    @Override
    public ParameterItem saveParameterToList(Long idList, ParameterItem parameterItem) {
        return null;
    }


    @Override
    public ParameterItem updateParameterInList(Long idList, ParameterItem parameterItem) {
        return null;
    }






}
