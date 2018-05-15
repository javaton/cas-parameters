package com.asseco.cas.controllers;


import com.asseco.cas.facade.ParameterItemFacadeImpl;
import com.asseco.cas.facade.ParameterListFacadeImpl;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
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


    @RequestMapping(value = "/parameter-lists", method = RequestMethod.GET)
    public List<ParameterList> getParameterLists(HttpServletResponse response){
        List<ParameterList> list  = parameterListFacadeImpl.findAll();
        if (!list.isEmpty()){
            return list;
        } else {response.setStatus(400); return null;}
    }


    @RequestMapping(value = "/parameter-lists/{id}", method = RequestMethod.GET)
    public Object getParameterList(@PathVariable(value = "id")String stringId, HttpServletResponse response){
        Long id = null;
        try{
            id = Long.parseLong(stringId);
        } catch(Exception e){
            return "List ID should be a numerical value";
        }


        ParameterList list =  parameterListFacadeImpl.findById(id);
        if (!(list==null)){
            response.setStatus(200);
            return list;
        } else {
            response.setStatus(400);
            if (id == null)
                return "Not a valid ID";

            return "No list with that ID";
        }
    }

    @RequestMapping (value = "/parameter-lists/{list}/{item}", method = RequestMethod.GET)
    public Object getItemFromList(@PathVariable(value = "list")String stringListId, @PathVariable(value = "item")String stringItemId, HttpServletResponse response){
        Long listId = null;
        Long itemId = null;
        try{
            listId = Long.parseLong(stringListId);
            itemId = Long.parseLong(stringItemId);
        } catch(Exception e){
            return "List and Item ID should be a numerical value";
        }

        ParameterItem p = parameterItemFacadeImpl.getParameterFromList(listId, itemId);
        if (p!=null) {
            response.setStatus(200);
            return p;
        } else {
            response.setStatus(400);

            if(listId == null)
                return "Not a valid List ID";
            if(itemId == null)
                return "Not a valid Item ID";

            return "No item with given ID";
        }
    }


    //TODO Potrebno je napraviti razliku izmedju System i ApplicationParameterList, jer implementacija samo ParameterLista ne moze da se mapira iz JSON-a
    @RequestMapping(value = "/parameter-lists", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object addParameterList(@RequestBody SystemParameterList parameterList, HttpServletResponse response){
        ParameterList list = parameterListFacadeImpl.store(parameterList);
        if (!(list==null)){
            response.setStatus(201);
            return getParameterList(parameterList.getId().toString(), response);
        } else {
            response.setStatus(400);
            return "Neki nas error";
        }
    }


    @RequestMapping(value = "/parameter-lists/{idList}", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object addParameterToList(@PathVariable(value = "idList") Long idList, @RequestBody ParameterItem parameterItem, HttpServletResponse response){
        ParameterItem pItem = parameterItemFacadeImpl.saveParameterToList(idList, parameterItem);
        if (!(pItem==null)){
            response.setStatus(201);
            return getItemFromList(idList.toString(), parameterItem.getId().toString(), response);
        } else {
            response.setStatus(400);
            return "Neki nas error";
        }
    }


    @RequestMapping(value = "/parameter-lists", method = RequestMethod.PUT,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object editList(@RequestBody SystemParameterList parameterList, HttpServletResponse response){
        ParameterList list =  parameterListFacadeImpl.update(parameterList);
        if (!(list==null)){
            response.setStatus(200);
            return getParameterList(parameterList.getId().toString(), response);
        } else {
            response.setStatus(400);
            return "Neki nas error";
        }
    }

    @RequestMapping(value = "/parameter-lists/{idList}", method = RequestMethod.PUT,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object editParameterInList(@PathVariable(value = "idList") Long idList, @RequestBody ParameterItem parameterItem, HttpServletResponse response){
        ParameterItem pItem = parameterItemFacadeImpl.updateParameterInList(idList, parameterItem);
        if (!(pItem==null)){
            response.setStatus(200);
            return getItemFromList(idList.toString(), parameterItem.getId().toString(), response);
        } else {
            response.setStatus(400);
            return "Neki nas Error";
        }
    }

    //TODO void je definisan u DAO

    @RequestMapping(value = "/parameter-lists/{idList}", method = RequestMethod.DELETE,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteList(@PathVariable (value = "idList")Long idList, HttpServletResponse response){
        response.setStatus(204);
        parameterListFacadeImpl.remove(idList);
    }


    @RequestMapping(value = "/parameter-lists/{list}/{idParameter}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "list")Long idParameterList, @PathVariable(value = "idParameter")Long idParameter, HttpServletResponse response){
        response.setStatus(204);
        parameterItemFacadeImpl.delete(idParameterList, idParameter);
    }

}
