package com.asseco.cas.controllers;


import com.asseco.cas.facade.ParameterItemFacadeImpl;
import com.asseco.cas.facade.ParameterListFacadeImpl;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@ComponentScan
@RestController
@EnableAutoConfiguration
public class ParameterController {


    private ParameterListFacadeImpl parameterListFacadeImpl;
    private ParameterItemFacadeImpl parameterItemFacadeImpl;

    @Autowired
    public ParameterController(ParameterListFacadeImpl parameterListFacadeImpl, ParameterItemFacadeImpl parameterItemFacadeImpl){
        this.parameterListFacadeImpl = parameterListFacadeImpl;
        this.parameterItemFacadeImpl = parameterItemFacadeImpl;
    }


    @RequestMapping(value = "/parameter-lists", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParameterList> getParameterLists(HttpServletResponse response){
        List<ParameterList> list  = parameterListFacadeImpl.findAll();
        if (!list.isEmpty()){
            return list;
        } else {response.setStatus(400); return null;}
    }


    @RequestMapping(value = "/parameter-lists/{id}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ParameterList getParameterList(@PathVariable(value = "id")Long id, HttpServletResponse response){
        ParameterList list =  parameterListFacadeImpl.findById(id);
        if (!(list==null)){
            response.setStatus(200);
            return list;
        } else {response.setStatus(400); return null;}
    }


    //TODO Potrebno je napraviti razliku izmedju System i ApplicationParameterList, jer implementacija samo ParameterLista ne moze da se mapira iz JSON-a
    @RequestMapping(value = "/parameter-list", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ParameterList addParameterList(@RequestBody SystemParameterList parameterList, HttpServletResponse response){
        ParameterList list = parameterListFacadeImpl.store(parameterList);
        if (!(list==null)){
            response.setStatus(201);
            return list;
        } else {response.setStatus(400); return null;}
    }


    @RequestMapping(value = "/parameter-lists/{idList}", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem addParameterToList(@PathVariable(value = "idList") Long idList, @RequestBody ParameterItem parameterItem, HttpServletResponse response){
        ParameterItem pItem = parameterItemFacadeImpl.saveParameterToList(idList, parameterItem);
        if (!(pItem==null)){
            response.setStatus(201);
            return pItem;
        } else {response.setStatus(400); return null;}
    }


    @RequestMapping(value = "/parameter-lists", method = RequestMethod.PUT,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ParameterList editList(@RequestBody SystemParameterList parameterList, HttpServletResponse response){
        ParameterList list =  parameterListFacadeImpl.update(parameterList);
        if (!(list==null)){
            response.setStatus(200);
            return list;
        } else {response.setStatus(400); return null;}
    }

    @RequestMapping(value = "/parameter-lists/{idList}", method = RequestMethod.PUT,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem editParameterInList(@PathVariable(value = "idList") Long idList, @RequestBody ParameterItem parameterItem, HttpServletResponse response){
        ParameterItem pItem = parameterItemFacadeImpl.updateParameterInList(idList, parameterItem);
        if (!(pItem==null)){
            response.setStatus(200);
            return pItem;
        } else {response.setStatus(400); return null;}
    }


    //TODO void je definisan u DAO
    @RequestMapping(value = "/parameter-lists/{idList}", method = RequestMethod.DELETE,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteList(@PathVariable (value = "idList")Long idList, HttpServletResponse response){
        parameterListFacadeImpl.remove(idList);
    }


    /*@RequestMapping(value = "/parameter-item", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem addParameter(@RequestBody ParameterItem parameterItem, HttpServletResponse response){

        if(parameterItemFacadeImpl.store(parameterItem)) {
            response.setStatus(201);
            return findById(parameterItem.getId(), response);
        } else {
            response.setStatus(400);
            return null;
        }
    }*/



    /*@RequestMapping (value = "/parameter-item", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem updateParameter (@RequestBody ParameterItem parameterItem, HttpServletResponse response){
        ParameterItem p = parameterItemFacadeImpl.update(parameterItem);
        if (!(p==null)) {
            response.setStatus(200);
            return findById(p.getId(), response);
        }
        response.setStatus(400);
        return null;

    }*/


    @RequestMapping(value = "/parameter-item/{list}/{idParameter}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "list")Long idParameterList, @PathVariable(value = "idParameter")Long idParameter, HttpServletResponse response){
        response.setStatus(204);
        parameterItemFacadeImpl.delete(idParameterList, idParameter);
    }


    //@RequestMapping(value = "/parameter-item/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

    public ParameterItem findById(@PathVariable(value = "id")Long id, HttpServletResponse response){
        ParameterItem p = parameterItemFacadeImpl.findById(id);
        if (!(p==null)) {
            if(response==null)
                response.setStatus(200);
            return p;
        }
        response.setStatus(400);
        return null;
    }



    @RequestMapping (value = "/parameter-items/{name:[\\D]+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParameterItem> allFromList(@PathVariable(value = "name")String name, HttpServletResponse response){
        List<ParameterItem> p = parameterItemFacadeImpl.findAllParameterFromList(name);
        if (!(p.isEmpty())){
            response.setStatus(200);
            return p;
        } else {
            response.setStatus(400);
            return null;
        }
    }



    @RequestMapping (value = "/parameter-items/{id:[\\d]+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParameterItem> allFromList(@PathVariable(value = "id")Long id, HttpServletResponse response){
        List<ParameterItem> p = parameterItemFacadeImpl.findAllParameterFromList(id);

        if (!(p.isEmpty())){
            response.setStatus(200);
            return p;
        } else {
            response.setStatus(400);
            return null;
        }

    }


    @RequestMapping (value = "/parameter-item/{list}/{key}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem getByName(@PathVariable(value = "list")String listName, @PathVariable(value = "key")String parameterKey, HttpServletResponse response){
        ParameterItem p = parameterItemFacadeImpl.getParameterFromListByName(listName, parameterKey);
        if (p!=null) {
            response.setStatus(200);
            return p;
        } else {
            response.setStatus(400);
            return null;
        }
    }






}
