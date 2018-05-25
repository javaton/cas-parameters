package com.asseco.cas.interfaces;

import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cass.persist.EntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterItemRepository extends EntityRepository<ParameterItem> {

    void delete (Long idParameterList, Long idParameter);

    ParameterItem getParameterFromList(Long listId, Long itemId);

    ParameterItem saveParameterToList(Long idList, ParameterItem parameterItem);

    ParameterItem updateParameterInList (Long idList, ParameterItem parameterItem);
}
