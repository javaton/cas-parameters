package com.asseco.cas.parameters.resource.local;

import com.asseco.cas.interfaces.ParameterItemRepository;
import com.asseco.cas.parameters.domain.ParameterItem;
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
public class ParameterItemRepoLocalImpl<P extends ParameterItem> extends EntityRepositoryImpl<ParameterItem> implements ParameterItemRepository {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("parametersPU");

    private EntityManager getRepository(){
        em = emf.createEntityManager();
        return em;
    }


    @Override
    public List<ParameterItem> findAllParameterFromList(String paramListName) {
        String query = "select p from PARAMETER_ITEM p "
                + "where p.parameterList.name =" + "'" + paramListName + "'";
        System.out.println(query);
        Query q = getRepository().createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<ParameterItem> findAllParameterFromList(Long idParameterList) {
        return null;
    }


    public void delete(Long idParameterList, ParameterItem parameterItem) {

    }

    @Override
    public void delete(Long idParameterList, Long idParameter) {

    }

    @Override
    public ParameterItem getParameterFromListByName(String listName, String parameterKey) {
        return null;
    }

    @Override
    protected Class<ParameterItem> getEntityClass() {
        return null;
    }


    @Override
    public ParameterItem saveParameterToList(Long idList, ParameterItem parameterItem) {
        getRepository();
        if(idList == null) {
            return null;
        } else {
            em.getTransaction().begin();

            if(parameterItem.getId() == null) {

                System.out.println("Store new " + parameterItem.getId());
                this.em.persist(parameterItem);
                System.out.println("After persist new " + parameterItem.getId());

            } else {
                parameterItem = this.em.merge(parameterItem);
                em.flush();

            }
            em.getTransaction().commit();

            //this.entityManager.close();
            return parameterItem;
        }
    }

    @Override
    public ParameterItem updateParameterInList(Long idList, ParameterItem parameterItem) {
        return null;
    }


}
