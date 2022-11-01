package com.ecobank.FcubsAccountCreation.services;

import com.ecobank.FcubsAccountCreation.dao.Response;
import com.ecobank.FcubsAccountCreation.restmodel.CustomerUpdateReq;

public interface CustomerUpdateService {
     Response callUpdatesCustomer(CustomerUpdateReq customerUpdateReq) throws Exception;
}
