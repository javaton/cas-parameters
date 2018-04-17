package com.asseco.cas.parameters.dao;


import com.asseco.cas.interfaces.ParameterInterface;
import com.asseco.cas.parameters.domain.Parameter;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.exceptions.checked.ParameterListNotFoundException;
import com.asseco.cass.ais.dao.AisDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterDAO extends AisDao implements ParameterInterface {

    /**
     * Function that save Parameter
     * @param parameter
     */
    public void save(Parameter parameter) {
        getRepository().persist(parameter);
    }
    /**
     * Function that update Parameter
     * @param parameter
     * @return
     */
    public Parameter update(Parameter parameter) {
        return getRepository().merge(parameter);
    }

    /**
     * Function that remove Parameter
     * @param parameter
     * @throws ParameterListNotFoundException
     */
    public void delete(Long idParameterList, Parameter parameter) throws ParameterListNotFoundException {
        ParameterList parameterList = getRepository().findById(ParameterList.class, idParameterList);

        if(parameterList == null){
            throw new ParameterListNotFoundException("Parameter list not found!!");
        }

        if(parameterList.getParameters() != null){
            parameterList.getParameters().remove(parameter);
        }

        getRepository().remove(parameter);
    }

    /**
     * Function that finds Parameter with given id
     * @param idParameter
     * @return
     */
    public Parameter findById(Long idParameter) {
        return getRepository().findById(Parameter.class, idParameter);
    }

    /**
     * Function that finds all parameters from given ParameterList
     * @param pramListName
     * @return
     */
    public List<Parameter> findAllParameterFromList(String paramListName) {
        String query = "select p from Parameter p "
                + "where p.parameterList.name =" + "'" + paramListName + "'";

        return getRepository().executeQuery(query);
    }
    /**
     * Function that finds all parameters from given ParameterList
     * @param pramListName
     * @return
     */
    public List<Parameter> findAllParameterFromList(Long idParameterList) {
        String query = "select p from Parameter p "
                + "where p.parameterList.id =" + "'" + idParameterList + "'";

        return getRepository().executeQuery(query);
    }

    public Parameter getParameterFromListByName(String listName, String parameterKey){
        String queryString = " select p from Parameter p " +
                " where p.key = :keyParam and " +
                "       p.parameterList.name = :listNameParam ";

        Map<String, String> params = new HashMap<String, String>();
        params.put("keyParam", parameterKey);
        params.put("listNameParam", listName);

        List<Parameter> list = getRepository().findByNamedParams(queryString, params);

        if(list == null || list.isEmpty()){
            return null;
        }

        return list.get(0);
    }


    //SAMO ZA TESTIRANJE
    public ArrayList<Parameter> readList (){
        return null;
    }

}
