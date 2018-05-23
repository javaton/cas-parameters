package com.asseco.cas.parameters.resource.local;

import com.asseco.cas.interfaces.ParameterItemRepo;
import com.asseco.cas.interfaces.ParameterItemRepository;
import com.asseco.cas.interfaces.ParameterListRepo;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cass.persist.EntityRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;


@Service
@EnableAutoConfiguration
@Repository
@Profile("resource-local")
public class ParameterItemRepoLocalImpl<P extends ParameterItem> extends EntityRepositoryImpl<ParameterItem> implements ParameterItemRepository {

    @Autowired
    private ParameterItemRepo parameterItemRepo;

    @Autowired
    private ParameterListRepo parameterListRepo;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ParameterItem getParameterFromList(Long listId, Long itemId) {

        ParameterItem item = null;

        try {
            item = (ParameterItem)entityManager.createQuery("select p from ParameterItem p where p.parameterList.id=" + listId + " and p.id=" + itemId).getSingleResult();
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        if (item==null)
            return null;

        return item;

    }


    @Override
    public ParameterItem saveParameterToList(Long idList, ParameterItem parameterItem) {

        ParameterItem item = null;
        ParameterList list = null;
        try {
            list = parameterListRepo.findById(idList).get();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        parameterItem.setParameterList(list);

        try{
            item = parameterItemRepo.save(parameterItem);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        if (item==null)
            return null;

        return item;

    }


    @Override
    public ParameterItem updateParameterInList(Long idList, ParameterItem parameterItem) {

        ParameterItem item = null;

        try{
            item = item = (ParameterItem)entityManager.createQuery("select p from ParameterItem p where p.parameterList.id=" + idList + " and p.id=" + parameterItem.getId()).getSingleResult();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        if (item==null) {
            return null;
        } else {
            item.setDescription(parameterItem.getDescription());
            item.setValue(parameterItem.getValue());
            item.setKey(parameterItem.getKey());

            parameterItemRepo.save(item);

            return item;
        }
    }


    @Override
    @Transactional
    public void delete(Long idParameterList, Long idParameter) {

        ParameterItem item = null;
        try{
            item = (ParameterItem)entityManager.createQuery("select p from ParameterItem p where p.parameterList.id=" + idParameterList + " and p.id=" + idParameter).getSingleResult();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        parameterItemRepo.delete(item);

    }










    @Override
    protected Class<ParameterItem> getEntityClass() {
        return null;
    }

}
