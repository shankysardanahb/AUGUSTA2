package com.db.hk.controller;

import com.db.hk.DataDTO;
import com.db.hk.service.DataService;
import org.h2.jdbc.JdbcConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @Autowired
    DataService dataService;

    @GetMapping("/get/{id}")
    public DataDTO getData(@PathVariable String id) {
        System.out.println("Hello " + id);
        return dataService.test();
    }
}
