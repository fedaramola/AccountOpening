package com.ecobank.FcubsAccountCreation.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CorpCustRequest {
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
    @JsonProperty("cType")
    private String CTYPE;
    @JsonProperty("name")
    private String NAME;
    @JsonProperty("addrLn1")
    private String ADDRLN1;
    @JsonProperty("addrLn2")
    private String ADDRLN2;
    @JsonProperty("addrLn3")
    private String ADDRLN3;
    @JsonProperty("addrLn4")
    private String ADDRLN4;
    @JsonProperty("country")
    private String COUNTRY;
    @JsonProperty("shortName")
    private String SNAME;
    @JsonProperty("nationality")
    private String NLTY;
    @JsonProperty("localBrn")
    private String LBRN;
    @JsonProperty("customerCategory")
    private String CCATEG;
    @JsonProperty("fullName")
    private String FULLNAME;
    @JsonProperty("uidName")
    private String UIDNAME;
    @JsonProperty("uidValue")
    private String UIDVAL;
    @JsonProperty("location")
    private String LOC;
    @JsonProperty("currency")
    private String LMCCY;
    @JsonProperty("bvn")
    private String UDF2;
    @JsonProperty("dob")
    private String UDF3;
    @JsonProperty("corporateName")
    private String CORPNAME;
    @JsonProperty("dateIncorporated")
    private String INCORPDT;
    @JsonProperty("companyAddreess1")
    private String CADDR1;
    @JsonProperty("companyAddreess2")
    private String CADDR2;
    @JsonProperty("companyAddreess3")
    private String CADDR3;
    @JsonProperty("companyAddreess4")
    private String CADDR4;
    @JsonProperty("language")
    private String LANGUAGE;
    @JsonProperty("companyName")
    private String CNAME;
    @JsonProperty("telephone")
    private String TELEPHONE;
    @JsonProperty("emailId")
    private String EMAILID;
    @JsonProperty("mobileNumber")
    private String MOBILENUMBER;
    @JsonProperty("liabilityName")
    private String LIABILTY_NAME;
}
