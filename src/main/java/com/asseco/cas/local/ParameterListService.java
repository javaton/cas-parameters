package com.asseco.cas.local;


import com.asseco.cas.interfaces.ParameterListInterface;
import com.asseco.cas.parameters.domain.ApplicationParameterList;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Profile("local")
public class ParameterListService implements ParameterListInterface {

    ParameterList parameterList = new SystemParameterList();
    ParameterList parameterList2 = new SystemParameterList();

    List<ParameterList> parameterValuesList = new ArrayList<>();

    public ParameterListService(){
        parameterList.setId((long)1);
        parameterList.setName("ListName");
        parameterList.setStateCode(ParameterList.ParameterListEnum.INITIAL);
        parameterList.setVersion((long)123456);

        parameterList2.setId((long)2);
        parameterList2.setName("List2Name");
        parameterList2.setStateCode(ParameterList.ParameterListEnum.ACTIVE);
        parameterList2.setVersion((long)654321);

        parameterValuesList.add(parameterList);
        parameterValuesList.add(parameterList2);


        Set<ParameterItem> set = new HashSet<>();

        ParameterItem items[];

        InputStream is = getClass().getClassLoader().getResourceAsStream("MOCK_DATA.json");
        if (is != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                items = objectMapper.readValue(is, ParameterItem[].class);
                Collections.addAll(set, items);
                parameterList.setParameterItems(set);
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }

    }



    @Override
    public ParameterList save(ParameterList parameterList) {

        if(parameterValuesList.add(parameterList))
            return parameterList;

        return null;
    }

    @Override
    public ParameterItem saveParameterToList(Long idList, ParameterItem parameterItem){

        for(ParameterList pList : parameterValuesList){
            if (pList.getId().equals(idList)){
                pList.addParameter(parameterItem);
                return parameterItem;
            }
        }

        return null;
    }

    @Override
    public ParameterList update(ParameterList parameter) {

        for (ParameterList pList : parameterValuesList){
            if (pList.getId().equals(parameter.getId())){
                pList.setName(parameter.getName());
                pList.setVersion(parameter.getVersion());
                pList.setStateCode(parameter.getStateCode());
                return pList;
            }
        }


        return null;
    }

    public ParameterItem updateParameterInList (Long idList, ParameterItem parameterItem){

        for(ParameterList pList : parameterValuesList){
            if(pList.getId().equals(idList)){
                Set<ParameterItem> tmp = pList.getParameterItems();
                for(ParameterItem pItem : tmp){
                    if(pItem.getId().equals(parameterItem.getId())){
                        pItem.setKey(parameterItem.getKey());
                        pItem.setDescription(parameterItem.getDescription());
                        pItem.setValue(parameterItem.getValue());
                        return pItem;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void delete(ParameterList parameterList) {
        for (Iterator<ParameterList> it = parameterValuesList.iterator(); it.hasNext(); ) {
            ParameterList pList = it.next();
            if ((pList.getId()).equals(parameterList.getId())) {
                it.remove();
            }
        }
    }

    @Override
    public void deleteFromList(Long idList, ParameterItem parameterItem){
        for (ParameterList pList : parameterValuesList){
            if (pList.getId().equals(idList)){
                Set<ParameterItem> tmp = pList.getParameterItems();
                for (Iterator<ParameterItem> it = tmp.iterator(); it.hasNext(); ) {
                    ParameterItem pItem = it.next();
                    if(pItem.getId().equals(parameterItem.getId()))
                        it.remove();
                }
            }
        }
    }

    @Override
    public List<ParameterList> findAll() {
        return null;
    }

    @Override
    public ParameterList findById(Long idParameterList) {
        if (parameterValuesList != null)
            return parameterValuesList.stream()
                    .filter(e -> e.getId().equals(idParameterList))
                    .findFirst()
                    .get();

        return null;
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
    public List<ParameterList> readList() {
        if(parameterValuesList!=null)
            return parameterValuesList.stream()
            .collect(Collectors.toList());

        return null;
    }

    @Override
    public ParameterItem getParameterItem(Long idList, Long idItem){

        for(ParameterList pList : parameterValuesList){
            if(pList.getId().equals(idList)){
                Set<ParameterItem> tmp = pList.getParameterItems();
                for (ParameterItem pItem : tmp){
                    if(pItem.getId().equals(idItem))
                        return pItem;
                }
            }
        }

        return null;
    }

}
