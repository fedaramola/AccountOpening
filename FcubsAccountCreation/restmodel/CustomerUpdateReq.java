package com.ecobank.FcubsAccountCreation.restmodel;


import lombok.Data;

@Data
public class CustomerUpdateReq {
    private String affiliate;
    private String customerNo;
    private String idType;
    private String idNo;
    private String expireDate;
    private String issueDate;
    private String mobileNumber;
    private String email;

}
