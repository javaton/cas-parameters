package com.asseco.cas.parameters.resource.local;

import com.asseco.cas.interfaces.ParameterListRepository;
import com.asseco.cas.parameters.domain.ApplicationParameterList;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.asseco.cass.persist.EntityRepositoryImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;


@Service
@EnableAutoConfiguration
@Repository
@Profile("resource-local")
public class ParameterListRepoLocalImpl extends EntityRepositoryImpl<ParameterList> implements ParameterListRepository {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("parametersPU");

    private EntityManager getRepository(){
        em = emf.createEntityManager();
        return em;
    }


    @Override
    public ParameterList store(ParameterList entity) {
        getRepository();
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

            //this.entityManager.close();
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

    public ParameterList findById(Long idParameterList){
        String query = "select p from ParameterList p where p.id=" + idParameterList;
        System.out.println(query);
        ParameterList parameterList = (ParameterList)getRepository().createQuery(query).getSingleResult();
        return parameterList;
    }

    //Da li cemo imati liste sa istim imenom?
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
    public ParameterList update(ParameterList parameterList) {
        return null;
    }


    @Override
    public void remove(Long idList) {

    }

    @Override
    protected Class<ParameterList> getEntityClass() {
        return null;
    }
}
