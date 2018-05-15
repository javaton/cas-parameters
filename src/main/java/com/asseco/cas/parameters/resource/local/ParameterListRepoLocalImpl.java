package com.asseco.cas.parameters.resource.local;

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
@Profile("resource-local")
public class ParameterListRepoLocalImpl extends EntityRepositoryImpl<ParameterList> implements ParameterListRepository {

    private EntityManager getRepository(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("parametersPU");
        em = emf.createEntityManager();
        return em;
    }

    public EntityManager getEntityManager(){
        if(em==null){
            getRepository();
        }
        return em;
    }


    @Override
    public ParameterList store(ParameterList entity) {
        getEntityManager();
        if(entity == null) {
            return null;
        } else {
            em.getTransaction().begin();

            if(entity.getId() == null) {

                System.out.println("Store new " + entity.getId());
                this.em.persist(entity);
                System.out.println("After persist new " + entity.getId());

            } else {
                entity = this.em.merge(entity);
                em.flush();

            }
            em.getTransaction().commit();

            return entity;
        }
    }


    @Override
    public List<ParameterList> findAll() {
        String queryString =  " select pl from ParameterList pl " ;
        System.out.println("Status " + getRepository().isOpen());
        Query q = getRepository().createQuery(queryString);
        return q.getResultList();
    }


    @Override
    public ParameterList findById(Long idParameterList){

        ParameterList parameterList = null;
        String query = "select p from ParameterList p where p.id=" + idParameterList;
        System.out.println(query);
        try {
            parameterList = (ParameterList)getRepository().createQuery(query).getSingleResult();
        } catch (NoResultException e){e.getMessage();}

        return parameterList;
    }


    @Override
    public ParameterList update(ParameterList entity) {
        getRepository();

        if (entity == null) {
            return null;
        } else {
            em.getTransaction().begin();

            ParameterList p = null;

            if (!(entity.getId() == null)) {

                try {
                    p = (ParameterList) em.createQuery("select p from ParameterList p where p.id=" + entity.getId()).getSingleResult();
                } catch (Exception e) {
                    e.getMessage();
                    return null;
                }
                if (!(p == null)) {
                    p.setName(entity.getName());
                    p.setParameterItems(entity.getParameterItems());
                    p.setStateCode(entity.getStateCode());
                    p.setVersion(entity.getVersion());

                    this.em.merge(p);

                }

            } else {
                em.flush();
                return null;
            }

        }

        try {
            em.flush();
            em.getTransaction().commit();
        } catch (OptimisticLockException e){
            e.getMessage();
            return null;
        }

        return entity;
    }



    @Override
    public void remove(Long idList) {
        getRepository();
        em.getTransaction().begin();

        String query = "delete from ParameterList p where p.id=" + idList;
        System.out.println(query);
        em.createQuery(query).executeUpdate();

        em.getTransaction().commit();
        em.close();

    }



















    @Override
    public List<ParameterList> findByName(String parameterListName) {
        String queryString =  " select * from PARAMETER_LIST pl where pl.PARAMETER_NAME='"+parameterListName+"'" ;
        Query q = getRepository().createQuery(queryString);
        return q.getResultList();
    }


    @Override
    public List<ApplicationParameterList> findAllApplicationLists() {
        return null;
    }

    @Override
    public List<SystemParameterList> findAllSystemLists() {
        return null;
    }

    @Override
    protected Class<ParameterList> getEntityClass() {
        return null;
    }
}
