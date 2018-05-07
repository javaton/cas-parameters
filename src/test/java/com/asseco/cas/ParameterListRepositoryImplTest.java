package com.asseco.cas;

import com.asseco.cas.interfaces.ParameterListRepository;
import com.asseco.cas.parameters.dao.ParameterListRepositoryImpl;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

/**
 * Created by djordje.colovic on 26-Apr-18.
 */

@RunWith(SpringRunner.class)
@ComponentScan
@EnableAutoConfiguration
@ActiveProfiles("database")
public class ParameterListRepositoryImplTest {


    private ParameterListRepository parameterListRepository;

    @Autowired
    public void setParameterListRepository(ParameterListRepositoryImpl listInterface) {
        this.parameterListRepository = listInterface;
    }

    private ParameterList parameterList;

    @Before
    public void setUp(){
        parameterList = new SystemParameterList();
        parameterList.setName("Test1" + UUID.randomUUID().toString());
        parameterList.setStateCode(ParameterList.ParameterListEnum.ACTIVE);
        parameterList.setUuid(UUID.randomUUID().toString());
        ParameterItem pi = new ParameterItem();
        parameterList.addParameter(pi);
        System.out.println();
    }

    @Test
    public void storeParameterList(){
        ParameterList testObject = parameterListRepository.store(parameterList);
        System.out.println("Test object getUuid " + testObject.getUuid());
        System.out.println("Test object id " + testObject.getId());
        System.out.println("Test object entity created "  + testObject.getEntityCreated());
        Assert.assertNotNull(testObject.getId());
        Assert.assertNotNull(testObject.getVersion());
    }

    @Test
    public void readParameterList(){
        List<ParameterList> testObject = parameterListRepository.findAll();
        System.out.println("Test object list size " + testObject.size());
        Assert.assertNotEquals(0, testObject.size());


    }

}
