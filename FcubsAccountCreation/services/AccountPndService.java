package com.ecobank.FcubsAccountCreation.services;

import com.ecobank.FcubsAccountCreation.dao.Response;
import com.ecobank.FcubsAccountCreation.dao.ResponseDuo;
import com.ecobank.FcubsAccountCreation.restmodel.PndRequest;

public interface AccountPndService {
     ResponseDuo PlacePndOrRemoved(PndRequest pndRequest) ;
}
