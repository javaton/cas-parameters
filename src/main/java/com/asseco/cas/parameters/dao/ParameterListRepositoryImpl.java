package com.asseco.cas.parameters.dao;

import com.asseco.cas.interfaces.ParameterListRepository;
import com.asseco.cas.parameters.domain.ApplicationParameterList;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.asseco.cass.persist.EntityRepositoryImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.*;

import java.util.List;


@Service
@EnableAutoConfiguration
@Repository
@Profile("database")
public class ParameterListRepositoryImpl extends EntityRepositoryImpl<ParameterList> implements ParameterListRepository {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("parametersPU");

    EntityManager entityManager = em;

    private EntityManager getRepository(){
        entityManager = emf.createEntityManager();
        return entityManager;
    }


    @Override
    public ParameterList store(ParameterList entity) {
        getRepository();
        if(entity == null) {
            return null;
        } else {
            entityManager.getTransaction().begin();

            if(entity.getId() == null) {

                System.out.println("Store new " + entity.getId());
                //this.entityManager.joinTransaction();
                this.entityManager.persist(entity);
                System.out.println("After persist new " + entity.getId());

            } else {
                entity = this.entityManager.merge(entity);
                entityManager.flush();

            }
            entityManager.getTransaction().commit();

            //this.entityManager.close();
            return entity;
        }
    }

    public List<ParameterList> findAll() {
        String queryString =  " select pl from ParameterList pl order by pl.name " ;
        System.out.println("Status " + getRepository().isOpen());
        Query q = getRepository().createQuery(queryString);
        return q.getResultList();
    }

    @Override
    public ParameterList findById(Long idParameterList){
        return null;
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

    @Override
    public ParameterList update(ParameterList parameter) {
        return null;
    }


    public void remove (Long idLong){
        // TODO: 08-May-18
        //Kroz DELETE ne moze da stigne objekat
    }

}
