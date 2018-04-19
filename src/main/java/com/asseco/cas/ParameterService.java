package com.asseco.cas;

import com.asseco.cas.interfaces.ParameterInterface;
import com.asseco.cas.parameters.domain.Parameter;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.exceptions.checked.ParameterListNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParameterService implements ParameterInterface {

    private ArrayList<Parameter> list = new ArrayList<>();;
    private HashSet<Parameter> set = new HashSet<Parameter>();



    //Test Lista
    ParameterList parameterList = new ParameterList() {
        @Override
        public String getName() {
            return super.getName();
        }

        @Override
        public void setName(String name) {
            super.setName(name);
        }

        @Override
        public Set<Parameter> getParameters() {
            return super.getParameters();
        }

        @Override
        public void setParameters(Set<Parameter> parameters) {
            super.setParameters(parameters);
        }

        @Override
        public ParameterListEnum getStateCode() {
            return super.getStateCode();
        }

        @Override
        public void setStateCode(ParameterListEnum stateCode) {
            super.setStateCode(stateCode);
        }

        @Override
        public boolean addParameter(Parameter parameter) {
            return super.addParameter(parameter);
        }

        @Override
        public boolean removeParameter(Parameter parameter) {
            return super.removeParameter(parameter);
        }

        @Override
        public Calendar getEntityCreated() {
            return super.getEntityCreated();
        }

        @Override
        public void setEntityCreated(Calendar entityCreated) {
            super.setEntityCreated(entityCreated);
        }

        @Override
        public Long getId() {
            return super.getId();
        }

        @Override
        public Long getVersion() {
            return super.getVersion();
        }

        @Override
        public String getUuid() {
            return super.getUuid();
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        public void setId(Long id) {
            super.setId(id);
        }

        @Override
        public void setUuid(String uuid) {
            super.setUuid(uuid);
        }

        @Override
        public void setVersion(Long version) {
            super.setVersion(version);
        }
    };

    public ParameterService(){
        populateList();
    }

    private void populateList(){
        parameterList.setId((long)1);
        parameterList.setName("ListName");

        Parameter para;
        for (int i = 0; i<=50; i++){
                para = new Parameter();
                para.setId(Long.valueOf(i));
                para.setKey(String.valueOf(i/* * (int)(Math.random()*100)*/));
                para.setValue("Para Value: " + String.valueOf(i * (int)(Math.random()*150)));
                para.setDescription("Para " + String.valueOf(i * (int)(Math.random()*150)) + " Description");
                //addParameter() ne radi jer trazi da id bude null, a ne postavlja mu posle sam vrednost
                //parameterList.addParameter(para);
                list.add(para);
                set.add(para);
            parameterList.setParameters(set);
            para.setParameterList(parameterList);
        }


    }


    public ArrayList<Parameter> readList (){
        return list;
    }

    @Override
    public void save(Parameter parameter) {
        Parameter p = parameter;
        p.setId((long)list.size());
        list.add(parameter);
    }

    @Override
    public Parameter update(Parameter parameter) {
        for (Parameter p : list){
            if ((p.getId()).equals(parameter.getId())){
                p.setDescription(parameter.getDescription());
                p.setValue(parameter.getValue());
                return p;
            }
        }
        return null;
    }

    @Override
    public void delete(Long idParameterList, Parameter parameter) throws ParameterListNotFoundException {

        if (parameterList.getId().equals(idParameterList)) {
            boolean check = false;
            for (Iterator<Parameter> it = list.iterator(); it.hasNext(); ) {
                Parameter p = it.next();
                if ((p.getId()).equals(parameter.getId())) {
                    it.remove();
                    check = true;
                }
            }
            if (check == false)
                throw new ParameterListNotFoundException("No such parameter to delete");
        } else throw new ParameterListNotFoundException("No \"" + String.valueOf(idParameterList) + "\" list available");
    }

    @Override
    public Parameter findById(Long idParameter) {

        for(Iterator<Parameter> it = list.iterator(); it.hasNext();){
            Parameter p = it.next();
            if((p.getId()).equals(idParameter)){
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Parameter> findAllParameterFromList(String paramListName) {

        Parameter parameter;

        ArrayList<Parameter> tmp = new ArrayList<>();
        for(Iterator<Parameter> it = list.iterator(); it.hasNext();){
            Parameter p1 = it.next();
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
    public List<Parameter> findAllParameterFromList(Long idParameterList) {

        ArrayList<Parameter> tmp = new ArrayList<>();
        for(Iterator<Parameter> it = list.iterator(); it.hasNext();){
            Parameter p = it.next();
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
    public Parameter getParameterFromListByName(String listName, String parameterKey) {

        if(parameterList.getName().equals(listName)){
            for(Iterator<Parameter> it = list.iterator(); it.hasNext();){
                Parameter p = it.next();
                if(parameterKey.equals(p.getKey())){
                    return p;
                }
            }
        }

        return null;
    }
}
