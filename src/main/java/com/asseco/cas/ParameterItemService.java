package com.asseco.cas;

import com.asseco.cas.interfaces.ParameterItemInterface;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.asseco.cass.application.ApplicationException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Profile("local")
public class ParameterItemService implements ParameterItemInterface {

    private ArrayList<ParameterItem> list = new ArrayList<>();;
    private HashSet<ParameterItem> set = new HashSet<ParameterItem>();

    ParameterList parameterList = new SystemParameterList();

    public ParameterItemService(){
        populateList();
    }

    private void populateList(){
        parameterList.setId((long)1);
        parameterList.setName("ListName");

        ParameterItem para;
        for (int i = 0; i<=50; i++){
                para = new ParameterItem();
                para.setId(Long.valueOf(i));
                para.setKey(String.valueOf(i/* * (int)(Math.random()*100)*/));
                para.setValue("Para Value: " + String.valueOf(i * (int)(Math.random()*150)));
                para.setDescription("Para " + String.valueOf(i * (int)(Math.random()*150)) + " Description");
                //addParameter() ne radi jer trazi da id bude null, a ne postavlja mu posle sam vrednost
                //parameterList.addParameter(para);
                list.add(para);
                set.add(para);
           // parameterList.setParameterItems(set);
            para.setParameterList(parameterList);
        }
    }


    public ArrayList<ParameterItem> readList (){
        return list;
    }

    @Override
    public void save(ParameterItem parameterItem) {
        ParameterItem p = parameterItem;
        p.setId((long)list.size());
        list.add(parameterItem);
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
            //TO DO proveriti sta se ovde desava
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

        ArrayList<ParameterItem> tmp = new ArrayList<>();
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

        ArrayList<ParameterItem> tmp = new ArrayList<>();
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
