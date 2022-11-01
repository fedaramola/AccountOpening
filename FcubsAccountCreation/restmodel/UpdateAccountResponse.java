package com.ecobank.FcubsAccountCreation.restmodel;

import lombok.Data;

@Data
public class UpdateAccountResponse {
    private String respMsg;
    private String respCode;
    private String refNum;
    private String account;
}
