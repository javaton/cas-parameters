package com.asseco.cas.facade;


import com.asseco.cas.interfaces.ParameterItemFacade;
import com.asseco.cas.interfaces.ParameterItemRepository;
import com.asseco.cas.parameters.domain.ParameterItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParameterItemFacadeImpl  implements ParameterItemFacade {

    ParameterItemRepository parameterItemRepository;

    @Autowired
    public ParameterItemFacadeImpl(ParameterItemRepository parameterItemRepository){
        this.parameterItemRepository = parameterItemRepository;
    }


    public ParameterItem findById(Long idParameter) {
        if (idParameter != null)
            return parameterItemRepository.findById(idParameter);

        return null;
    }


    public boolean store(ParameterItem parameterItem) {
        if(parameterItem.getKey() != null
                && parameterItem.getValue() != null
                && parameterItem.getDescription() != null) {

            parameterItemRepository.store(parameterItem);
            return true;
        }
        else return false;
    }

    /*public ParameterItem update(ParameterItem parameterItem) {
        if (parameterItem.getKey() != null)
            return parameterItemRepository.update(parameterItem);

        return null;
    }*/


    public void delete(Long idParameterList, ParameterItem parameterItem) {

    }

    @Override
    public void delete(Long idParameterList, Long idParameter) {
        parameterItemRepository.delete(idParameterList, idParameter);
    }


    /*public List<ParameterItem> findAllParameterFromList(String paramListName) {

        List<ParameterItem> p = new ArrayList<>();
        try {
            p = parameterItemRepository.findAllParameterFromList(paramListName);
        } catch (Exception e){
            System.out.println("Exception in findAllParameterFromList(Long): " + e.getMessage());
        }

        return p;
        *//*if (!(p.isEmpty()))
            return p;

        return null;*//*
    }


    public List<ParameterItem> findAllParameterFromList(Long idParameterList) {
        List<ParameterItem> p = new ArrayList<>();
        try {
            p = parameterItemRepository.findAllParameterFromList(idParameterList);
        } catch (Exception e){
            System.out.println("Exception in findAllParameterFromList(Long): " + e.getMessage());
        }

        if (!(p.isEmpty()))
            return p;

        return null;
    }*/


    public ParameterItem getParameterFromListByName(String listName, String parameterKey) {

        if(!(listName.isEmpty()) && !(parameterKey.isEmpty())) {
            return parameterItemRepository.getParameterFromListByName(listName, parameterKey);

        }
        return null;
    }

    public ParameterItem saveParameterToList(Long idList, ParameterItem parameterItem){

        if(idList != 0 && idList > 0 && parameterItem.getKey() != null
                && parameterItem.getValue() != null
                && parameterItem.getDescription() !=null)
            return parameterItemRepository.saveParameterToList(idList, parameterItem);

        return null;
    }

    public ParameterItem updateParameterInList (Long idList, ParameterItem parameterItem){

        if(idList != null && idList > 0
                && parameterItem.getKey() != null
                && parameterItem.getValue() != null
                && parameterItem.getDescription() !=null)
            return parameterItemRepository.updateParameterInList(idList, parameterItem);

        return null;
    }

}
