package com.ecobank.FcubsAccountCreation.services;

import com.ecobank.FcubsAccountCreation.dao.Response;
import com.ecobank.FcubsAccountCreation.restmodel.CorpCustRequest;
import com.ecobank.FcubsAccountCreation.restmodel.CreateAccountRequest;
import com.ecobank.FcubsAccountCreation.restmodel.GwtIndCustomerRequest;
import com.ecobank.FcubsAccountCreation.restmodel.GwtIndividualAcctRequest;

import java.io.IOException;

public interface AccountGatewayService {

     Response callAccountCreationGateway(CreateAccountRequest createAccountRequest) throws IOException;

     Response callCreateCustomerGateway(CorpCustRequest corpCustRequest) throws IOException;

     Response callCreateIndividualCustomerGateway(GwtIndCustomerRequest gwtIndCustomerRequest) throws IOException;

     Response callIndividualAccountCreationGateway(GwtIndividualAcctRequest gwtIndividualAcctRequest) throws IOException;
}
