package com.ecobank.FcubsAccountCreation.controller;

import com.ecobank.FcubsAccountCreation.dao.Response;
import com.ecobank.FcubsAccountCreation.dao.ResponseDuo;
import com.ecobank.FcubsAccountCreation.restmodel.CustomerAccountCreationReq;
import com.ecobank.FcubsAccountCreation.restmodel.CustomerUpdateReq;
import com.ecobank.FcubsAccountCreation.restmodel.GetAccount;
import com.ecobank.FcubsAccountCreation.restmodel.PndRequest;
import com.ecobank.FcubsAccountCreation.services.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountOpeningController {

    @Autowired
    private Processor processor;

    @RequestMapping(value = "/genericAccountOpening", method = RequestMethod.POST)
    public Response flexcubeAccountOpening(@RequestBody CustomerAccountCreationReq customerAccountCreationReq) throws NoSuchFieldException, IllegalAccessException {

        return processor.processRequest(customerAccountCreationReq);
    }

    @RequestMapping(value = "/getAccount", method = RequestMethod.POST)
    public Response getAccount(@RequestBody GetAccount getAccount) {
        return processor.getAccount(getAccount);
    }

    @RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
    public Response UpdateCustomer(@RequestBody CustomerUpdateReq customerUpdateReq) throws Exception {
        return processor.updateDetails(customerUpdateReq);
    }

    @RequestMapping(value = "/accountPnd", method = RequestMethod.POST)
    public ResponseDuo PlacedRemovePnd(@RequestBody PndRequest pndRequest) {
        return processor.PlacedOrRemovePnd(pndRequest);
    }

}
