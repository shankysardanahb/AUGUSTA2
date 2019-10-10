package com.db.hk.controller;

import com.db.hk.model.DataDTO;
import com.db.hk.model.Rewards;
import com.db.hk.service.DataService;
import org.h2.jdbc.JdbcConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class DataController {

    @Autowired
    DataService dataService;

    @GetMapping("/get/{id}")
    public DataDTO getData(@PathVariable String id)  throws SQLException {
        System.out.println("Hello " + id);
        return dataService.test();
    }

    @GetMapping("/get-rewards/{id}")
    public Rewards getRewards(@PathVariable String id)  throws SQLException {
        System.out.println("CustomerId " + id);
        return dataService.getRewards(id);
    }

    @GetMapping("/reset")
    public Boolean reset()  throws SQLException {
        System.out.println("Reset");
        return dataService.reset();
    }
}
