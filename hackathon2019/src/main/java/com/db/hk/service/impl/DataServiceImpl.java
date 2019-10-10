package com.db.hk.service.impl;

import com.db.hk.model.DataDTO;
import com.db.hk.model.Rewards;
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

    @Override
    public Rewards getRewards(String customerIdStr) throws SQLException {
        try {
            Connection connection = dataSource.getConnection();
            String schema = connection.getSchema();
            System.out.println("Successful connection - Schema: " + schema);

            System.out.println("=========================================");

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT sum(Amount) as Amount, "
                    + "CustomerID, "
                    + "MONTH(TransactionDate) as Month, "
                    + "count(CustomerID) as Count "
                    + "from customerInfo cd "
                    + "JOIN CustomerStatement cs  ON cd.ID = cs.CustomerID "
                    + "and (cs.TransactionDescription like '%FUEL%' "
                    + "or cs.TransactionDescription LIKE '%PETROL%' "
                    + "or cs.TransactionDescription LIKE '%PUMP%' "
                    + "or cs.TransactionDescription LIKE '%OIL%' "
                    + "or cs.TransactionDescription LIKE '%DIESEL%' "
                    + "or cs.TransactionDescription LIKE '%SHELL%') "
                    + "group by cs.CustomerID ,  MONTH(TransactionDate) "
                    + "having MONTH(TransactionDate) = MONTH(GETDATE()) -1 "
                    + "and CustomerID = " + customerIdStr;

            String insertSql = "INSERT INTO customerRewards(" + "CustomerID," + "PointsEarned," + "Month_ID,"
                    + "Amount/*," + "TotalPoints*/)" + "VALUES(?,?,?,?/*,?*/)";

            PreparedStatement pstmt = connection.prepareStatement(insertSql);

            try (Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
                 ResultSet resultSet = statement.executeQuery(selectSql)) {

                System.out.println("Fetching the Fuel debit entries and the rewards as per criteria:");
                while (resultSet.next()) {
                    int amount = resultSet.getInt(1);
                    String customerID = resultSet.getString(2);
                    String month = resultSet.getString(3);
                    String count = resultSet.getString(4);
                    int pointsEarned = 0;

                    if (amount <= 2000) {
                        pointsEarned = (amount * 6 / 100);
                    } else {
                        pointsEarned = 0;
                    }

                    pstmt.setString(1, customerID);
                    pstmt.setInt(2, pointsEarned);
                    pstmt.setString(3, month);
                    pstmt.setInt(4, amount);
                    //pstmt.setString(5, TotalPoints);
                    pstmt.executeUpdate();

                    return Rewards.builder()
                            .customerId(customerID)
                            .month(month)
                            .pointsEarned(pointsEarned)
                            .amount(amount +"").build();
                }
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean reset() throws SQLException {
        Connection connection = dataSource.getConnection();
        String schema = connection.getSchema();
        System.out.println("Reset ");
        String selectSql = "TRUNCATE TABLE customerRewards";

        try (Statement statement = connection.createStatement()) {
            statement.execute(selectSql);
            connection.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}