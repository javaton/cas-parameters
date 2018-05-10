package com.asseco.cas.parameters.resource.local;

import com.asseco.cas.interfaces.ParameterItemRepository;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cass.persist.EntityRepositoryImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


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
    public void delete(Long idParameterList, Long idParameter) {

        getRepository();
        em.getTransaction().begin();


        String query = "delete from ParameterItem p where p.parameterList.id=" + idParameterList + " and p.id=" + idParameter;
        System.out.println(query);
        em.createQuery(query).executeUpdate();

        em.getTransaction().commit();
        em.close();


    /*
        ParameterItem p = new ParameterItem();
        p.setId(idParameter);
        p.setParameterList((ParameterList)em.createQuery("select pl from ParameterList pl where pl.id=" + idParameterList).getSingleResult());

        if(!em.contains(p))
            p = em.merge(p);

        em.remove(p);*/
    }

    @Override
    public ParameterItem getParameterFromList(Long listId, Long itemId) {
        String query = "select p from ParameterItem p where p.parameterList.id=" + listId + " and p.id=" + itemId ;
        System.out.println(query);
        ParameterItem parameterItem = (ParameterItem)getRepository().createQuery(query).getSingleResult();
        return parameterItem;
    }


    @Override
    public ParameterItem saveParameterToList(Long idList, ParameterItem parameterItem) {
        getRepository();

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

        getRepository();

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

                System.out.println("Store new " + parameterItem.getId());
                this.em.merge(pItem);
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
    protected Class<ParameterItem> getEntityClass() {
        return null;
    }

}
