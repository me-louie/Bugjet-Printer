package com.restservice;

import java.io.FileReader;
import java.util.List;


import ast.Main;
import com.google.gson.Gson;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        public Object newRequest(@RequestBody Request newRequest) {

        String jsonString = null;
        try {
            jsonString = Main.process(newRequest.program);
            return new ResponseEntity<>(jsonString, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        //return jsonString;
    }
}