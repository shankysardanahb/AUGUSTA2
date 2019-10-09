package com.db.hk.service.impl;

import com.db.hk.DataDTO;
import com.db.hk.service.DataService;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
public class DataServiceImpl implements DataService {

    @Override
    public DataDTO test() {
        return DataDTO.builder().userName("Shashank").customerId("12345").build();
    }
}
