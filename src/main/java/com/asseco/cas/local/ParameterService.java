package com.asseco.cas.local;

import com.asseco.cas.interfaces.ParameterInterface;
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

@Service
@Profile("local")
public class ParameterService implements ParameterInterface {

    private List<ParameterItem> list = new ArrayList<>();

    private ParameterList parameterList = new SystemParameterList();

    public ParameterService(){
        populateList();
    }

    private void populateList(){
        parameterList.setId((long)1);
        parameterList.setName("ListName");
        parameterList.setStateCode(ParameterList.ParameterListEnum.INITIAL);
        parameterList.setVersion((long)123456);

        ParameterItem items[];

        InputStream is = getClass().getClassLoader().getResourceAsStream("MOCK_DATA.json");
        if (is != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                items = objectMapper.readValue(is, ParameterItem[].class);
                //list = Arrays.asList(items);
                list.addAll(Arrays.asList(items));
                for(ParameterItem p : list){
                    p.setParameterList(parameterList);
                }
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }
    }


    public List<ParameterItem> readList (){
        return list;
    }

    @Override
    public void save(ParameterItem parameterItem) {
        ParameterItem p = parameterItem;
        p.setId((long)list.size()+1);
        p.setParameterList(parameterList);
        list.add(p);
    }

    @Override
    public ParameterItem update(ParameterItem parameterItem) {
        for (ParameterItem p : list){
            if ((p.getId()).equals(parameterItem.getId())){
                p.setDescription(parameterItem.getDescription());
                p.setValue(parameterItem.getValue());
                return p;
            }
        }
        return null;
    }

    @Override
    public void delete(Long idParameterList, ParameterItem parameterItem) throws ApplicationException {

        if (parameterList.getId().equals(idParameterList)) {
            boolean check = false;
            for (Iterator<ParameterItem> it = list.iterator(); it.hasNext(); ) {
                ParameterItem p = it.next();
                if ((p.getId()).equals(parameterItem.getId())) {
                    it.remove();
                    check = true;
                }
            }
            //TODO proveriti sta se ovde desava
//            if (check == false)
//                throw new ApplicationException("No such parameterItem to delete");
//        } else throw new ApplicationException("No \"" + String.valueOf(idParameterList) + "\" list available");
        }
    }

    @Override
    public ParameterItem findById(Long idParameter) {

        for(Iterator<ParameterItem> it = list.iterator(); it.hasNext();){
            ParameterItem p = it.next();
            if((p.getId()).equals(idParameter)){
                return p;
            }
        }
        return null;
    }

    @Override
    public List<ParameterItem> findAllParameterFromList(String paramListName) {

        ParameterItem parameterItem;

        List<ParameterItem> tmp = new ArrayList<>();
        for(Iterator<ParameterItem> it = list.iterator(); it.hasNext();){
            ParameterItem p1 = it.next();
            String tmpName = p1.getParameterList().getName();
            if(tmpName.equals(paramListName)){
                tmp.add(p1);
            }
        }

        if (!(tmp.isEmpty()))
            return tmp;

        return null;
    }

    @Override
    public List<ParameterItem> findAllParameterFromList(Long idParameterList) {

        List<ParameterItem> tmp = new ArrayList<>();
        for(Iterator<ParameterItem> it = list.iterator(); it.hasNext();){
            ParameterItem p = it.next();
            Long tmpId = p.getParameterList().getId();
            if(tmpId.equals(idParameterList)){
                tmp.add(p);
            }
        }
        if (!(tmp.isEmpty()))
            return tmp;

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
