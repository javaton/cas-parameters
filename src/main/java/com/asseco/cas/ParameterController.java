package com.asseco.cas;


import com.asseco.cas.interfaces.ParameterInterface;
import com.asseco.cas.parameters.domain.Parameter;
import com.asseco.cas.parameters.exceptions.checked.ParameterListNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@ComponentScan
@RestController
@EnableAutoConfiguration
public class ParameterController {

    private ParameterInterface paraService;



    @Autowired
    public ParameterController(ParameterInterface paraService){
        this.paraService = paraService;
    }

    //Test metoda OVO CE BITI IZBRISANO
    @RequestMapping(value = "/parameters", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Parameter> readAll() {
        return paraService.readList();
    }


    @RequestMapping(value = "/parameter", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Parameter addParameter(@RequestBody Parameter parameter, HttpServletResponse response){
        boolean check1 = parameter.getKey() == null ? false : true;
        boolean check2 = parameter.getValue() == null ? false : true;
        boolean check3 = parameter.getDescription() == null ? false : true;

        if(check1 && check2 && check3) {
            try {
                paraService.save(parameter);
                response.setStatus(201);
                return findById(parameter.getId(), response);
            } catch (Exception e){
                response.setStatus(400);
                return null;
            }
        }
        response.setStatus(400);
        return null;
    }


    @RequestMapping (value = "/parameter", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Parameter updateParameter (@RequestBody Parameter parameter, HttpServletResponse response){
        Parameter p = paraService.update(parameter);
        if (!(p==null)) {
            response.setStatus(200);
            return findById(parameter.getId(), response);
        }
        response.setStatus(400);
        return null;

    }

    @RequestMapping(value = "/parameter/{list}", method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "list")Long idParameterList, @RequestBody Parameter parameter, HttpServletResponse response){
        try {
            response.setStatus(204);
            paraService.delete(idParameterList, parameter);
            return ("Successfully deleted Parameter with ID: " + String.valueOf(parameter.getId()));
        } catch (ParameterListNotFoundException e) {
            response.setStatus(400);
            System.out.println(e.getStackTrace());
            return e.getMessage();
        }

    }

    @RequestMapping(value = "/parameter/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Parameter findById(@PathVariable(value = "id")Long id, HttpServletResponse response){
        Parameter p = paraService.findById(id);
        if (!(p==null)) {
            if(response==null)
                response.setStatus(200);
            return p;
        }
        response.setStatus(400);
        return null;
    }

    @RequestMapping (value = "/parameters/{name:[\\D]+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Parameter> allFromList(@PathVariable(value = "name")String name, HttpServletResponse response){
        try {
            ArrayList<Parameter> p = (ArrayList<Parameter>) paraService.findAllParameterFromList(name);
            if (!(p.isEmpty())){
                response.setStatus(200);
                return p;
            } else {
                response.setStatus(400);
                return null;
            }
        } catch (Exception e){response.setStatus(400); return null;}



    }

    @RequestMapping (value = "/parameters/{id:[\\d]+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Parameter> allFromList(@PathVariable(value = "id")Long id, HttpServletResponse response){
        ArrayList<Parameter> p = (ArrayList<Parameter>) paraService.findAllParameterFromList(id);

        if (!(p.isEmpty())){
            response.setStatus(200);
            return p;
        } else {
            response.setStatus(400);
            return null;
        }

    }


    @RequestMapping (value = "/parameter/{list}/{key}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Parameter getByName(@PathVariable(value = "list")String listName, @PathVariable(value = "key")String parameterKey, HttpServletResponse response){
        Parameter p = paraService.getParameterFromListByName(listName, parameterKey);
        if (p!=null) {
            response.setStatus(200);
            return p;
        } else {
            response.setStatus(400);
            return null;
        }
    }

}