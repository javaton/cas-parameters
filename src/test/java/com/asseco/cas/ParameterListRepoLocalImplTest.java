package com.asseco.cas;

import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.asseco.cas.parameters.resource.local.ParameterListRepoLocalImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@ComponentScan
@EnableAutoConfiguration
@DataJpaTest
@EnableTransactionManagement
@Transactional
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


    @Test
    public void findAllTest(){
        List<ParameterList> list = parameterListRepoLocal.findAll();
        System.out.println("Test object list size " + list.size());
        Assert.assertNotEquals(0, list.size());
    }


    @Test
    public void storeParameterListTest(){
        EntityManager em = parameterListRepoLocal.getEntityManager();

        ParameterList list = parameterListRepoLocal.store(parameterList);
        System.out.println("UUID: " + list.getUuid());
        System.out.println("Id: " + list.getId());

        Assert.assertNotNull(list.getId());
        Assert.assertNotNull(list.getVersion());

        em.getTransaction().begin();
        em.remove(list);
        em.getTransaction().commit();

    }


    //TODO Dodati data je nepromenjen posle testa, ali version se i dalje menja zbog baze
    @Test
    public void updateParameterListTest(){

        EntityManager em = parameterListRepoLocal.getEntityManager();

        ParameterList p = parameterListRepoLocal.findById((long)383);
        String startName = p.getName();
        System.out.println("p.id = " + p.getId());
        System.out.println("p.version = " + p.getVersion());

        p.setName("Test1" + Math.random()*9000);
        p.setVersion(p.getVersion());

        ParameterList testList = parameterListRepoLocal.update(p);
        String afterName = testList.getName();

        Assert.assertNotNull(testList.getId());
        Assert.assertNotNull(testList.getVersion());
        Assert.assertNotEquals(startName, afterName);

        p.setVersion(parameterListRepoLocal.findById((long)383).getVersion());
        p.setName(startName);


        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
    }


    @Test
    public void removeTest(){
        EntityManager em = parameterListRepoLocal.getEntityManager();

        ParameterList pList = new SystemParameterList();
        pList.setName("Test1" + Math.random()*9000);
        pList.setStateCode(ParameterList.ParameterListEnum.ARCHIVED);

        ParameterList  test = parameterListRepoLocal.store(pList);


        parameterListRepoLocal.remove(test.getId());

        ParameterList testList = null;

        try {
            testList = parameterListRepoLocal.findById(test.getId());
        } catch (NullPointerException e) {
            e.getMessage();

            Assert.assertNull(testList.getId());
            Assert.assertNull(testList.getVersion());
        }
    }


    @Test
    public void findByIdTest(){
        ParameterList list = parameterListRepoLocal.findById((long)383);
        Assert.assertNotNull(list.getId());
        Assert.assertNotNull(list.getVersion());
    }

}
