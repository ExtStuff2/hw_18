package com.fun.clientpayment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReceiptDetailed {

    private String id;
    private String clientId;
    private String clientName;

    private String serviceName;
    private double servicePrice;
    private Map<String, Object> additionalServices;


}
