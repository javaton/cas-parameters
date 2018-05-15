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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("resource-local")
@WebMvcTest(ParameterController.class)
public class ParameterControllerResourceLocalTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ParameterController controller;

    private Long listId = 152L;
    private Long itemId = 532L;

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
        String expected = "{\"id\":152}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }


    @Test
    public void getItemFromListTest() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/parameter-lists/" + String.valueOf(listId) + "/" + String.valueOf(itemId)).accept(
                MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        String expected = "{\"id\": 532}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }


    @Test
    public void addParameterListTest() throws Exception{
        String name= "ControllerTestingName"  + Math.random()*50;
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/parameter-lists")
                .accept(MediaType.APPLICATION_JSON).content("{\"name\" : \"" + name +"\", \"stateCode\" : \"INITIAL\"}")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String expected = "{\"name\" : \"" + name +"\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        ParameterList p = new ObjectMapper().readValue(result.getResponse().getContentAsString(), SystemParameterList.class);
        System.out.println("THIS IS THE ID OF THE LIST --> " + p.getId());
        controller.deleteList(p.getId(), response);
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
        String expected = "{\"key\" : \"" + key + "\", \"value\" : \"" + value + "\", \"description\" : \"" + description + "\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        ParameterItem p = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ParameterItem.class);
        controller.delete(listId, p.getId(), response);

    }


    @Test
    public void editListTest() throws Exception{
        String name= "ControllerTestingName"  + Math.random()*50;
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();

        String json = new ObjectMapper().writeValueAsString(controller.getParameterList(String.valueOf(listId), httpServletResponse));

        SystemParameterList tmpList = new ObjectMapper().readValue(json, SystemParameterList.class);



        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/parameter-lists")
                .accept(MediaType.APPLICATION_JSON).content("{\"id\" : " + listId + ", \"version\" : "+ tmpList.getVersion() +", \"name\" : \"" + name +"\", \"stateCode\" : \"INITIAL\"}")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String expected = "{\"id\" : " + listId + ", \"name\" : \"" + name +"\", \"stateCode\" : \"INITIAL\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        SystemParameterList p = new ObjectMapper().readValue(result.getResponse().getContentAsString(), SystemParameterList.class);
        controller.editList(p, response);
    }

    @Test
    public void editParameterInListTest() throws Exception {
        String  value = "TestValue" + Math.random()*50;
        String  key = "TestKey" + Math.random()*50;
        String  description = "TestDescription" + Math.random()*50;

        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        String json = new ObjectMapper().writeValueAsString(controller.getItemFromList(String.valueOf(listId), String.valueOf(itemId), httpServletResponse));
        ParameterItem tmpItem = new ObjectMapper().readValue(json, ParameterItem.class);


        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/parameter-lists/" + listId)
                .accept(MediaType.APPLICATION_JSON).content("{\"id\" : " + String.valueOf(itemId) + ", \"version\" : "+ tmpItem.getVersion() +", \"key\" : \"" + key +"\", \"value\" : \"" + value + "\", \"description\" : \" "+ description + "\"}")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String expected =  "{\"id\" : " + String.valueOf(itemId) + ", \"version\" : "+ (tmpItem.getVersion()+1) +", \"key\" : \"" + key +"\", \"value\" : \"" + value + "\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        ParameterItem p = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ParameterItem.class);
        p.setKey(tmpItem.getKey());
        p.setValue(tmpItem.getValue());
        p.setDescription(tmpItem.getDescription());
        controller.editParameterInList(listId, p, response);
    }


    @Test
    public void deleteListTest() throws Exception{
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        SystemParameterList p = new SystemParameterList();
        p.setName("deleteTest" + Math.random()*500);
        p.setStateCode(ParameterList.ParameterListEnum.INITIAL);
        SystemParameterList tmp = (SystemParameterList) controller.addParameterList(p, httpServletResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/parameter-lists/" + tmp.getId()).contentType(MediaType.APPLICATION_JSON);;
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        SystemParameterList assertList = null;

        try {
            assertList = (SystemParameterList) controller.getParameterList(String.valueOf(tmp.getId()), httpServletResponse);
        } catch (Exception e){e.getMessage();}

        Assert.assertNull(assertList);
    }

    @Test
    public void deleteTest() throws Exception {

    }


}
