package com.db.hk.service.impl;

import com.db.hk.model.DataDTO;
import com.db.hk.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class DataServiceImpl implements DataService {

    @Autowired
    private DataSource dataSource;

    @Override
    public DataDTO test() throws SQLException {
        Connection connection = dataSource.getConnection();
        String schema = connection.getSchema();
        System.out.println("Successful connection - Schema: " + schema);

        // Create and execute a SELECT SQL statement.
        String selectSql = "SELECT TOP 20 pc.Name as CategoryName, p.name as ProductName "
                + "FROM [SalesLT].[ProductCategory] pc "
                + "JOIN [SalesLT].[Product] p ON pc.productcategoryid = p.productcategoryid";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSql)) {

            // Print results from select statement
            System.out.println("Top 20 categories:");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " "
                        + resultSet.getString(2));
            }
            connection.close();
        }
        return DataDTO.builder().userName("Shashank").customerId("12345").build();
    }
}