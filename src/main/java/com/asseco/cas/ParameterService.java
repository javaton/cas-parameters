package com.asseco.cas;

import com.asseco.cas.DTO.ParameterRepresentation;
import com.asseco.cas.interfaces.ParameterInterface;
import com.asseco.cas.parameters.domain.Parameter;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.exceptions.checked.ParameterListNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParameterService implements ParameterInterface<ParameterRepresentation> {

    ArrayList<Parameter> list = new ArrayList<>();;
    HashSet<Parameter> set = new HashSet<Parameter>();
    //Potrebno samo za testiranje
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
        //list = new ArrayList<>();
        parameterList.setId(1L);
        parameterList.setName("Placeholder ParamList Name");

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


    public ArrayList<ParameterRepresentation> readList (){
        ArrayList<ParameterRepresentation> rep = new ArrayList<>();
        for (Parameter p : list){
            rep.add(new ParameterRepresentation(p.getId(), p.getKey(),p.getValue(),p.getDescription()));
        }

        return rep;
    }

    /*public boolean addParameter(Parameter parameter){

        if (list.add(parameter))
            return true;

        return false;
    }*/


    /*public boolean checkKey(String key){
        for (Parameter p : list){
            if ((p.getKey()).equals(key))
                return true;
        }
        return false;
    }

    public Parameter getByKey(String key){
        for (Parameter p : list){
            if ((p.getKey()).equals(key))
                return p;
        }
        return null;
    }*/


    @Override
    public void save(Parameter parameter) {
        list.add(parameter);
    }

    @Override
    public Parameter update(Parameter parameter) {

        for (Parameter p : list){
            if ((p.getKey()).equals(parameter.getKey())){
                p.setDescription(parameter.getDescription());
                p.setValue(parameter.getValue());
                return p;
            }
        }
        return null;
    }

    @Override
    public void delete(Long idParameterList, Parameter parameter) throws ParameterListNotFoundException {
        for(Iterator<Parameter> it = list.iterator(); it.hasNext();){
            Parameter p1 = it.next();
            if((p1.getId()).equals(parameter.getId())){
                it.remove();
            }
        }
    }

    @Override
    public Parameter findById(Long idParameter) {
        for(Iterator<Parameter> it = list.iterator(); it.hasNext();){
            Parameter p1 = it.next();
            if((p1.getId()).equals(idParameter)){
                return p1;
            }
        }
        return null;
    }

    @Override
    public List<Parameter> findAllParameterFromList(String paramListName) {
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
            Parameter p1 = it.next();
            Long tmpId = p1.getParameterList().getId();
            if(tmpId.equals(idParameterList)){
                tmp.add(p1);
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
                Parameter p1 = it.next();

                if(parameterKey.equals(p1.getKey())){
                    return p1;
                }
            }
        }

        return null;
    }
}
