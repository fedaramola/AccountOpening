package com.ecobank.FcubsAccountCreation.restmodel;

/**
 * @Author fedaramola on 2/5/22
 */
public class GetAccount {

    private String trackRef;
    private String affCode;


    public String getTrackRef() {
        return trackRef;
    }

    public void setTrackRef(String trackRef) {
        this.trackRef = trackRef;
    }

    public String getAffCode() {
        return affCode;
    }

    public void setAffCode(String affCode) {
        this.affCode = affCode;
    }

    @Override
    public String toString() {
        return "GetAccount{" +
                "trackRef='" + trackRef + '\'' +
                ", affCode='" + affCode + '\'' +
                '}';
    }
}
