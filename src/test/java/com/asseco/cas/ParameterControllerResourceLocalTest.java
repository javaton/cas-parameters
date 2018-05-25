package com.asseco.cas;


import com.asseco.cas.controllers.ParameterController;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("resource-local")
@SpringBootTest
@AutoConfigureMockMvc
public class ParameterControllerResourceLocalTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ParameterController controller;

    private Long listId = 3L;
    private Long itemId = 7L;

    @Test
    public void getParameterListsTest() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/parameter-lists").accept(
                MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();


        /*String expected = "[{\"id\":152}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
        JSONAssert ne radi kada se ne radi sa JSON objektima, nego sa JSON nizovima kojima se ne zna tacan broj elemenata.
        Redosled elemenata moze da bude proizvoljan, ali broj elemenata mora da bude fiksan
        */
    }


    @Test
    public void getParameterListTest() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/parameter-lists/" + String.valueOf(listId)).accept(
                MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        ParameterList list = new ObjectMapper().readValue(result.getResponse().getContentAsString(), SystemParameterList.class);

        Assert.assertNotNull(list.getId());
    }


    @Test
    public void getItemFromListTest() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/parameter-lists/" + String.valueOf(listId) + "/" + String.valueOf(itemId)).accept(
                MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();


        ParameterItem item = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ParameterItem.class);

        Assert.assertNotNull(item);
    }


    @Test
    public void addParameterListTest() throws Exception{
        String name= "ControllerTestingName"  + Math.random()*50;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/parameter-lists")
                .accept(MediaType.APPLICATION_JSON).content("{\"name\" : \"" + name +"\", \"stateCode\" : \"INITIAL\"}")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        ParameterList p = new ObjectMapper().readValue(result.getResponse().getContentAsString(), SystemParameterList.class);
        controller.deleteList(p.getId(), response);

        //TODO Ako se menja poruka u controlleru mora da se menja i ovde
        String message = "No list with that ID";
        Assert.assertEquals(controller.getParameterList(p.getId().toString(), response), message);
    }


    @Test
    public void addParameterToListTest() throws Exception{

        String  value = "ControllerTestValue" + Math.random()*50;
        String  key = "ControllerTestKey" + Math.random()*50;
        String  description = "ControllerTestDescription" + Math.random()*50;


        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/parameter-lists/" + listId)
                .accept(MediaType.APPLICATION_JSON).content("{\"key\" : \"" + key + "\", \"value\" : \"" + value + "\", \"description\" : \"" + description + "\"}")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        ParameterItem p = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ParameterItem.class);
        controller.delete(listId, p.getId(), response);
        String message = "No item with given ID";

        Assert.assertEquals(controller.getItemFromList(listId.toString(), p.getId().toString(), response), message);
    }


    @Test
    public void editListTest() throws Exception{
        String name= "ControllerTestingName"  + Math.random()*50;
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();

        SystemParameterList testList = new SystemParameterList();
        testList.setName("FirstWrite" + Math.random()*50);
        testList.setStateCode(ParameterList.ParameterListEnum.ARCHIVED);
        controller.addParameterList(testList, httpServletResponse);

        List<ParameterList> read = controller.getParameterLists(httpServletResponse);

        ParameterList tmpList = read.get(read.size()-1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/parameter-lists")
                .accept(MediaType.APPLICATION_JSON).content("{\"id\" : " + tmpList.getId() + ", \"version\" : "+ tmpList.getVersion() +", \"name\" : \"" + name +"\", \"stateCode\" : \"INITIAL\"}")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        SystemParameterList updated = new ObjectMapper().readValue(result.getResponse().getContentAsString(), SystemParameterList.class);

        Assert.assertEquals(tmpList.getId(), updated.getId());
        Assert.assertNotEquals(tmpList.getName(), updated.getName());

        controller.deleteList(tmpList.getId(), httpServletResponse);
        String message = "No list with that ID";

        Assert.assertEquals(controller.getParameterList(tmpList.getId().toString(), response).toString(), message);

    }

    @Test
    public void editParameterInListTest() throws Exception {
        String  value = "TestValue" + Math.random()*50;
        String  key = "TestKey" + Math.random()*50;
        String  description = "TestDescription" + Math.random()*50;

        String  valueUpdate = "TestValue" + Math.random()*50;
        String  keyUpdate = "TestKey" + Math.random()*50;
        String  descriptionUpdate = "TestDescription" + Math.random()*50;


        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();

        ParameterItem item = new ParameterItem();
        item.setValue(value);
        item.setKey(key);
        item.setDescription(description);

        ParameterItem tmpItem = (ParameterItem)controller.addParameterToList(listId, item, httpServletResponse);



        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/parameter-lists/" + listId)
                .accept(MediaType.APPLICATION_JSON).content("{\"id\" : " + tmpItem.getId() + ", \"version\" : "+ tmpItem.getVersion() +", \"key\" : \"" + keyUpdate +"\", \"value\" : \"" + valueUpdate
                        + "\", \"description\" : \" "+ descriptionUpdate + "\"}")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        ParameterItem p = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ParameterItem.class);

        Assert.assertEquals(tmpItem.getId(), p.getId());
        Assert.assertNotEquals(tmpItem.getKey(), p.getKey());
        Assert.assertNotEquals(tmpItem.getValue(), p.getValue());

        controller.delete(listId, tmpItem.getId(), httpServletResponse);
        String message = "No item with given ID";

        Assert.assertEquals(controller.getItemFromList(listId.toString(), p.getId().toString(), response), message);
    }


    @Test
    public void deleteListTest() throws Exception{
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();

        SystemParameterList p = new SystemParameterList();
        p.setName("deleteTest" + Math.random()*500);
        p.setStateCode(ParameterList.ParameterListEnum.INITIAL);
        SystemParameterList tmp = (SystemParameterList) controller.addParameterList(p, httpServletResponse);

        Assert.assertNotNull(tmp);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/parameter-lists/" + tmp.getId()).contentType(MediaType.APPLICATION_JSON);;
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isNoContent()).andReturn();

        String message = "No list with that ID";

        Assert.assertEquals(controller.getParameterList(tmp.getId().toString(), httpServletResponse), message);
    }

    @Test
    public void deleteTest() throws Exception {
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();

        ParameterItem p = new ParameterItem();
        p.setKey("deleteTestKey" + Math.random()*500);
        p.setValue("deleteTestValue" + Math.random()*500);
        p.setDescription("deleteTestDescription" + Math.random()*500);

        ParameterItem tmp = (ParameterItem) controller.addParameterToList(listId, p, httpServletResponse);
        Assert.assertNotNull(tmp);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/parameter-lists/"+ listId + "/" + tmp.getId()).contentType(MediaType.APPLICATION_JSON);;
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isNoContent()).andReturn();

        String message = "No item with given ID";

        Assert.assertEquals(controller.getItemFromList(listId.toString(), tmp.getId().toString(), httpServletResponse), message);
    }


}
