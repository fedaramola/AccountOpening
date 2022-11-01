package com.ecobank.FcubsAccountCreation.services.implementation;

import com.ecobank.FcubsAccountCreation.constant.ResponseCodes;
import com.ecobank.FcubsAccountCreation.dao.Response;
import com.ecobank.FcubsAccountCreation.exception.GenericException;
import com.ecobank.FcubsAccountCreation.restmodel.OciCorporateRequest;
import com.ecobank.FcubsAccountCreation.restmodel.OciIndividualRequest;
import com.ecobank.FcubsAccountCreation.restmodel.UpdateAccountRequest;
import com.ecobank.FcubsAccountCreation.restmodel.UpdateCustomerRequest;
import com.ecobank.FcubsAccountCreation.services.AccountCreationService;
import com.ecobank.FcubsAccountCreation.utiils.DataSources;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.SimpleDateFormat;

@Service("AccountCreationService")
public class AccountServiceImplementation implements AccountCreationService {

    private final Logger logger = Logger.getLogger(AccountServiceImplementation.class);

    @Override
    public Response ociCustomerAcctCreationMandate(OciIndividualRequest ociIndividualRequest) {
        logger.info("ociCustomerAccountCreationMandate ++++++++++++++PAYLOAD" + ociIndividualRequest);

        Response response = new Response();
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            con = DataSources.getDataAffBaseConnection2(ociIndividualRequest.getAffCode());
            stmt = con.prepareCall("{call xxeco_customer_account_creation_pkg.pr_upload_cust_acct(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            stmt.setString(1, ociIndividualRequest.getAffCode());
            stmt.setString(2, ociIndividualRequest.getFirstName());
            stmt.setString(3, ociIndividualRequest.getMiddleName());
            stmt.setString(4, ociIndividualRequest.getLastName());
            stmt.setDate(5, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(ociIndividualRequest.getDob()).getTime()));
            stmt.setString(6, ociIndividualRequest.getId_type());
            stmt.setString(7, ociIndividualRequest.getIdNo());
            stmt.setDate(8, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(ociIndividualRequest.getIdIssuingDate()).getTime()));
            stmt.setDate(9, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(ociIndividualRequest.getIdExpiryDate()).getTime()));
            stmt.setString(10, ociIndividualRequest.getPhoneNumber());
            stmt.setString(11, ociIndividualRequest.getEmail());
            stmt.setString(12, ociIndividualRequest.getGender());
            stmt.setString(13, ociIndividualRequest.getAddress1());
            stmt.setString(14, ociIndividualRequest.getAddress2());
            stmt.setString(15, ociIndividualRequest.getCountryCode());
            stmt.setString(16, ociIndividualRequest.getCustType());
            stmt.setString(17, ociIndividualRequest.getCustCategory());
            stmt.setString(18, ociIndividualRequest.getBrnCode());
            stmt.setString(19, ociIndividualRequest.getCcy());
            stmt.setString(20, ociIndividualRequest.getFlexCustId());
            stmt.setString(21, ociIndividualRequest.getAccountClass());
            stmt.setString(22, ociIndividualRequest.getMandateBase64());
            stmt.registerOutParameter(23, Types.VARCHAR);
            stmt.registerOutParameter(24, Types.VARCHAR);
            stmt.registerOutParameter(25, Types.VARCHAR);
            stmt.registerOutParameter(26, Types.VARCHAR);
            stmt.execute();
            logger.info("This ::: ociCustomerAcctCreationMandate" + stmt.getString(25) + stmt.getString(26));
            logger.info("This ::: ociCustomerAcctCreationMandate" + stmt.getString(24) + stmt.getString(23));
            response.setResponseCode(stmt.getString(25));
            response.setResponseMessage(stmt.getString(26));
            logger.info("getAccount response data:: " + response.getData());

            if (response.getResponseCode().equals("00")) {

                response.setData(stmt.getString(23));
                response.setObject("Pending");
                logger.info("ociCustomerAcctCreationMandate response:: " + response);
                return response;
            } else {
                response.setData(stmt.getString(24));
                return response;
            }

        } catch (Exception e) {
            logger.info("+++=++ DATABASE ERROR======" + e.getMessage());
            response.setResponseCode("98");
            response.setResponseMessage("DATABASE ERROR" + e.getMessage());

        } finally {
            DataSources.closeFinally(con, stmt, rs);

        }
        logger.info("ociCustomerAccountCreation response:: " + response);
        return response;
    }

    @Override
    public Response ociCustCorpAcctCreationMandate(OciCorporateRequest ociCustCorpReq) {
        logger.info("ociCustCorpAcctCreationMandate =============Payload" + ociCustCorpReq);
        Response response = new Response();
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            logger.info("Affiliate Code ===========" + ociCustCorpReq.getAffiliateCode());
            con = DataSources.getDataAffBaseConnection2(ociCustCorpReq.getAffiliateCode());
            logger.info("Coonected" + con);
            stmt = con.prepareCall("{call xxeco_custcorp_acct_creatn_pkg.pr_upload_custcorp_acct(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            stmt.setString(1, ociCustCorpReq.getAffiliateCode());
            stmt.setString(2, ociCustCorpReq.getName());
            stmt.setString(3, ociCustCorpReq.getCustType());
            stmt.setString(4, ociCustCorpReq.getAddress1());
            stmt.setString(5, ociCustCorpReq.getAddress2());
            stmt.setString(6, ociCustCorpReq.getAddress3());
            stmt.setString(7, ociCustCorpReq.getAddress4());
            stmt.setString(8, ociCustCorpReq.getCountryCode());
            stmt.setString(9, ociCustCorpReq.getCustCategory());
            stmt.setString(10, ociCustCorpReq.getFullName());
            stmt.setString(11, ociCustCorpReq.getUidName());
            stmt.setString(12, ociCustCorpReq.getUidValue());
            stmt.setString(13, ociCustCorpReq.getCurrency());
            stmt.setString(14, ociCustCorpReq.getBvn());
            stmt.setDate(15, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(ociCustCorpReq.getDob()).getTime()));
            stmt.setString(16, ociCustCorpReq.getCorporateName());
            stmt.setDate(17, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(ociCustCorpReq.getDateIncorporated()).getTime()));
            stmt.setString(18, ociCustCorpReq.getCompanyAddress1());
            stmt.setString(19, ociCustCorpReq.getCompanyAddress2());
            stmt.setString(20, ociCustCorpReq.getCompanyAddress3());
            stmt.setString(21, ociCustCorpReq.getCompanyAddress4());
            stmt.setString(22, ociCustCorpReq.getLanguage());
            stmt.setString(23, ociCustCorpReq.getCompanyName());
            stmt.setString(24, ociCustCorpReq.getTelephone());
            stmt.setString(25, ociCustCorpReq.getEmailId());
            stmt.setString(26, ociCustCorpReq.getMobileNumber());
            stmt.setString(27, ociCustCorpReq.getLiabilityName());
            stmt.setString(28, ociCustCorpReq.getFlexCustId());
            stmt.setString(29, ociCustCorpReq.getAccountClass());
            stmt.setString(30, ociCustCorpReq.getBranchCode());
            stmt.setString(31, ociCustCorpReq.getAccountName());
            stmt.setString(32, ociCustCorpReq.getAccountAddress1());
            stmt.setString(33, ociCustCorpReq.getAccountAddress2());
            stmt.setString(34, ociCustCorpReq.getCompanyAddress3());
            stmt.setString(35, ociCustCorpReq.getCompanyAddress4());
            stmt.setString(36, ociCustCorpReq.getMandateBase64());
            stmt.registerOutParameter(37, Types.VARCHAR);
            stmt.registerOutParameter(38, Types.VARCHAR);
            stmt.registerOutParameter(39, Types.VARCHAR);
            stmt.registerOutParameter(40, Types.VARCHAR);
            stmt.execute();

            logger.info("This ::: ociCustCorpAcctCreation" + stmt.getString(39) + stmt.getString(40));
            logger.info("This ::: ociCustCorpAcctCreation" + stmt.getString(38) + stmt.getString(37));

            response.setResponseCode(stmt.getString(39));
            response.setResponseMessage(stmt.getString(40));

            logger.info("ociCustCorpAcctCreation Response :: Data " + response);

            if (response.getResponseCode().equals("00")) {

                response.setData(stmt.getString(37));
                response.setObject("Pending");
                logger.info("ociCustCorpAcctCreationMandate response:: " + response);
                return response;
            } else {
                response.setData(stmt.getString(38));
                return response;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseCode("99");
            response.setResponseMessage("DATABASE EXCEPTION" + ex.getMessage());
        } finally {
            DataSources.closeFinally(con, stmt, rs);
        }
        return response;
    }

    @Override
    public Response UpdateAccount(UpdateAccountRequest updateAccountRequest) {
        logger.info("UpdateAccount  +++ Payload : " + updateAccountRequest);
        Response responseDao = new Response();
        CallableStatement stmt = null;
        Connection con = null;
        ResultSet rs = null;
        String message = "Failed!";
        String code = "99";


        try {
            con = DataSources.getDataAffBaseConnection2(updateAccountRequest.getAffiliate());
            stmt = con.prepareCall("{call xxeco_custcorp_acct_creatn_pkg.pr_update_account_response(?,?,?,?,?,?) }");
            stmt.setString(1, updateAccountRequest.getRespCode());
            stmt.setString(2, updateAccountRequest.getRespMsg());
            stmt.setString(3, updateAccountRequest.getAccount());
            stmt.setString(4, updateAccountRequest.getRefNum());
            stmt.registerOutParameter(5, Types.VARCHAR);
            stmt.registerOutParameter(6, Types.VARCHAR);

            stmt.execute();

            logger.info("updateAccountResponse Response Code " + stmt.getString(5));
            logger.info("updateAccountResponse response message " + stmt.getString(6));

            code = stmt.getString(5);
            message = stmt.getString(6);
            logger.info("code: " + code + " message + " + message);


            responseDao.setResponseCode(code);
            responseDao.setResponseMessage(message);
            logger.info("Response Data ++++++ " + responseDao);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            responseDao.setResponseCode("99");
            throw new GenericException(ResponseCodes.PROCESS_ERROR, e.getMessage(), null);
        } finally {
            DataSources.closeFinally(con, stmt, rs);
        }

        return responseDao;
    }

    @Override
    public Response UpdateCustomer(UpdateCustomerRequest updateCustomerRequest) {
        logger.info("UpdateCustomer  +++ Payload : " + updateCustomerRequest);
        Response responseDao = new Response();
        CallableStatement stmt = null;
        Connection con = null;
        ResultSet rs = null;
        String message = "Failed!";
        String code = "99";


        try {
            con = DataSources.getDataAffBaseConnection2(updateCustomerRequest.getAffiliate());
            stmt = con.prepareCall("{call xxeco_custcorp_acct_creatn_pkg.pr_update_customer_response(?,?,?,?,?,?) }");
            stmt.setString(1, updateCustomerRequest.getRespCode());
            stmt.setString(2, updateCustomerRequest.getRespMsg());
            stmt.setString(3, updateCustomerRequest.getFlexCustomerId());
            stmt.setString(4, updateCustomerRequest.getRefNum());
            stmt.registerOutParameter(5, Types.VARCHAR);
            stmt.registerOutParameter(6, Types.VARCHAR);

            stmt.execute();

            logger.info("Response Code " + stmt.getString(5));
            logger.info("response message " + stmt.getString(6));

            code = stmt.getString(5);
            message = stmt.getString(6);

            logger.info("code  :" + code + " message : " + message);
            responseDao.setResponseCode(code);
            responseDao.setResponseMessage(message);
            logger.info("Response updateCustomerRequest Data : " + responseDao);


        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            responseDao.setResponseCode("99");
            throw new GenericException(ResponseCodes.PROCESS_ERROR, e.getMessage(), null);
        } finally {
            DataSources.closeFinally(con, stmt, rs);
        }

        return responseDao;
    }




}