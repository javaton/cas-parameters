package com.asseco.cas.controllers;


import com.asseco.cas.facade.ParameterItemFacade;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cass.application.ApplicationException;
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
public class ParameterItemController {


    private ParameterItemFacade parameterItemFacade;


    @Autowired
    public ParameterItemController(ParameterItemFacade parameterItemFacade){
        this.parameterItemFacade = parameterItemFacade;
    }


    @RequestMapping(value = "/parameter-items", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParameterItem> readAll() {
        return parameterItemFacade.readList();
    }


    @RequestMapping(value = "/parameter-item", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem addParameter(@RequestBody ParameterItem parameterItem, HttpServletResponse response){

            if(parameterItemFacade.store(parameterItem)) {
                response.setStatus(201);
                return findById(parameterItem.getId(), response);
            } else {
                response.setStatus(400);
                return null;
            }
    }


    @RequestMapping (value = "/parameter-item", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem updateParameter (@RequestBody ParameterItem parameterItem, HttpServletResponse response){
        ParameterItem p = parameterItemFacade.update(parameterItem);
        if (!(p==null)) {
            response.setStatus(200);
            return findById(p.getId(), response);
        }
        response.setStatus(400);
        return null;

    }

    @RequestMapping(value = "/parameter-item/{list}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "list")Long idParameterList, @RequestBody ParameterItem parameterItem, HttpServletResponse response){
        response.setStatus(204);
        parameterItemFacade.delete(idParameterList, parameterItem);
    }

    @RequestMapping(value = "/parameter-item/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem findById(@PathVariable(value = "id")Long id, HttpServletResponse response){
        ParameterItem p = parameterItemFacade.findById(id);
        if (!(p==null)) {
            if(response==null)
                response.setStatus(200);
            return p;
        }
        response.setStatus(400);
        return null;
    }

  /*  @RequestMapping(value = "/parameter-list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ParameterList insert(){
        ParameterList pl = new SystemParameterList();
        pl.setName("djole");
        pl.setStateCode(ParameterList.ParameterListEnum.ACTIVE);
        ParameterItem pi = new ParameterItem();
        pi.setKey("djole2");
        pi.setValue("333");
//        pi.setParameterList(pl);
        pl.addParameter(pi);
        parameterItemFacade.store(pl);
        return pl;
    }*/

    @RequestMapping (value = "/parameter-items/{name:[\\D]+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParameterItem> allFromList(@PathVariable(value = "name")String name, HttpServletResponse response){
        List<ParameterItem> p = parameterItemFacade.findAllParameterFromList(name);
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
        List<ParameterItem> p = parameterItemFacade.findAllParameterFromList(id);

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
        ParameterItem p = parameterItemFacade.getParameterFromListByName(listName, parameterKey);
        if (p!=null) {
            response.setStatus(200);
            return p;
        } else {
            response.setStatus(400);
            return null;
        }
    }

}