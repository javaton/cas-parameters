package com.asseco.cas;


import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.resource.local.ParameterItemRepoLocalImpl;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.UUID;

@RunWith(SpringRunner.class)
@ComponentScan
@EnableAutoConfiguration
@DataJpaTest
@EnableTransactionManagement
@Transactional
@ActiveProfiles("resource-local")
public class ParameterItemRepoLocalImplTest {

    private ParameterItemRepoLocalImpl parameterItemRepoLocal;

    @Autowired
    public void setParameterItemRepoLocal(ParameterItemRepoLocalImpl parameterItemRepoLocal) {
        this.parameterItemRepoLocal = parameterItemRepoLocal;
    }

    private ParameterItem parameterItem;
    private Long listId = 152L;
    private Long itemId = 532L;

    @Before
    public void setUp(){
        parameterItem = new ParameterItem();
        parameterItem.setKey("Test1" + UUID.randomUUID().toString());
        parameterItem.setValue("TestValue " + Math.random()*5000);
        parameterItem.setDescription("TestDescription " + Math.random()*9000);

        System.out.println("____________________________________________________________");
    }


    @Test
    public void getParameterFromListTest(){
        ParameterItem p = parameterItemRepoLocal.getParameterFromList(listId, itemId);
        Assert.assertNotNull(p.getId());
        Assert.assertNotNull(p.getKey());
        Assert.assertNotNull(p.getValue());
    }


    @Test
    public void saveParameterToListTest(){
        EntityManager em = parameterItemRepoLocal.getEntityManager();

        ParameterItem item = parameterItemRepoLocal.saveParameterToList(listId, parameterItem);

        Assert.assertNotNull(item.getId());
        Assert.assertNotNull(item.getVersion());

        em.getTransaction().begin();
        em.remove(item);
        em.getTransaction().commit();
    }

    @Test
    public void updateParameterInListTest(){
        EntityManager em = parameterItemRepoLocal.getEntityManager();

        ParameterItem p = parameterItemRepoLocal.getParameterFromList(listId, itemId);
        String startKey = p.getKey();
        String startValue = p.getValue();
        String startDescription = p.getDescription();


        p.setKey("Test1" + Math.random()*9000);
        p.setVersion(p.getVersion());
        p.setValue("TestUpdateValue" + Math.random()*500);

        ParameterItem test = parameterItemRepoLocal.updateParameterInList(listId, p);
        String afterKey = test.getKey();
        String afterValue = test.getValue();

        Assert.assertNotNull(test.getId());
        Assert.assertNotNull(test.getVersion());
        Assert.assertNotEquals(startKey, afterKey);
        Assert.assertNotEquals(startValue, afterValue);

        p.setVersion(parameterItemRepoLocal.getParameterFromList(listId, itemId).getVersion());
        p.setKey(startKey);
        p.setValue(startValue);


        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
    }

    @Test
    public void deleteTest(){
        EntityManager em = parameterItemRepoLocal.getEntityManager();

        ParameterItem p = new ParameterItem();
        p.setKey("Test1" + Math.random()*9000);
        p.setVersion(p.getVersion());
        p.setValue("TestUpdateValue" + Math.random()*500);

        ParameterItem test = parameterItemRepoLocal.saveParameterToList(listId, p);
        Assert.assertNotNull(test.getId());

        parameterItemRepoLocal.delete(listId, test.getId());

        ParameterItem testItem = null;

        try {
            testItem = parameterItemRepoLocal.getParameterFromList(listId, test.getId());
        } catch (NullPointerException e) {
            e.getMessage();

            Assert.assertNull(testItem.getId());
            Assert.assertNull(testItem.getVersion());
        }
    }

}
