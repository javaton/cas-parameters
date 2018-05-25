package com.asseco.cas;


import com.asseco.cas.interfaces.ParameterItemRepository;
import com.asseco.cas.parameters.domain.ParameterItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@ComponentScan
@EnableAutoConfiguration
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableTransactionManagement
@Transactional
@ActiveProfiles("resource-local")
public class ParameterItemRepoLocalImplTest {

    private ParameterItemRepository parameterItemRepository;

    @Autowired
    public void setParameterItemRepository(ParameterItemRepository parameterItemRepository){
        this.parameterItemRepository = parameterItemRepository;
    }

    private ParameterItem item = new ParameterItem();

    String key = "TestKey" + Math.random()*50;
    String value = "TestValue" + Math.random()*50;
    String description = "TestDescription" + Math.random()*50;
    Long listID = (long)3;

    @Before
    public void setUp(){
        item.setKey(key);
        item.setValue(value);
        item.setDescription(description);
    }

    @Test
    public void saveParameterToListTest(){
        ParameterItem testItem = parameterItemRepository.saveParameterToList(listID, item);
        Assert.assertNotNull(testItem);

        parameterItemRepository.delete(listID, testItem.getId());

        Assert.assertNull(parameterItemRepository.getParameterFromList(listID, testItem.getId()));
    }

    @Test
    public void getParameterFromListTest(){

        ParameterItem testItem = parameterItemRepository.saveParameterToList(listID, item);

        ParameterItem getTest = parameterItemRepository.getParameterFromList(listID, testItem.getId());

        Assert.assertNotNull(getTest);
        Assert.assertNotNull(getTest.getId());
    }

    @Test
    public void updateParameterInListTest(){
        ParameterItem testItem = parameterItemRepository.saveParameterToList(listID, item);
        String testKey = testItem.getKey();
        String testValue = testItem.getValue();

        testItem.setKey("UpdatedKey" + Math.random()*50);
        testItem.setValue("UpdatedValue" + Math.random()*50);

        ParameterItem updatedItem = parameterItemRepository.updateParameterInList(listID, testItem);
        String updateKey = updatedItem.getKey();
        String updateValue = updatedItem.getValue();

        Assert.assertNotNull(updatedItem);
        Assert.assertNotEquals(updateKey, testKey);
        Assert.assertNotEquals(updateValue, testValue);

    }

    @Test
    public void deleteTest(){
        ParameterItem testItem = parameterItemRepository.saveParameterToList(listID, item);

        Assert.assertNotNull(testItem);

        parameterItemRepository.delete(listID, testItem.getId());

        Assert.assertNull(parameterItemRepository.getParameterFromList(listID, testItem.getId()));

    }

}
