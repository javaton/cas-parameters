package com.asseco.cas.parameters.dao;

import com.asseco.cas.interfaces.ParameterListInterface;
import com.asseco.cas.parameters.domain.ApplicationParameterList;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.asseco.cass.ais.domain.BaseEntity;
import com.asseco.cass.persist.EntityRepositoryImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.*;

import java.util.List;

//@Repository
@Service
@EnableAutoConfiguration
@Repository
public class ParameterListRepositoryImpl extends EntityRepositoryImpl<ParameterList> implements ParameterListInterface {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("parametersPU");



    private EntityManager getRepository(){
        em = emf.createEntityManager();
        return em;
    }

//    @Override
//    public ParameterList store(ParameterList entity) {
//        getRepository();
//        return super.store(entity);
//    }

    @Override
    public ParameterList store(ParameterList entity) {
        getRepository();
        if(entity == null) {
            return null;
        } else {
            if(entity.getId() == null) {
                System.out.println("Store new " + entity.getId());
                this.em.persist(entity);
                System.out.println("After persist new " + entity.getId());
            } else {
                entity = this.em.merge(entity);
            }

            this.em.close();
            return entity;
        }
    }

    public List<ParameterList> findAll() {
        String queryString =  " select pl from ParameterList pl order by pl.name " ;
        System.out.println("Status " + getRepository().isOpen());
        Query q = getRepository().createQuery(queryString);
        return q.getResultList();
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


    @Override
    protected Class<ParameterList> getEntityClass() {
        return ParameterList.class;
    }
}
