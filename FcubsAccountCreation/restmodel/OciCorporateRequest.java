package com.ecobank.FcubsAccountCreation.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OciCorporateRequest {
    @JsonProperty("affCode")
    private String affiliateCode;
    @JsonProperty("source")
    private String sourceCode;
    @JsonProperty("requestId")
    private String requestId;
    @JsonProperty("customerType")
    private String custType;
    @JsonProperty("name")
    private String name;
    @JsonProperty("addrLn1")
    private String address1;
    @JsonProperty("addrLn2")
    private String address2;
    @JsonProperty("addrLn3")
    private String address3;
    @JsonProperty("addrLn4")
    private String address4;
    @JsonProperty("countryCode")
    private String countryCode;
    @JsonProperty("shortName")
    private String shortName;
    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("customerCategory")
    private String custCategory;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("uidName")
    private String uidName;
    @JsonProperty("uidValue")
    private String uidValue;
    @JsonProperty("location")
    private String location;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("bvn")
    private String bvn;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("corporateName")
    private String corporateName;
    @JsonProperty("dateIncorporated")
    private String dateIncorporated;
    @JsonProperty("companyAddress1")
    private String companyAddress1;
    @JsonProperty("companyAddress2")
    private String companyAddress2;
    @JsonProperty("companyAddress3")
    private String companyAddress3;
    @JsonProperty("companyAddress4")
    private String companyAddress4;
    @JsonProperty("language")
    private String language;
    @JsonProperty("companyName")
    private String companyName;
    @JsonProperty("telephone")
    private String telephone;
    @JsonProperty("emailId")
    private String emailId;
    @JsonProperty("mobileNumber")
    private String mobileNumber;
    @JsonProperty("liabilityName")
    private String liabilityName;
    @JsonProperty("accountClass")
    private String accountClass;
    @JsonProperty("flexCustId")
    private String flexCustId;
    @JsonProperty("branchCode")
    private String branchCode;
    @JsonProperty("accountName")
    private String accountName;
    @JsonProperty("accountAddress1")
    private String accountAddress1;
    @JsonProperty("accountAddress2")
    private String accountAddress2;
    @JsonProperty("accountAddress3")
    private String accountAddress3;
    @JsonProperty("accountAddress4")
    private String accountAddress4;
    @JsonProperty("mandateBase64")
    private String mandateBase64;
}
