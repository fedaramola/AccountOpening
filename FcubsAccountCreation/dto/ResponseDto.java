package com.ecobank.FcubsAccountCreation.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseDto<T> {
    private String message;
    private String responseCode;
    private T payload;
}
