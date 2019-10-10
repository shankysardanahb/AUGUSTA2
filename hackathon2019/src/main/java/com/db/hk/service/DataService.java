package com.db.hk.service;

import com.db.hk.model.DataDTO;
import com.db.hk.model.Rewards;

import java.sql.SQLException;

public interface DataService {
    DataDTO test() throws SQLException;
    Rewards getRewards(String customerId) throws SQLException;
    Boolean reset() throws SQLException;
}
