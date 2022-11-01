package com.ecobank.FcubsAccountCreation.restmodel;

import lombok.Data;

@Data
public class PndRequest {
    private String account;
    private String pndAction;
    private String  pndReason;
    private String sourceCode;
    private String affiliate;
}
