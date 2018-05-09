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


    @Override
    public ParameterItem store(ParameterItem parameterItem) {return null;}


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
    public ParameterItem getParameterFromList(Long listId, Long itemId) {

        for(ParameterList parameterList : lc.getParameterValuesList()){
            if(parameterList.getId() == listId){
                for(Iterator<ParameterItem> it = parameterList.getParameterItems().iterator(); it.hasNext();){
                    ParameterItem p = it.next();
                    if(p.getId() == itemId){
                        return p;
                    }
                }
            }
        }
        return null;
    }


    @Override
    public ParameterItem saveParameterToList(Long idList, ParameterItem parameterItem){

        for(ParameterList pList : lc.getParameterValuesList()){
            if (pList.getId().equals(idList)){
                try {
                    parameterItem.setId((long)pList.getParameterItems().size()+1);
                    Set<ParameterItem> set = pList.getParameterItems();
                    set.add(parameterItem);
                    pList.setParameterItems(set);
                } catch (Exception e){return null;}
                return parameterItem;
            }
        }

        return null;
    }


    @Override
    public ParameterItem updateParameterInList (Long idList, ParameterItem parameterItem){

        for(ParameterList pList : lc.getParameterValuesList()){
            if(pList.getId().equals(idList)){
                Set<ParameterItem> tmp = pList.getParameterItems();

                for(ParameterItem pItem : tmp){
                    if(pItem.getId().equals(parameterItem.getId())){
                        try {
                            pItem.setKey(parameterItem.getKey());
                            pItem.setDescription(parameterItem.getDescription());
                            pItem.setValue(parameterItem.getValue());
                            return pItem;
                        } catch(Exception e){return null;}
                    }
                }
            }
        }
        return null;
    }















    @Override
    public void remove(ParameterItem parameterItem) {}

    @Override
    public ParameterItem findById(Long aLong) {
        return null;
    }

    @Override
    public ParameterItem findByUuid(String s) {
        return null;
    }

}
