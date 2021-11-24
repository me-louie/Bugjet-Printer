package com.restservice;

import ast.Main;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class OutputController {

    @PostMapping(value = "/output", consumes = "application/json", produces = "application/json")
        public Object newRequest(@RequestBody Request newRequest) {

        String jsonString;
        try {
            jsonString = Main.process(newRequest.program);
            return new ResponseEntity<>(jsonString, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        //return jsonString;
    }
}