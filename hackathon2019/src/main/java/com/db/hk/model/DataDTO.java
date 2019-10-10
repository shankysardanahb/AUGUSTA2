package com.db.hk.model;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class DataDTO {
    private String userName;
    private String customerId;
}
