package com.ecobank.FcubsAccountCreation.utiils;

import java.util.ResourceBundle;

public class accountCreationProperties {

    private static ResourceBundle resource;

    public static String getMessage(String key) {
        if (resource == null)
            resource = ResourceBundle.getBundle("flex-creation-uat");
        return resource.getString(key);
    }

    public static void main(String[] args) {
        try {
            System.out.println("This " + getMessage("ENG"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
