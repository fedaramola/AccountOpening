package com.ecobank.FcubsAccountCreation.restmodel;

import lombok.Data;

@Data
public class OciIndividualRequest {

    private String affCode;
    private String firstName;
    private String middleName ;
    private String lastName ;
    private String dob;
    private String id_type ;
    private String idNo ;
    private String idIssuingDate;
    private String idExpiryDate ;
    private String phoneNumber ;
    private String  email ;
    private String gender ;
    private String address1;
    private String  address2;
    private String countryCode;
    private String custType ;
    private String custCategory ;
    private String brnCode ;
    private String ccy ;
    private String accountClass ;
    private String flexCustId ;
    private String mandateBase64;
}
