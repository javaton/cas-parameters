package com.asseco.cas.parameters.resource.local;

import com.asseco.cas.interfaces.ParameterListRepo;
import com.asseco.cas.interfaces.ParameterListRepository;
import com.asseco.cas.parameters.domain.ApplicationParameterList;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.asseco.cass.persist.EntityRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Service
@EnableAutoConfiguration
@Profile("resource-local")
public class ParameterListRepoLocalImpl extends EntityRepositoryImpl<ParameterList> implements ParameterListRepository {

    @Autowired
    private ParameterListRepo parameterListRepo;

    @Override
    @Transactional
    public ParameterList store(ParameterList entity) {

        ParameterList list = null;
        try {
            list = parameterListRepo.save(entity);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        if (list==null)
            return null;
        return list;

    }


    @Override
    public List<ParameterList> findAll() {
        return parameterListRepo.findAll();
    }


    @Override
    public ParameterList findById(Long idParameterList){
        Optional<ParameterList> found = parameterListRepo.findById(idParameterList);

        if(found.isPresent())
            return found.get();

        return null;
    }


    @Override
    public ParameterList update(ParameterList entity) {

        ParameterList list = null;

        try {
            list = parameterListRepo.findById(entity.getId()).get();
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        if (list==null) {
            return null;
        } else {
            //TODO jos uvek nije stavljeno da update-uje i parameterItems
            list.setStateCode(entity.getStateCode());
            list.setName(entity.getName());

            parameterListRepo.save(list);
            return parameterListRepo.findById(entity.getId()).get();
        }

    }

    @Override
    public void remove(Long idList) {
        ParameterList list = null;

        try {
            list = parameterListRepo.findById(idList).get();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        if (!(list==null))
            parameterListRepo.delete(list);
    }



















    @Override
    public List<ParameterList> findByName(String parameterListName) {
        return null;
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
