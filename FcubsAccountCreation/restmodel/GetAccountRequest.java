package com.ecobank.FcubsAccountCreation.restmodel;

/**
 * @Author talabiomotayo on 2/8/22
 */
public class GetAccountRequest {

   private String account;
    private String customer;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "GetAccountRequest{" +
                "account='" + account + '\'' +
                ", customer='" + customer + '\'' +
                '}';
    }
}
