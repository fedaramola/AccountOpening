package com.ecobank.FcubsAccountCreation.dto;

import com.ecobank.FcubsAccountCreation.constant.ResponseCodes;

public class ErrorResponse {
    private String message;
    private String responseCode;

    public ErrorResponse(ResponseCodes responseCodes, String errorMessage){
        this.responseCode = responseCodes.getCode();
        this.message = errorMessage;
    }
}
