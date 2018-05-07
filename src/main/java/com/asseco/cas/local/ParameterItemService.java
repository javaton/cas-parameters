package com.asseco.cas.local;

import com.asseco.cas.interfaces.ParameterItemRepository;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.asseco.cass.application.ApplicationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Profile("local")
public class ParameterItemService implements ParameterItemRepository {

    private List<ParameterItem> list = new ArrayList<>();;
    private Set<ParameterItem> set = new HashSet<ParameterItem>();

    private List<ParameterList> lists = new ArrayList<>();

    ParameterList parameterList = new SystemParameterList();

    public ParameterItemService(){

        parameterList.setId((long)1);
        parameterList.setName("ListName");
        parameterList.setStateCode(ParameterList.ParameterListEnum.INITIAL);
        parameterList.setVersion((long)123456);
        lists.add(parameterList);

        ParameterItem items[];

        InputStream is = getClass().getClassLoader().getResourceAsStream("MOCK_DATA.json");
        if (is != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                items = objectMapper.readValue(is, ParameterItem[].class);
                Collections.addAll(list, items);
                Collections.addAll(set, items);
                parameterList.setParameterItems(set);
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }

    }


    public List<ParameterItem> readList (){  return parameterList.getParameterItems().stream().collect(Collectors.toList()); }


    public ParameterItem store(ParameterItem parameterItem) {
        parameterItem.setId((long)parameterList.getParameterItems().size()+1);
        set.add(parameterItem);
        parameterList.setParameterItems(set);
        return parameterItem;
    }

    @Override
    public void remove(ParameterItem parameterItem) {

    }

    @Override
    public ParameterItem update(ParameterItem parameterItem) {


        for(Iterator<ParameterItem> it = parameterList.getParameterItems().iterator(); it.hasNext(); ){
            ParameterItem parameter = it.next();
            if(parameter.getKey().equals(parameterItem.getKey())){
                parameter.setValue(parameterItem.getValue());
                parameter.setDescription(parameterItem.getDescription());
                return parameter;
            }
        }
        return null;
    }


    public void delete(Long idParameterList, ParameterItem parameterItem) {
        if (parameterList.getId().equals(idParameterList)) {
            Set <ParameterItem> tmpSet = parameterList.getParameterItems();
            for (Iterator<ParameterItem> it = tmpSet.iterator(); it.hasNext(); ) {
                ParameterItem p = it.next();
                if ((p.getId()).equals(parameterItem.getId())) {
                    it.remove();
                    parameterList.setParameterItems(tmpSet);
                }
            }
        }
    }

    @Override
    public ParameterItem findById(Long idParameter) {

        Set<ParameterItem> pList = parameterList.getParameterItems();

        for(Iterator<ParameterItem> it = pList.iterator(); it.hasNext();){
            ParameterItem p = it.next();
            if((p.getId()).equals(idParameter)){
                return p;
            }
        }
        return null;
    }

    @Override
    public ParameterItem findByUuid(String s) {
        return null;
    }

    @Override
    public List<ParameterItem> findAllParameterFromList(String paramListName) {

        for (Iterator<ParameterList> it = lists.iterator(); it.hasNext(); ){
            ParameterList pList = it.next();
            if ((pList.getName()).equals(paramListName)) {
                return pList.getParameterItems().stream().collect(Collectors.toList());
            }
        }
        return null;
    }

    @Override
    public List<ParameterItem> findAllParameterFromList(Long idParameterList) {

        for (Iterator<ParameterList> it = lists.iterator(); it.hasNext(); ){
            ParameterList pList = it.next();
            if ((pList.getId()).equals(idParameterList)) {
                return pList.getParameterItems().stream().collect(Collectors.toList());
            }
        }
        return null;
    }

    @Override
    public ParameterItem getParameterFromListByName(String listName, String parameterKey) {

        if(parameterList.getName().equals(listName)){
            for(Iterator<ParameterItem> it = list.iterator(); it.hasNext();){
                ParameterItem p = it.next();
                if(parameterKey.equals(p.getKey())){
                    return p;
                }
            }
        }

        return null;
    }
}
