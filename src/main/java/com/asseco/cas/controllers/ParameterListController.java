package com.asseco.cas.controllers;


import com.asseco.cas.interfaces.ParameterListInterface;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@ComponentScan
@RestController
@EnableAutoConfiguration
public class ParameterListController {


    private ParameterListInterface parameterListInterface;

    @Autowired
    public ParameterListController(ParameterListInterface parameterValuesRepositoryImpl){
        this.parameterListInterface = parameterValuesRepositoryImpl;
    }


    @RequestMapping(value = "/parameter-lists", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParameterList> getParameterLists(HttpServletResponse response){
        List<ParameterList> list  = parameterListInterface.readList();
        if (!list.isEmpty()){
            return list;
        } else {response.setStatus(400); return null;}
    }


    @RequestMapping(value = "/parameter-lists/{id}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ParameterList getParameterList(@PathVariable(value = "id")Long id, HttpServletResponse response){
        ParameterList list =  parameterListInterface.findById(id);
        if (!(list==null)){
            response.setStatus(200);
            return list;
        } else {response.setStatus(400); return null;}
    }


    @RequestMapping(value = "/parameter/{idList}/{idItem}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem getParameterItemFromList(@PathVariable(value = "idList")Long idList, @PathVariable(value = "idItem")Long idItem, HttpServletResponse response){
        ParameterItem parameterItem = parameterListInterface.getParameterItem(idList, idItem);
        if (!(parameterItem==null)){
            response.setStatus(200);
            return parameterItem;
        } else {response.setStatus(400); return null;}
    }


    //Potrebno je napraviti razliku izmedju System i ApplicationParameterList, jer implementacija samo ParameterLista ne moze da se mapira iz JSON-a
    @RequestMapping(value = "/parameter-list", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ParameterList addParameterList(@RequestBody SystemParameterList parameterList, HttpServletResponse response){
        ParameterList list = parameterListInterface.save(parameterList);
        if (!(list==null)){
            response.setStatus(201);
            return list;
        } else {response.setStatus(400); return null;}
    }


    @RequestMapping(value = "/parameter-lists/{idList}", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem addParameterToList(@PathVariable(value = "idList") Long idList, @RequestBody ParameterItem parameterItem, HttpServletResponse response){
        ParameterItem pItem = parameterListInterface.saveParameterToList(idList, parameterItem);
        if (!(pItem==null)){
            response.setStatus(201);
            return pItem;
        } else {response.setStatus(400); return null;}
    }


    @RequestMapping(value = "/parameter-lists", method = RequestMethod.PUT,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ParameterList editList(@RequestBody SystemParameterList parameterList, HttpServletResponse response){
        ParameterList list =  parameterListInterface.update(parameterList);
        if (!(list==null)){
            response.setStatus(200);
            return list;
        } else {response.setStatus(400); return null;}
    }

    @RequestMapping(value = "/parameter-lists/{idList}", method = RequestMethod.PUT,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem editParameterInList(@PathVariable(value = "idList") Long idList, @RequestBody ParameterItem parameterItem, HttpServletResponse response){
        ParameterItem pItem = parameterListInterface.updateParameterInList(idList, parameterItem);
        if (!(pItem==null)){
            response.setStatus(200);
            return pItem;
        } else {response.setStatus(400); return null;}
    }


    //void je definisan u DAO
    @RequestMapping(value = "/parameter-lists", method = RequestMethod.DELETE,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteList(@RequestBody SystemParameterList parameterList, HttpServletResponse response){
        parameterListInterface.delete(parameterList);
    }


    @RequestMapping(value = "/parameter-lists/{idList}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteParameterFromList(@PathVariable(value = "idList") Long idList, @RequestBody ParameterItem parameterItem, HttpServletResponse response){
        parameterListInterface.deleteFromList(idList, parameterItem);
    }


}
