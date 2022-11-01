package com.ecobank.FcubsAccountCreation.services;

import com.ecobank.FcubsAccountCreation.dao.Response;
import com.ecobank.FcubsAccountCreation.dao.ResponseDuo;
import com.ecobank.FcubsAccountCreation.repository.ValidateClient;
import com.ecobank.FcubsAccountCreation.restmodel.*;
import com.ecobank.FcubsAccountCreation.services.implementation.AccountGwtServiceImplementation;
import com.ecobank.FcubsAccountCreation.services.implementation.AccountServiceImplementation;
import com.ecobank.FcubsAccountCreation.utiils.HttpUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("Processor")
public class Processor {
    private final Logger logger = Logger.getLogger(Processor.class);
    String createMode = "";

    @Autowired
    private AccountGwtServiceImplementation accountGwtServiceImplementation;
    @Autowired
    private AccountServiceImplementation accountServiceImplementation;
    @Autowired
    private ValidateClient validateClient;
    @Autowired
    private CustomerUpdateService customerUpdateService;

    @Autowired
    private AccountPndService accountPndService;


    public Response processRequest(CustomerAccountCreationReq cacRequest) throws NoSuchFieldException, IllegalAccessException {
        Response response = new Response();
        Response responseTanked = null;
        Response responseUpdate = null;
        HttpUtils httpUtils = new HttpUtils();
        logger.info(" cacRequest ================ payload " + cacRequest.getAffiliateCode());

        createMode = validateClient.getCreationType(cacRequest.getAffiliateCode(), cacRequest.getCustType(),
                cacRequest.getCreateModeAccountType(), cacRequest.getCreateModeMandateReq(), cacRequest.getRequestToken());

        logger.info(" createMode ================ response " + createMode + " cacRequest.getCustType() " + cacRequest.getCustType());

        if ((createMode).equalsIgnoreCase("OCI") && cacRequest.getCustType().equalsIgnoreCase("I")) {

            OciIndividualRequest ociIndividualRequest = new OciIndividualRequest();
            ociIndividualRequest.setAffCode(cacRequest.getAffiliateCode());
            ociIndividualRequest.setFirstName(cacRequest.getFirstName());
            ociIndividualRequest.setMiddleName(cacRequest.getMiddleName());
            ociIndividualRequest.setLastName(cacRequest.getLastName());
            ociIndividualRequest.setDob(cacRequest.getDob());
            ociIndividualRequest.setId_type(cacRequest.getId_type());
            ociIndividualRequest.setIdNo(cacRequest.getIdNo());
            ociIndividualRequest.setIdIssuingDate(cacRequest.getIdIssuingDate());
            ociIndividualRequest.setIdExpiryDate(cacRequest.getIdExpiryDate());
            ociIndividualRequest.setPhoneNumber(cacRequest.getMobileNumber());
            ociIndividualRequest.setEmail(cacRequest.getEmailId());
            ociIndividualRequest.setGender(cacRequest.getGender());
            ociIndividualRequest.setAddress1(cacRequest.getAddress1());
            ociIndividualRequest.setAddress2(cacRequest.getAddress2());
            ociIndividualRequest.setCountryCode(cacRequest.getCountryCode());
            ociIndividualRequest.setCustType(cacRequest.getCustType());
            ociIndividualRequest.setCustCategory(cacRequest.getCustCategory());
            ociIndividualRequest.setBrnCode(cacRequest.getBranchCode());
            ociIndividualRequest.setCcy(cacRequest.getCurrency());
            ociIndividualRequest.setAccountClass(cacRequest.getAccountClass());
            ociIndividualRequest.setFlexCustId(cacRequest.getFlexCustId());
            ociIndividualRequest.setMandateBase64(cacRequest.getMandateBase64());

            logger.info(" accountServiceImplementation.ociCustomerAcctCreationMandate ================ payload " + ociIndividualRequest);

            return accountServiceImplementation.ociCustomerAcctCreationMandate(ociIndividualRequest);


        } else if (createMode.equalsIgnoreCase("OCI") && cacRequest.getCustType().equalsIgnoreCase("C")) {

            OciCorporateRequest ociCorporateRequest = new OciCorporateRequest();
            ociCorporateRequest.setAffiliateCode(cacRequest.getAffiliateCode());
            ociCorporateRequest.setSourceCode(cacRequest.getSourceCode());
            ociCorporateRequest.setRequestId(cacRequest.getRequestId());
            ociCorporateRequest.setCustType(cacRequest.getCustType());
            ociCorporateRequest.setName(cacRequest.getName());
            ociCorporateRequest.setAddress1(cacRequest.getAddress1());
            ociCorporateRequest.setAddress2(cacRequest.getAddress2());
            ociCorporateRequest.setAddress3(cacRequest.getAddress3());
            ociCorporateRequest.setAddress4(cacRequest.getAddress4());
            ociCorporateRequest.setCountryCode(cacRequest.getCountryCode());
            ociCorporateRequest.setShortName(cacRequest.getShortName());
            ociCorporateRequest.setNationality(cacRequest.getNationality());
            ociCorporateRequest.setCustCategory(cacRequest.getCustCategory());
            ociCorporateRequest.setFullName(cacRequest.getFullName());
            ociCorporateRequest.setUidName(cacRequest.getUidName());
            ociCorporateRequest.setUidValue(cacRequest.getUidValue());
            ociCorporateRequest.setLocation(cacRequest.getLocation());
            ociCorporateRequest.setCurrency(cacRequest.getCurrency());
            ociCorporateRequest.setBvn(cacRequest.getBvn());
            ociCorporateRequest.setDob(cacRequest.getDob());
            ociCorporateRequest.setCorporateName(cacRequest.getCorporateName());
            ociCorporateRequest.setDateIncorporated(cacRequest.getDateIncorporated());
            ociCorporateRequest.setCompanyAddress1(cacRequest.getCompanyAddress1());
            ociCorporateRequest.setCompanyAddress2(cacRequest.getCompanyAddress2());
            ociCorporateRequest.setCompanyAddress3(cacRequest.getCompanyAddress3());
            ociCorporateRequest.setCompanyAddress4(cacRequest.getCompanyAddress4());
            ociCorporateRequest.setLanguage(cacRequest.getLanguage());
            ociCorporateRequest.setCompanyName(cacRequest.getCompanyName());
            ociCorporateRequest.setTelephone(cacRequest.getTelephone());
            ociCorporateRequest.setEmailId(cacRequest.getEmailId());
            ociCorporateRequest.setMobileNumber(cacRequest.getMobileNumber());
            ociCorporateRequest.setLiabilityName(cacRequest.getLiabilityName());
            ociCorporateRequest.setAccountClass(cacRequest.getAccountClass());
            ociCorporateRequest.setFlexCustId(cacRequest.getFlexCustId());
            ociCorporateRequest.setBranchCode(cacRequest.getBranchCode());
            ociCorporateRequest.setAccountName(cacRequest.getAccountName());
            ociCorporateRequest.setAccountAddress1(cacRequest.getAccountAddress1());
            ociCorporateRequest.setAccountAddress2(cacRequest.getAccountAddress2());
            ociCorporateRequest.setAccountAddress3(cacRequest.getAccountAddress3());
            ociCorporateRequest.setAccountAddress4(cacRequest.getAccountAddress4());
            ociCorporateRequest.setMandateBase64(cacRequest.getMandateBase64());

            logger.info(" accountServiceImplementation.ociCustCorpAcctCreationMandate ================ payload " + ociCorporateRequest);

            return accountServiceImplementation.ociCustCorpAcctCreationMandate(ociCorporateRequest);


        } else if (createMode.equalsIgnoreCase("GWT") && cacRequest.getCustType().equalsIgnoreCase("C")) {

            String dob = null;
            try {
                dob = httpUtils.formateDate(cacRequest.getDob());
                logger.info("dob inside corp " + dob);
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("Dob " + dob);

            // tanked  the request
            OciCorporateRequest ociCorporateReq = new OciCorporateRequest();
            ociCorporateReq.setAffiliateCode(cacRequest.getAffiliateCode());
            ociCorporateReq.setSourceCode(cacRequest.getSourceCode());
            ociCorporateReq.setRequestId(cacRequest.getRequestId());
            ociCorporateReq.setCustType(cacRequest.getCustType());
            ociCorporateReq.setName(cacRequest.getName());
            ociCorporateReq.setAddress1(cacRequest.getAddress1());
            ociCorporateReq.setAddress2(cacRequest.getAddress2());
            ociCorporateReq.setAddress3(cacRequest.getAddress3());
            ociCorporateReq.setAddress4(cacRequest.getAddress4());
            ociCorporateReq.setCountryCode(cacRequest.getCountryCode());
            ociCorporateReq.setShortName(cacRequest.getShortName());
            ociCorporateReq.setNationality(cacRequest.getNationality());
            ociCorporateReq.setCustCategory(cacRequest.getCustCategory());
            ociCorporateReq.setFullName(cacRequest.getFullName());
            ociCorporateReq.setUidName(cacRequest.getUidName());
            ociCorporateReq.setUidValue(cacRequest.getUidValue());
            ociCorporateReq.setLocation(cacRequest.getLocation());
            ociCorporateReq.setCurrency(cacRequest.getCurrency());
            ociCorporateReq.setBvn(cacRequest.getBvn());
            ociCorporateReq.setDob(cacRequest.getDob());
            ociCorporateReq.setCorporateName(cacRequest.getCorporateName());
            ociCorporateReq.setDateIncorporated(cacRequest.getDateIncorporated());
            ociCorporateReq.setCompanyAddress1(cacRequest.getCompanyAddress1());
            ociCorporateReq.setCompanyAddress2(cacRequest.getCompanyAddress2());
            ociCorporateReq.setCompanyAddress3(cacRequest.getCompanyAddress3());
            ociCorporateReq.setCompanyAddress4(cacRequest.getCompanyAddress4());
            ociCorporateReq.setLanguage(cacRequest.getLanguage());
            ociCorporateReq.setCompanyName(cacRequest.getCompanyName());
            ociCorporateReq.setTelephone(cacRequest.getTelephone());
            ociCorporateReq.setEmailId(cacRequest.getEmailId());
            ociCorporateReq.setMobileNumber(cacRequest.getMobileNumber());
            ociCorporateReq.setLiabilityName(cacRequest.getLiabilityName());
            ociCorporateReq.setAccountClass(cacRequest.getAccountClass());
            ociCorporateReq.setFlexCustId("STD");
            ociCorporateReq.setBranchCode(cacRequest.getBranchCode());
            ociCorporateReq.setAccountName(cacRequest.getAccountName());
            ociCorporateReq.setAccountAddress1(cacRequest.getAccountAddress1());
            ociCorporateReq.setAccountAddress2(cacRequest.getAccountAddress2());
            ociCorporateReq.setAccountAddress3(cacRequest.getAccountAddress3());
            ociCorporateReq.setAccountAddress4(cacRequest.getAccountAddress4());
            ociCorporateReq.setMandateBase64(cacRequest.getMandateBase64());

            logger.info(" accountServiceImplementation.ociCustCorpAcctCreationMandate ================ payload " + ociCorporateReq);

            responseTanked = accountServiceImplementation.ociCustCorpAcctCreationMandate(ociCorporateReq);
            logger.info("Taked  Respose Data " + responseTanked);


            try {
                CorpCustRequest corpCustRequest = new CorpCustRequest();
                corpCustRequest.setAFFCODE(cacRequest.getAffiliateCode());
                corpCustRequest.setSOURCE(cacRequest.getSourceCode());
                corpCustRequest.setMSGID(cacRequest.getRequestId());
                corpCustRequest.setCORRELID(cacRequest.getRequestId());
                corpCustRequest.setUSERID(cacRequest.getSourceCode());
                corpCustRequest.setCTYPE(cacRequest.getCustType());
                corpCustRequest.setNAME(cacRequest.getName());
                corpCustRequest.setADDRLN1(cacRequest.getAddress1());
                corpCustRequest.setADDRLN2(cacRequest.getAddress2());
                corpCustRequest.setADDRLN3(cacRequest.getAddress3());
                corpCustRequest.setADDRLN4(cacRequest.getAddress4());
                corpCustRequest.setCOUNTRY(cacRequest.getCountryCode());
                corpCustRequest.setSNAME(cacRequest.getShortName());
                corpCustRequest.setNLTY(cacRequest.getNationality());
                corpCustRequest.setLBRN(cacRequest.getAffiliateCode());
                corpCustRequest.setCCATEG(cacRequest.getCustCategory());
                corpCustRequest.setFULLNAME(cacRequest.getFullName());
                corpCustRequest.setUIDNAME(cacRequest.getUidName());
                corpCustRequest.setUIDVAL(cacRequest.getUidValue());
                corpCustRequest.setLOC(cacRequest.getLocation());
                corpCustRequest.setLMCCY(cacRequest.getCurrency());
                corpCustRequest.setUDF2(cacRequest.getBvn());
                corpCustRequest.setUDF3(dob);
                corpCustRequest.setCORPNAME(cacRequest.getCompanyName());
                corpCustRequest.setINCORPDT(cacRequest.getDateIncorporated());
                corpCustRequest.setCADDR1(cacRequest.getCompanyAddress1());
                corpCustRequest.setCADDR2(cacRequest.getCompanyAddress2());
                corpCustRequest.setCADDR3(cacRequest.getCompanyAddress3());
                corpCustRequest.setCADDR4(cacRequest.getCompanyAddress4());
                corpCustRequest.setLANGUAGE(cacRequest.getLanguage());
                corpCustRequest.setCNAME(cacRequest.getCompanyName());
                corpCustRequest.setTELEPHONE(cacRequest.getTelephone());
                corpCustRequest.setEMAILID(cacRequest.getEmailId());
                corpCustRequest.setMOBILENUMBER(cacRequest.getMobileNumber());
                corpCustRequest.setLIABILTY_NAME(cacRequest.getLiabilityName());

                logger.info(" accountGwtServiceImplementation.callCreateCustomerGateway ================ payload " + corpCustRequest);

                response = accountGwtServiceImplementation.callCreateCustomerGateway(corpCustRequest);

                logger.info("Create Customer Response Data : - " + response);


            } catch (Exception ex) {
                ex.printStackTrace();
                logger.info(ex.getMessage());
                response.setResponseCode("77");
                response.setResponseMessage("Failed");
            }

            logger.info("abt  to update customer status ....");
            UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest();
            updateCustomerRequest.setAffiliate(cacRequest.getAffiliateCode());
            updateCustomerRequest.setRespMsg(response.getResponseMessage());
            updateCustomerRequest.setRespCode(response.getResponseCode());
            updateCustomerRequest.setRefNum(String.valueOf(responseTanked.getData()));
            updateCustomerRequest.setFlexCustomerId(response.getObject());

            Response resp = accountServiceImplementation.UpdateCustomer(updateCustomerRequest);
            logger.info("Customer Update Response " + resp);


            if (!response.getResponseCode().equalsIgnoreCase("000")) {
                return responseTanked;
            } else {


                logger.info(" about to call create accout ++ ");


                String genCustomer = response.getObject();
                logger.info("genCustomer Generated ======= " + response.getObject());

                try {
                    CreateAccountRequest createAccountRequest = new CreateAccountRequest();
                    createAccountRequest.setAFFCODE(cacRequest.getAffiliateCode());
                    createAccountRequest.setSOURCE(cacRequest.getSourceCode());
                    createAccountRequest.setMSGID("ACC" + cacRequest.getRequestId());
                    createAccountRequest.setCORRELID("ACC" + cacRequest.getRequestId());
                    createAccountRequest.setUSERID(cacRequest.getSourceCode());
                    createAccountRequest.setBRN(cacRequest.getBranchCode());
                    createAccountRequest.setACC("DUMMY");
                    createAccountRequest.setCUSTNO(genCustomer);
                    createAccountRequest.setACCLS(cacRequest.getAccountClass());
                    createAccountRequest.setCCY(cacRequest.getCurrency());
                    createAccountRequest.setADESC(cacRequest.getAccountName());
                    createAccountRequest.setACSTATNODR("N");
                    createAccountRequest.setACSTATNOCR("N");
                    createAccountRequest.setFROZEN("N");
                    createAccountRequest.setADDRESS_1(cacRequest.getAccountAddress1());
                    createAccountRequest.setADDRESS_2(cacRequest.getAccountAddress2());
                    createAccountRequest.setADDRESS_3(cacRequest.getAccountAddress3());
                    createAccountRequest.setADDRESS_4(cacRequest.getAccountAddress4());
                    createAccountRequest.setPOSTALLOWED("Y");
                    createAccountRequest.setLOC(cacRequest.getLocation());
                    createAccountRequest.setCHQBOOK("Y");
                    createAccountRequest.setPASSBOOK("Y");
                    createAccountRequest.setATM("Y");

                    logger.info("accountGwtServiceImplementation.callAccountCreationGateway PAYLOAD " + createAccountRequest);

                    response = accountGwtServiceImplementation.callAccountCreationGateway(createAccountRequest);

                    logger.info("Create corp account response Data: " + response);

                    UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest();
                    updateAccountRequest.setAffiliate(cacRequest.getAffiliateCode());
                    updateAccountRequest.setRespCode(response.getResponseCode());
                    updateAccountRequest.setRespMsg(response.getResponseMessage());
                    updateAccountRequest.setAccount(response.getObject());
                    updateAccountRequest.setRefNum(String.valueOf(responseTanked.getData()));

                    Response respAccount =  accountServiceImplementation.UpdateAccount(updateAccountRequest);

                    logger.info("Update Account  Response  "+ respAccount);


                    if (!response.getResponseCode().equalsIgnoreCase("000")) {
                        return responseTanked;
                    }

                    String getcust = response.getObject();

                    logger.info("before updating account");


                    logger.info("getAcc : " + getcust);


                    return response;

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info(e.getMessage());
                    response.setResponseCode("78");
                    response.setResponseMessage("Failed to create  Account" + genCustomer);
                }
                return response;
            }


        } else if (createMode.equalsIgnoreCase("GWT") && cacRequest.getCustType().equalsIgnoreCase("I")) {

            String dob = null;
            try {
                dob = httpUtils.formateDate(cacRequest.getDob());

                logger.info("dob inside indv " + dob);
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("Dob " + dob);

                 // tanked the txn inCase gateWay timeout.
                OciIndividualRequest ociIndividualReq = new OciIndividualRequest();
                System.out.println("here 1 ");
                ociIndividualReq.setAffCode(cacRequest.getAffiliateCode());
                ociIndividualReq.setFirstName(cacRequest.getFirstName());
                ociIndividualReq.setMiddleName(cacRequest.getMiddleName());
                ociIndividualReq.setLastName(cacRequest.getLastName());
                ociIndividualReq.setDob(cacRequest.getDob());
                ociIndividualReq.setId_type(cacRequest.getId_type());
                ociIndividualReq.setIdNo(cacRequest.getIdNo());
                ociIndividualReq.setIdIssuingDate(cacRequest.getIdIssuingDate());
                ociIndividualReq.setIdExpiryDate(cacRequest.getIdExpiryDate());
                ociIndividualReq.setPhoneNumber(cacRequest.getMobileNumber());
                ociIndividualReq.setEmail(cacRequest.getEmailId());
                ociIndividualReq.setGender(cacRequest.getGender());
                ociIndividualReq.setAddress1(cacRequest.getAddress1());
                ociIndividualReq.setAddress2(cacRequest.getAddress2());
                ociIndividualReq.setCountryCode(cacRequest.getCountryCode());
                ociIndividualReq.setCustType(cacRequest.getCustType());
                ociIndividualReq.setCustCategory(cacRequest.getCustCategory());
                ociIndividualReq.setBrnCode(cacRequest.getBranchCode());
                ociIndividualReq.setCcy(cacRequest.getCurrency());
                ociIndividualReq.setAccountClass(cacRequest.getAccountClass());
                ociIndividualReq.setFlexCustId("STD");
                ociIndividualReq.setMandateBase64(cacRequest.getMandateBase64());

                logger.info(" accountServiceImplementation.ociIndividualReq   Customer ================ payload " + ociIndividualReq);

                responseTanked = accountServiceImplementation.ociCustomerAcctCreationMandate(ociIndividualReq);

                logger.info("Tanked individual Response  Data -" + responseTanked);


            try {
                String shortName = cacRequest.getLastName() + cacRequest.getMobileNumber().substring(1, 5);
                String fullName = cacRequest.getLastName() + " " + cacRequest.getFirstName() + " " + cacRequest.getMiddleName();

                GwtIndCustomerRequest gwtIndCustomerRequest = new GwtIndCustomerRequest();


                gwtIndCustomerRequest.setAFFCODE(cacRequest.getAffiliateCode());
                gwtIndCustomerRequest.setSOURCE(cacRequest.getSourceCode());
                gwtIndCustomerRequest.setMSGID(cacRequest.getRequestId());
                gwtIndCustomerRequest.setCORRELID(cacRequest.getRequestId());
                gwtIndCustomerRequest.setUSERID(cacRequest.getSourceCode());
                gwtIndCustomerRequest.setAFFCODE(cacRequest.getAffiliateCode());
                gwtIndCustomerRequest.setCTYPE(cacRequest.getCustType());
                gwtIndCustomerRequest.setNAME(fullName);
                gwtIndCustomerRequest.setADDRLN1(cacRequest.getAddress1());
                gwtIndCustomerRequest.setADDRLN2(cacRequest.getAddress2());
                gwtIndCustomerRequest.setADDRLN3(cacRequest.getAddress3());
                gwtIndCustomerRequest.setADDRLN4(cacRequest.getAddress4());
                gwtIndCustomerRequest.setCOUNTRY(cacRequest.getCountryCode());
                gwtIndCustomerRequest.setSNAME(shortName);
                gwtIndCustomerRequest.setNLTY(cacRequest.getCountryCode());
                gwtIndCustomerRequest.setLBRN(cacRequest.getAffiliateCode());
                gwtIndCustomerRequest.setCCATEG(cacRequest.getCustCategory());
                gwtIndCustomerRequest.setFULLNAME(fullName);
                gwtIndCustomerRequest.setUIDNAME(cacRequest.getId_type());
                gwtIndCustomerRequest.setUIDVAL(cacRequest.getIdNo());
                gwtIndCustomerRequest.setLOC(cacRequest.getLocation());
                gwtIndCustomerRequest.setLMCCY(cacRequest.getCurrency());
                gwtIndCustomerRequest.setUDF2(cacRequest.getBvn());
                gwtIndCustomerRequest.setUDF3(cacRequest.getDob());
                gwtIndCustomerRequest.setFSTNAME(cacRequest.getFirstName());
                gwtIndCustomerRequest.setMIDNAME(cacRequest.getMiddleName());
                gwtIndCustomerRequest.setLSTNAME(cacRequest.getLastName());
                gwtIndCustomerRequest.setDOB(cacRequest.getDob());
                gwtIndCustomerRequest.setGENDR(cacRequest.getGender());
                gwtIndCustomerRequest.setTELEPHNO(cacRequest.getTelephone());
                gwtIndCustomerRequest.setEMAILID(cacRequest.getEmailId());
                gwtIndCustomerRequest.setMOBNUM(cacRequest.getMobileNumber());
                gwtIndCustomerRequest.setLANGUAGE(cacRequest.getLanguage());

                logger.info("accountGwtServiceImplementation.callCreateIndividualCustomerGateway PAYLOAD " + gwtIndCustomerRequest);

                response = accountGwtServiceImplementation.callCreateIndividualCustomerGateway(gwtIndCustomerRequest);

                logger.info("Create Individual customer respose Data : " + response);

            } catch (Exception e) {
                e.printStackTrace();
                logger.info(e.getMessage());
                response.setResponseCode("72");
                response.setResponseMessage("Failed");
            }

            UpdateCustomerRequest updateCustomerReq = new UpdateCustomerRequest();
            updateCustomerReq.setAffiliate(cacRequest.getAffiliateCode());
            updateCustomerReq.setRespMsg(response.getResponseMessage());
            updateCustomerReq.setRespCode(response.getResponseCode());
            updateCustomerReq.setRefNum(String.valueOf(responseTanked.getData()));
            updateCustomerReq.setFlexCustomerId(response.getObject());

            Response resp = accountServiceImplementation.UpdateCustomer(updateCustomerReq);
            logger.info("Customer Update Response " + resp);



            if (!response.getResponseCode().equalsIgnoreCase("000")) {
                return responseTanked;
            } else {


                logger.info(" about to call create accout ++ ");

                String genCustomer = response.getObject();

                logger.info("genCustomer Individaul generated : " + genCustomer);
                logger.info("genCustomer Individaul generated String.valueOf : " + genCustomer);

                try {
                    String fullName = cacRequest.getLastName() + " " + cacRequest.getFirstName() + " " + cacRequest.getMiddleName();
                    GwtIndividualAcctRequest gwtIndividualAcctRequest = new GwtIndividualAcctRequest();
                    gwtIndividualAcctRequest.setAFFCODE(cacRequest.getAffiliateCode());
                    gwtIndividualAcctRequest.setSOURCE(cacRequest.getSourceCode());
                    gwtIndividualAcctRequest.setMSGID("ACC" + cacRequest.getRequestId());
                    gwtIndividualAcctRequest.setCORRELID("ACC" + cacRequest.getRequestId());
                    gwtIndividualAcctRequest.setUSERID(cacRequest.getSourceCode());
                    gwtIndividualAcctRequest.setBRANCH(cacRequest.getBranchCode());
                    gwtIndividualAcctRequest.setBRN(cacRequest.getBranchCode());
                    gwtIndividualAcctRequest.setACC("DUMMY");
                    gwtIndividualAcctRequest.setCUSTNO(genCustomer);
                    gwtIndividualAcctRequest.setACCLS(cacRequest.getAccountClass());
                    gwtIndividualAcctRequest.setCCY(cacRequest.getCurrency());
                    gwtIndividualAcctRequest.setADESC(fullName);
                    gwtIndividualAcctRequest.setACSTATNODR("N");
                    gwtIndividualAcctRequest.setACSTATNOCR("N");
                    gwtIndividualAcctRequest.setFROZEN("N");
                    gwtIndividualAcctRequest.setADDRESS_1(cacRequest.getAddress1());
                    gwtIndividualAcctRequest.setADDRESS_2(cacRequest.getAddress2());
                    gwtIndividualAcctRequest.setADDRESS_3(cacRequest.getAddress3());
                    gwtIndividualAcctRequest.setADDRESS_4(cacRequest.getAddress4());
                    gwtIndividualAcctRequest.setLOC(cacRequest.getLocation());
                    gwtIndividualAcctRequest.setCHQBOOK(cacRequest.getCHQBOOK());
                    gwtIndividualAcctRequest.setPASSBOOK(cacRequest.getPASSBOOK());
                    gwtIndividualAcctRequest.setATM("Y");

                    logger.info("accountGwtServiceImplementation.callIndividualAccountCreationGateway" + gwtIndividualAcctRequest);

                    response = accountGwtServiceImplementation.callIndividualAccountCreationGateway(gwtIndividualAcctRequest);
                    logger.info("Create individual Account response Data  -" + response);


                    UpdateAccountRequest updateAccountReq = new UpdateAccountRequest();
                    updateAccountReq.setAffiliate(cacRequest.getAffiliateCode());
                    updateAccountReq.setRespCode(response.getResponseCode());
                    updateAccountReq.setRespMsg(response.getResponseMessage());
                    updateAccountReq.setAccount(response.getObject());
                    updateAccountReq.setRefNum(String.valueOf(responseTanked.getData()));

                    Response respAccount =  accountServiceImplementation.UpdateAccount(updateAccountReq);

                    logger.info("Update Account  Response  "+ respAccount);

                    if (!response.getResponseCode().equalsIgnoreCase("000")) {
                        return responseTanked;
                    } else {
                        logger.info("Create Account response  : " + response.getData());
                        return response;
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info(e.getMessage());
                    response.setResponseCode("73");
                    response.setResponseMessage("Failed");
                }

                return response;
            }


        } else {
            response.setResponseCode("99");
            response.setResponseMessage("INVALID CREATE MODE SELECTED");
            logger.info("Else Part of the code response Data -" + response);

        }


        return response;

    }

    public Response getAccount(GetAccount getAccount) {
        return validateClient.getAccount(getAccount);
    }

    public Response updateDetails(CustomerUpdateReq customerUpdateReq) throws Exception {
        return customerUpdateService.callUpdatesCustomer(customerUpdateReq);
    }

    public ResponseDuo PlacedOrRemovePnd(PndRequest pndRequest) {
        return accountPndService.PlacePndOrRemoved(pndRequest);
    }
}



