package com.asseco.cas.parameters.resource.local;

import com.asseco.cas.interfaces.ParameterItemRepository;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cass.persist.EntityRepositoryImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.*;


@Service
@EnableAutoConfiguration
@Repository
@Profile("resource-local")
public class ParameterItemRepoLocalImpl<P extends ParameterItem> extends EntityRepositoryImpl<ParameterItem> implements ParameterItemRepository {


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
    public ParameterItem getParameterFromList(Long listId, Long itemId) {
        getEntityManager();
        ParameterItem parameterItem = null;

        String query = "select p from ParameterItem p where p.parameterList.id=" + listId + " and p.id=" + itemId ;
        System.out.println(query);
        try{
            parameterItem = (ParameterItem)getRepository().createQuery(query).getSingleResult();
        } catch (NoResultException e){e.getMessage();}

        return parameterItem;
    }


    @Override
    public ParameterItem saveParameterToList(Long idList, ParameterItem parameterItem) {
        getEntityManager();

        if(idList == null) {
            return null;
        } else {
            em.getTransaction().begin();

            if(parameterItem.getId() == null) {

                ParameterList p = (ParameterList)em.createQuery("select pl from ParameterList pl where pl.id=" + idList).getSingleResult();
                parameterItem.setParameterList(p);


                System.out.println("Store new " + parameterItem.getId());
                this.em.persist(parameterItem);
                System.out.println("After persist new " + parameterItem.getId());

            } else {
                parameterItem = this.em.merge(parameterItem);
                em.flush();

            }

            em.getTransaction().commit();

            return parameterItem;
        }
    }


    @Override
    public ParameterItem updateParameterInList(Long idList, ParameterItem parameterItem) {

        getEntityManager();

        if(idList == null) {
            return null;
        } else {
            em.getTransaction().begin();

            if(!(parameterItem.getId() == null)) {

                ParameterList p = (ParameterList)em.createQuery("select pl from ParameterList pl where pl.id=" + idList).getSingleResult();

                ParameterItem pItem = (ParameterItem)em.createQuery("select p from ParameterItem p where p.id=" + parameterItem.getId()).getSingleResult();

                pItem.setParameterList(p);
                pItem.setKey(parameterItem.getKey());
                pItem.setValue(parameterItem.getValue());
                pItem.setDescription(parameterItem.getDescription());
                pItem.setVersion(parameterItem.getVersion());

                System.out.println("Store new " + parameterItem.getId());
                this.em.merge(pItem);
                System.out.println("After persist new " + parameterItem.getId());

            } else {
                parameterItem = this.em.merge(parameterItem);
                em.flush();

            }

            try {
                em.flush();
                em.getTransaction().commit();
            } catch (OptimisticLockException e){
                e.getMessage();
                return null;
            }

            return parameterItem;
        }
    }


    @Override
    public void delete(Long idParameterList, Long idParameter) {

        getEntityManager();
        em.getTransaction().begin();


        String query = "delete from ParameterItem p where p.parameterList.id=" + idParameterList + " and p.id=" + idParameter;
        System.out.println(query);
        em.createQuery(query).executeUpdate();

        em.getTransaction().commit();
        em.close();


    }










    @Override
    protected Class<ParameterItem> getEntityClass() {
        return null;
    }

}
