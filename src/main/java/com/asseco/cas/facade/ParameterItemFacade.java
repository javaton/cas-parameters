package com.asseco.cas.facade;


import com.asseco.cas.interfaces.ParameterItemRepository;
import com.asseco.cas.parameters.domain.ParameterItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParameterItemFacade {

    ParameterItemRepository parameterItemRepository;

    @Autowired
    public ParameterItemFacade(ParameterItemRepository parameterItemRepository){
        this.parameterItemRepository = parameterItemRepository;
    }


    public List<ParameterItem> readList() {
        return parameterItemRepository.readList();
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

    public ParameterItem update(ParameterItem parameterItem) {
        if (parameterItem.getKey() != null)
            return parameterItemRepository.update(parameterItem);

        return null;
    }

    //TODO Ovde mora delete sa dva parametra da bi imalo smisla
    //U suprotnom ne zna se iz koje se liste brise
    //remove prihvata samo jedan parametar
    public void delete(Long idParameterList, ParameterItem parameterItem) {
        parameterItemRepository.delete(idParameterList, parameterItem);
    }


    public List<ParameterItem> findAllParameterFromList(String paramListName) {

        List<ParameterItem> p = new ArrayList<>();
        try {
            p = parameterItemRepository.findAllParameterFromList(paramListName);
        } catch (Exception e){
            System.out.println("Exception in findAllParameterFromList(Long): " + e.getMessage());
        }

        if (!(p.isEmpty()))
            return p;

        return null;
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
    }


    public ParameterItem getParameterFromListByName(String listName, String parameterKey) {

        if(!(listName.isEmpty()) && !(parameterKey.isEmpty())) {
            return parameterItemRepository.getParameterFromListByName(listName, parameterKey);

        }
        return null;
    }

}
