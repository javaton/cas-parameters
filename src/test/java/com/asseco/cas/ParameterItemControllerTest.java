
package com.asseco.cas;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@RunWith(SpringRunner.class)
//@ActiveProfiles("local")
//@WebMvcTest(ParameterController.class)
public class ParameterItemControllerTest {

//
//    @Autowired
    private MockMvc mockMvc;
//
//
//    //Ovaj Autowire radi samo za local, treba odraditi isto za DAO
//    @Autowired
    private ParameterItemService parameterService;


//    @Test
    public void findByIdTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/parameter/5").accept(
                MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        String expected = "{\"id\":5}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);

    }


//    @Test
    public void saveParameterTest() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/parameter")
                .accept(MediaType.APPLICATION_JSON).content("{\"key\" : \"5\", \"value\" : \"value\", \"description\" : \"descriptionValue\"}")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

//
//    @Test
    public void updateParameterTest() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/parameter")
                .accept(MediaType.APPLICATION_JSON).content("{\"id\" : \"5\", \"key\" : \"5\", \"value\" : \"value\", \"description\" : \"descriptionValue\"}")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

//    @Test
    public void deleteParameterTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/parameter/1")
                .accept(MediaType.APPLICATION_JSON).content("{\"id\" : \"48\"}")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }


//    @Test
    public void allFromListStringTest() throws Exception {
        String listName = "ListName";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/parameterItems/" + listName).accept(
                MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();


    }


//    @Test
    public void allFromListIntTest() throws Exception {
        String listNumber = "1";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/parameterItems/" + listNumber).accept(
                MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
    }


//    @Test
    public void getByNameTest() throws Exception {
        String listName = "ListName";
        String key = "5";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/parameter/" + listName + "/" + key)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        String expected = "{\"key\":\"5\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }


}

