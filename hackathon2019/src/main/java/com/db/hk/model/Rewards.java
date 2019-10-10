package com.db.hk.model;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Rewards {
    private String customerId;
    private int pointsEarned;
    private String month;
    private String amount;
    //private String totalPoints;
}
