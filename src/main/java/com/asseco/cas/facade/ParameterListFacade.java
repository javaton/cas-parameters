package com.asseco.cas.facade;

import com.asseco.cas.interfaces.ParameterListExtended;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParameterListFacade {

    ParameterListExtended parameterListExtended;

    @Autowired
    public ParameterListFacade(ParameterListExtended parameterListExtended){
        this.parameterListExtended = parameterListExtended;
    }


    public List<ParameterList> findAll(){
        return parameterListExtended.findAll();
    }


    public ParameterList findById(Long idParameterList){
        return parameterListExtended.findById(idParameterList);
    }

    public ParameterItem getParameterItem(Long idList, Long idItem){
        return parameterListExtended.getParameterItem(idList, idItem);
    }

    public ParameterList store(ParameterList parameterList){

        if (parameterList.getId() != null && parameterList.getId()>0)
                return parameterListExtended.store(parameterList);

        return null;
    }

    public ParameterItem saveParameterToList(Long idList, ParameterItem parameterItem){

        if(idList != 0 && idList > 0 && parameterItem.getKey() != null
                && parameterItem.getValue() != null
                && parameterItem.getDescription() !=null)
                return parameterListExtended.saveParameterToList(idList, parameterItem);

        return null;
    }

    public ParameterList update(ParameterList parameter){

        if(parameter.getId() != null && parameter.getId() >0
                && parameter.getName() != null
                && parameter.getStateCode() != null
                && parameter.getVersion() != null)
            return parameterListExtended.update(parameter);

        return null;
    }


    public ParameterItem updateParameterInList (Long idList, ParameterItem parameterItem){

        if(idList != null && idList > 0
                && parameterItem.getKey() != null
                && parameterItem.getValue() != null
                && parameterItem.getDescription() !=null)
                return parameterListExtended.updateParameterInList(idList, parameterItem);

        return null;
    }

    public void remove(ParameterList parameterList){
        if(parameterList.getId() != null && parameterList.getId() > 0)
            parameterListExtended.remove(parameterList);
    }

    public void deleteFromList(Long idList, ParameterItem parameterItem){
        if(idList > 0 && parameterItem.getId() != null)
            parameterListExtended.deleteFromList(idList, parameterItem);
    }

}