package com.ecobank.FcubsAccountCreation.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateAccountRequest {
    @JsonProperty("affCode")
    private String AFFCODE;
    @JsonProperty("source")
    private String SOURCE;
    @JsonProperty("msgId")
    private String MSGID;
    @JsonProperty("correlId")
    private String CORRELID;
    @JsonProperty("userId")
    private String USERID;
    @JsonProperty("branch")
    private String BRN;
    @JsonProperty("account")
    private String ACC;
    @JsonProperty("custNo")
    private String CUSTNO;
    @JsonProperty("accountClass")
    private String ACCLS;
    @JsonProperty("currency")
    private String CCY;
    @JsonProperty("accountName")
    private String ADESC;
    @JsonProperty("actStatNoDr")
    private String ACSTATNODR;
    @JsonProperty("acStatNoCr")
    private String ACSTATNOCR;
    @JsonProperty("frozen")
    private String FROZEN;
    @JsonProperty("address_1")
    private String ADDRESS_1;
    @JsonProperty("address_2")
    private String ADDRESS_2;
    @JsonProperty("address_3")
    private String ADDRESS_3;
    @JsonProperty("address_4")
    private String ADDRESS_4;
    @JsonProperty("postAllowed")
    private String POSTALLOWED;
    @JsonProperty("location")
    private String LOC;
    @JsonProperty("chqBook")
    private String CHQBOOK;
    @JsonProperty("passBook")
    private String PASSBOOK;
    @JsonProperty("atm")
    private String ATM;
}
