package com.ecobank.FcubsAccountCreation.services;

import com.ecobank.FcubsAccountCreation.dao.Response;
import com.ecobank.FcubsAccountCreation.restmodel.OciCorporateRequest;
import com.ecobank.FcubsAccountCreation.restmodel.OciIndividualRequest;
import com.ecobank.FcubsAccountCreation.restmodel.UpdateAccountRequest;
import com.ecobank.FcubsAccountCreation.restmodel.UpdateCustomerRequest;

public interface AccountCreationService {
    Response ociCustomerAcctCreationMandate(OciIndividualRequest ociIndividualRequest);
    Response ociCustCorpAcctCreationMandate (OciCorporateRequest ociCustCorpReq);
    Response UpdateCustomer(UpdateCustomerRequest updateCustomerRequest);
    Response UpdateAccount(UpdateAccountRequest updateAccountRequest);

}
