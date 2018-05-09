package com.asseco.cas.facade;


import com.asseco.cas.interfaces.ParameterItemFacade;
import com.asseco.cas.interfaces.ParameterItemRepository;
import com.asseco.cas.parameters.domain.ParameterItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParameterItemFacadeImpl  implements ParameterItemFacade {

    ParameterItemRepository parameterItemRepository;

    @Autowired
    public ParameterItemFacadeImpl(ParameterItemRepository parameterItemRepository){
        this.parameterItemRepository = parameterItemRepository;
    }


    public ParameterItem findById(Long idParameter) {
        if (idParameter != null)
            return parameterItemRepository.findById(idParameter);

        return null;
    }


    public boolean store(ParameterItem parameterItem) {
        if(parameterItem.getKey() != null
                && parameterItem.getValue() != null
                && parameterItem.getDescription() != null) {

            parameterItemRepository.store(parameterItem);
            return true;
        }
        else return false;
    }


    public void delete(Long idParameterList, Long idParameter) {
        parameterItemRepository.delete(idParameterList, idParameter);
    }


    public ParameterItem getParameterFromList(Long listId, Long itemId) {

        if(listId>0 && itemId>0) {
            return parameterItemRepository.getParameterFromList(listId, itemId);

        }
        return null;
    }


    public ParameterItem saveParameterToList(Long idList, ParameterItem parameterItem){

        if(idList != 0 && idList > 0 && parameterItem.getKey() != null
                && parameterItem.getValue() != null
                && parameterItem.getDescription() !=null)
            return parameterItemRepository.saveParameterToList(idList, parameterItem);

        return null;
    }


    public ParameterItem updateParameterInList (Long idList, ParameterItem parameterItem){

        if(idList != null && idList > 0
                && parameterItem.getKey() != null
                && parameterItem.getValue() != null
                && parameterItem.getDescription() !=null)
            return parameterItemRepository.updateParameterInList(idList, parameterItem);

        return null;
    }

}
