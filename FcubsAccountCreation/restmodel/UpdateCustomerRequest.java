package com.ecobank.FcubsAccountCreation.restmodel;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateCustomerRequest {
    private String affiliate;
    private String respMsg;
    private String respCode;
    private String refNum;
    private String flexCustomerId;
}
