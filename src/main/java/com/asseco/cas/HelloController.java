package com.asseco.cas;

import com.asseco.cas.DTO.ParameterRepresentation;
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
public class HelloController {

    private ParameterInterface paraService;

    @Autowired
    public HelloController(ParameterInterface paraService){
        this.paraService = paraService;
    }

    //Test metoda
    @RequestMapping(value = "/read", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<ParameterRepresentation> readAll() {
        return paraService.readList();
    }


    @RequestMapping(value = "/addParameter", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addParameter(@RequestBody Parameter parameter, HttpServletResponse response){
        paraService.save(parameter);
    }


    @RequestMapping (value = "/updateParameter", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Parameter updateParameter (@RequestBody Parameter parameter, HttpServletResponse response){
        Parameter p = paraService.update(parameter);
        if (!(p==null))
            return p;

        response.setStatus(400);
        return null;

    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void delete(@RequestParam(value = "idParamList")Long idParameterList, @RequestBody Parameter parameter){
        try {
            paraService.delete(idParameterList, parameter);
        } catch (ParameterListNotFoundException e) {
            System.out.println("ParameterListNotFoundException");
        }
    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Parameter findById(@PathVariable(value = "id")Long id, HttpServletResponse response){
        Parameter p = paraService.findById(id);
        if (!(p==null))
            return p;

        response.setStatus(400);
        return null;
    }

    @RequestMapping (value = "/allFromList/{name:[\\D]+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Parameter> allFromList(@PathVariable(value = "name")String name){
        return (ArrayList<Parameter>) paraService.findAllParameterFromList(name);
    }

    @RequestMapping (value = "/allFromList/{name:[\\d]+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Parameter> allFromList(@PathVariable(value = "name")Long id){
        return (ArrayList<Parameter>) paraService.findAllParameterFromList(id);
    }


    @RequestMapping (value = "/getParByName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Parameter getByName(@RequestParam(value = "listName")String listName, @RequestParam(value = "parameterKey")String parameterKey){
        Parameter p = paraService.getParameterFromListByName(listName, parameterKey);
        if (p!=null)
            return p;
        return null;
    }

}