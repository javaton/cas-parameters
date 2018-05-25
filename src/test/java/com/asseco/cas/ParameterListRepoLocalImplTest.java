package com.asseco.cas;

import com.asseco.cas.interfaces.ParameterListRepository;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
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

import java.util.List;


@RunWith(SpringRunner.class)
@ComponentScan
@EnableAutoConfiguration
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableTransactionManagement
@Transactional
@ActiveProfiles("resource-local")
public class ParameterListRepoLocalImplTest {


    private ParameterListRepository parameterListRepository;

    @Autowired
    public void setParameterListRepo(ParameterListRepository parameterListRepository){
        this.parameterListRepository=parameterListRepository;
    }


    private ParameterList list;

    String name = "TestTest" + Math.random()*200;


    @Before
    public void setUp() {
        list = new SystemParameterList();
        list.setName(name);
        list.setStateCode(ParameterList.ParameterListEnum.ARCHIVED);
}

    @Test
    public void findAllTest(){

        List<ParameterList> testList = parameterListRepository.findAll();
        System.out.println(testList.size());
        Assert.assertNotNull(testList);

    }


    @Test
    public void findByIdTest(){
        List<ParameterList> temp = parameterListRepository.findAll();
        Long id = temp.get(0).getId();
        ParameterList testList = parameterListRepository.findById(id);

        Assert.assertNotNull(testList);
        Assert.assertNotNull(testList.getId());
    }


    @Test
    public void storeTest(){

        ParameterList testList = parameterListRepository.store(list);

        Assert.assertNotNull(testList);
        Assert.assertNotNull(testList.getId());

        parameterListRepository.remove(testList.getId());

        Assert.assertNull(parameterListRepository.findById(testList.getId()));
    }


    @Test
    public void updateTest(){

        ParameterList testList = parameterListRepository.store(list);
        String testName = testList.getName();

        testList.setName("UpdateName" + Math.random()*50);
        testList.setStateCode(ParameterList.ParameterListEnum.ACTIVE);

        ParameterList updatedList = parameterListRepository.update(testList);
        String updateName = updatedList.getName();

        Assert.assertNotNull(updatedList);
        //Assert.assertNotEquals(testList.getVersion(), updatedList.getVersion());
        Assert.assertNotEquals(testName, updateName);

        parameterListRepository.remove(updatedList.getId());

        Assert.assertNull(parameterListRepository.findById(updatedList.getId()));

    }


    @Test
    public void removeTest(){

        ParameterList testList = parameterListRepository.store(list);

        Assert.assertNotNull(testList);

        parameterListRepository.remove(testList.getId());

        Assert.assertNull(parameterListRepository.findById(testList.getId()));

    }


}
