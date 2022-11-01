package com.ecobank.FcubsAccountCreation.dao;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.HashMap;
@JsonAutoDetect
@Data
public class Response {
    private String responseCode;
    private String responseMessage;
    private String object;

    private Object data;
    private final HashMap<String, Object> artefacts = new HashMap<>();

    public Response(String s, String message) {
    }

    public Response() {
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object addArtefact(String key, Object value) {
        return artefacts.put(key, value);
    }

    public Object getArtefact(String key) {
        return artefacts.get(key);
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseCode='" + responseCode + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                ", data=" + data +
                '}';
    }
}
