package com.asseco.cas;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        System.out.println("Ljubi brat console!");
        return "Ljubi brat!";
    }

}