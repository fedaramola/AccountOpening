package com.ecobank.FcubsAccountCreation.restmodel;

import lombok.Data;

@Data
public class ValidateType {
    String customerType;
    String accountType;
    String mandate;
    String token;
}
