package com.ecobank.FcubsAccountCreation.dao;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.HashMap;

@JsonAutoDetect
@Data
public class ResponseDuo {
    private String responseCode;
    private String responseMessage;
    private String data;

}
