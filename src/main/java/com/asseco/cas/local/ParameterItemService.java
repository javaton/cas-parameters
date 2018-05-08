package com.asseco.cas.local;

import com.asseco.cas.interfaces.ParameterItemRepository;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.asseco.cass.application.ApplicationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Profile("local")
public class ParameterItemService implements ParameterItemRepository {

    LocalService lc;

    public ParameterItemService(){
        lc = LocalService.getLocalService();
    }


    /*public ParameterItem store(ParameterItem parameterItem) {
        parameterItem.setId((long)parameterList.getParameterItems().size()+1);
        set.add(parameterItem);
        parameterList.setParameterItems(set);
        return parameterItem;
    }*/

    public ParameterItem store(ParameterItem parameterItem) {return null;}


    /*@Override
    public ParameterItem update(ParameterItem parameterItem) {


        for(Iterator<ParameterItem> it = lc.getParameterList().getParameterItems().iterator(); it.hasNext(); ){
            ParameterItem parameter = it.next();
            if(parameter.getKey().equals(parameterItem.getKey())){
                parameter.setValue(parameterItem.getValue());
                parameter.setDescription(parameterItem.getDescription());
                return parameter;
            }
        }
        return null;
    }*/


    public void delete(Long idParameterList, ParameterItem parameterItem) {


    }

    @Override
    public void delete(Long idParameterList, Long idParameter) {
        List<ParameterList> parameterValuesList = lc.getParameterValuesList();

        for (ParameterList parameterList : parameterValuesList){

            if (parameterList.getId().equals(idParameterList)) {

                Set <ParameterItem> tmpSet = parameterList.getParameterItems();

                for (Iterator<ParameterItem> it = tmpSet.iterator(); it.hasNext(); ) {
                    ParameterItem p = it.next();

                    if ((p.getId()).equals(idParameter)) {

                        it.remove();
                        parameterList.setParameterItems(tmpSet);

                    }
                }
            }
        }
    }

    @Override
    public ParameterItem findByUuid(String s) {
        return null;
    }

    @Override
    public List<ParameterItem> findAllParameterFromList(String paramListName) {

        for (Iterator<ParameterList> it = lc.getParameterValuesList().iterator(); it.hasNext(); ){
            ParameterList pList = it.next();
            System.out.println(pList.getName());
            if ((pList.getName()).equals(paramListName)) {
                return pList.getParameterItems().stream().collect(Collectors.toList());
            }
        }
        return null;
    }

    @Override
    public List<ParameterItem> findAllParameterFromList(Long idParameterList) {

        for (Iterator<ParameterList> it = lc.getParameterValuesList().iterator(); it.hasNext(); ){
            ParameterList pList = it.next();
            if ((pList.getId()).equals(idParameterList)) {
                return pList.getParameterItems().stream().collect(Collectors.toList());
            }
        }
        return null;
    }

    @Override
    public ParameterItem getParameterFromListByName(String listName, String parameterKey) {

        for(ParameterList parameterList : lc.getParameterValuesList()){
            if(parameterList.getName().equals(listName)){
                for(Iterator<ParameterItem> it = parameterList.getParameterItems().iterator(); it.hasNext();){
                    ParameterItem p = it.next();
                    if(parameterKey.equals(p.getKey())){
                        return p;
                    }
                }
            }
        }
        return null;
    }



    @Override
    public ParameterItem findById(Long idItem) {

        for(ParameterList parameterList : lc.getParameterValuesList()){

            for (ParameterItem pItem : parameterList.getParameterItems()){
                if (pItem.getId()== idItem)
                    return pItem;
            }
        }
        return null;
    }


    @Override
    public void remove(ParameterItem parameterItem) {}

}
