package com.ecobank.FcubsAccountCreation.repository;

import com.ecobank.FcubsAccountCreation.dao.Response;
import com.ecobank.FcubsAccountCreation.restmodel.GetAccount;
import com.ecobank.FcubsAccountCreation.restmodel.GetAccountRequest;
import com.ecobank.FcubsAccountCreation.utiils.DataSources;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class ValidateClient {

    private final Logger logger = Logger.getLogger(ValidateClient.class);

    public String getCreationType(String affiliteCode, String custType, String accoutType, String mandateType, String tokenRequest) {
        String resp = "";
        CallableStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

        logger.info("ValidateClient ++++++++++++++PAYLOAD custType: " + custType + " accoutType " + accoutType + " mandateType " + mandateType
                + " tokenrequest " + tokenRequest);

        try {
            logger.info("ValidateClient ++++++++++++++affiliteCode " + affiliteCode);
            conn = DataSources.getDataAffBaseConnection2(affiliteCode);
            stmt = conn.prepareCall("{call xxeco_custcorp_acct_creatn_pkg.pr_validatetype(?,?,?,?,?,?,?)}");
            stmt.setString(1, custType);
            stmt.setString(2, accoutType);
            stmt.setString(3, mandateType);
            stmt.setString(4, tokenRequest);
            stmt.registerOutParameter(5, Types.VARCHAR);
            stmt.registerOutParameter(6, Types.VARCHAR);
            stmt.registerOutParameter(7, Types.VARCHAR);

            stmt.execute();

            logger.info(" ValidateClient ====== Data " + stmt.getString(5));
            logger.info(" ValidateClient ====== response Code " + stmt.getString(6));
            logger.info(" ValidateClient ====== response Message " + stmt.getString(7));

            if (stmt.getString(6).equalsIgnoreCase("00")) {
                resp = stmt.getString(5);
            } else {
                resp = "90";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            resp = "89";
        } finally {
            DataSources.closeFinally(conn, stmt, rs);
        }

        return resp;
    }


    public Response getAccount(GetAccount getAccount) {
        logger.info("getAccount++++++++++++++PAYLOAD" + getAccount);

        GetAccountRequest jsonObject = new GetAccountRequest();
        Response response = new Response();
        Connection con = null;
        CallableStatement stmt = null;

        try {

            con = DataSources.getDataAffBaseConnection2(getAccount.getAffCode());
            stmt = con.prepareCall("{call xxeco_customer_account_creation_pkg.pr_get_account(?,?,?,?,?,?)}");

            stmt.setString(1, getAccount.getAffCode());
            stmt.setString(2, getAccount.getTrackRef());
            stmt.registerOutParameter(3, Types.VARCHAR);
            stmt.registerOutParameter(4, Types.VARCHAR);
            stmt.registerOutParameter(5, Types.VARCHAR);
            stmt.registerOutParameter(6, Types.VARCHAR);
            stmt.execute();
            logger.info("getAccount++++++++++++++");
            logger.info("This ::: getAccount" + stmt.getString(5) + stmt.getString(6));
            logger.info("This ::: getAccount" + stmt.getString(3) + stmt.getString(4));
            response.setResponseCode(stmt.getString(5));
            response.setResponseMessage(stmt.getString(6));
            logger.info("getAccount response data:: " + response.getData());
            if (response.getResponseCode().equals("00")) {

                jsonObject.setAccount(stmt.getString(3));
                jsonObject.setCustomer(stmt.getString(4));
                logger.info("getAccount response jsonObject:: " + jsonObject);
                response.setData(jsonObject);
                logger.info("getAccount response:: " + response.getData());
                logger.info("getAccount response:: " + response);

                return response;
            } else {

                return response;
            }

        } catch (Exception e) {
            response.setResponseCode("98");
            response.setResponseMessage("DATABASE ERROR");

        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    //e.printStackTrace();
                    response.setResponseCode("99");
                    response.setResponseMessage("DATABASE EXCEPTION");
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    //e.printStackTrace();
                    response.setResponseCode("99");
                    response.setResponseMessage("DATABASE EXCEPTION");
                }
            }

        }
        logger.info("getAccount response:: " + response);
        return response;
    }

}
