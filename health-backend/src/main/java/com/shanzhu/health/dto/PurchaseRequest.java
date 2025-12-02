package com.shanzhu.health.dto;

import lombok.Data;

@Data
public class PurchaseRequest {
    private String planType;
    private String paymentMethod;
}
