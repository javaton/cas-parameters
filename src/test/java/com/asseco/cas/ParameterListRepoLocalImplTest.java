package com.asseco.cas;


import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.asseco.cas.parameters.resource.local.ParameterListRepoLocalImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RunWith(SpringRunner.class)
@ComponentScan
@EnableAutoConfiguration
@ActiveProfiles("resource-local")
public class ParameterListRepoLocalImplTest {

    private ParameterListRepoLocalImpl parameterListRepoLocal;

    @Autowired
    public void setParameterListRepoLocal(ParameterListRepoLocalImpl parameterListRepoLocal){
        this.parameterListRepoLocal = parameterListRepoLocal;
    }


    private ParameterList parameterList;


    @Before
    public void setUp(){
        parameterList = new SystemParameterList();
        parameterList.setName("Test1" + UUID.randomUUID().toString());
        parameterList.setStateCode(ParameterList.ParameterListEnum.ACTIVE);
        parameterList.setUuid(UUID.randomUUID().toString());

        System.out.println("____________________________________________________________");
    }

    @After
    public void tearDown(){
        parameterListRepoLocal.remove(parameterList.getId());
    }


    @Test
    public void findAllTest(){
        List<ParameterList> list = parameterListRepoLocal.findAll();
        System.out.println("Test object list size " + list.size());
        Assert.assertNotEquals(0, list.size());
    }


    @Test
    public void storeParameterList(){
        ParameterList list = parameterListRepoLocal.store(parameterList);

        System.out.println("\n \n \n \n \n \n \n \n \n \n \n \n \n");
        System.out.println("UUID: " + list.getUuid());
        System.out.println("Id: " + list.getId());
        System.out.println("Entity created "  + list.getEntityCreated());
        System.out.println("\n \n \n \n \n \n \n \n \n \n \n \n \n");
        Assert.assertNotNull(list.getId());
        Assert.assertNotNull(list.getVersion());
    }


    @Test
    public void updateParameterList(){

        ParameterList p = parameterListRepoLocal.findById((long)543);
        System.out.println("\n \n \n \n \n \n \n \n \n \n \n \n \n");
        System.out.println("p.id = " + p.getId());
        System.out.println("p.version = " + p.getVersion());
        System.out.println("\n \n \n \n \n \n \n \n \n \n \n \n \n");

        Set<ParameterItem> set = null;

        parameterList.setId(p.getId());
        parameterList.setVersion(p.getVersion());
        parameterList.setName("Test1" + Math.random()*9000);
        parameterList.setStateCode(ParameterList.ParameterListEnum.ARCHIVED);
        //parameterList.setParameterItems(set);

        ParameterList pList = parameterListRepoLocal.update(parameterList);
        System.out.println("\n \n \n \n \n \n \n \n \n \n \n \n \n");
        System.out.println("UUID: " + pList.getUuid());
        System.out.println("Id: " + pList.getId());
        System.out.println("Entity created "  + pList.getEntityCreated());
        System.out.println("\n \n \n \n \n \n \n \n \n \n \n \n \n");
        Assert.assertNotNull(pList.getId());
        Assert.assertNotNull(pList.getVersion());
    }

}
