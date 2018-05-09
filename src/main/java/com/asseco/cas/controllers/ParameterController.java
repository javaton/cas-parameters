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

    @RequestMapping (value = "/parameter-lists/{list}/{item}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem getByName(@PathVariable(value = "list")Long listId, @PathVariable(value = "item")Long itemId, HttpServletResponse response){
        ParameterItem p = parameterItemFacadeImpl.getParameterFromList(listId, itemId);
        if (p!=null) {
            response.setStatus(200);
            return p;
        } else {
            response.setStatus(400);
            return null;
        }
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


    @RequestMapping(value = "/parameter-item/{list}/{idParameter}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "list")Long idParameterList, @PathVariable(value = "idParameter")Long idParameter, HttpServletResponse response){
        response.setStatus(204);
        parameterItemFacadeImpl.delete(idParameterList, idParameter);
    }


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



}
