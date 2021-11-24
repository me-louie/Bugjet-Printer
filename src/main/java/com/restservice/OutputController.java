package com.restservice;

import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class OutputController {


    @PostMapping(value = "/outputperson", consumes = "application/json", produces = "application/json")
        public String newRequest(@RequestBody Request newRequest) {

        //return JSONParser();
        return null;
    }
}