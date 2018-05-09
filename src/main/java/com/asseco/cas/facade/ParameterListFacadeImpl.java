package com.asseco.cas.facade;

import com.asseco.cas.interfaces.ParameterListFacade;
import com.asseco.cas.interfaces.ParameterListRepository;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParameterListFacadeImpl implements ParameterListFacade {

    ParameterListRepository parameterListRepository;

    @Autowired
    public ParameterListFacadeImpl(ParameterListRepository parameterListRepository){
        this.parameterListRepository = parameterListRepository;
    }


    public List<ParameterList> findAll(){
        return parameterListRepository.findAll();
    }


    public ParameterList findById(Long idParameterList){
        return parameterListRepository.findById(idParameterList);
    }

   public ParameterList store(ParameterList parameterList){

       // if (parameterList.getId() != null && parameterList.getId()>0)
                return parameterListRepository.store(parameterList);

        //return null;
    }



    public ParameterList update(ParameterList parameter){

        if(parameter.getId() != null && parameter.getId() >0
                && parameter.getName() != null
                && parameter.getStateCode() != null
                && parameter.getVersion() != null)
            return parameterListRepository.update(parameter);

        return null;
    }

    public void remove(Long idList){
        if(idList != null && idList > 0)
            parameterListRepository.remove(idList);
    }


}