package com.ecobank.FcubsAccountCreation.services.implementation;


import com.ecobank.FcubsAccountCreation.dao.ResponseDuo;
import com.ecobank.FcubsAccountCreation.restmodel.PndRequest;
import com.ecobank.FcubsAccountCreation.services.AccountPndService;
import com.ecobank.FcubsAccountCreation.utiils.DataSources;
import com.ecobank.FcubsAccountCreation.utiils.HttpUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

@Repository
public class AccountPndPlacement implements AccountPndService {

    private final Logger logger = Logger.getLogger(AccountPndPlacement.class);

    @Override
    public ResponseDuo PlacePndOrRemoved(PndRequest pndRequest) {
        logger.info("======AccountPndPlacement=++++Payload  " + pndRequest);
        ResponseDuo response = new ResponseDuo();
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        HttpUtils httpUtils = new HttpUtils();

        try {
            logger.info("AccountPndPlacement  Affiliate " + pndRequest.getAffiliate());
            con = DataSources.getDataAffBaseConnection2(pndRequest.getAffiliate());
            stmt = con.prepareCall("{call xxeco_custcorp_acct_creatn_pkg.pr_pnd_placed_removed(?,?,?,?,?,?)}");
            stmt.setString(1, pndRequest.getAccount());
            stmt.setString(2, pndRequest.getPndAction());
            stmt.setString(3, pndRequest.getPndReason());
            stmt.setString(4, pndRequest.getSourceCode());
            stmt.registerOutParameter(5, Types.VARCHAR);
            stmt.registerOutParameter(6, Types.VARCHAR);

            stmt.execute();

            logger.info("Response Code" + stmt.getString(5));
            logger.info("Response msg " + stmt.getString(6));

            response.setResponseCode(stmt.getString(5));
            response.setResponseMessage(stmt.getString(6));


        } catch (Exception e) {
            response.setResponseCode("99");
            response.setResponseMessage("DATABASE ERROR");
            logger.info(e.getMessage());
        } finally {
            DataSources.closeFinally(con, stmt, rs);
        }

        return response;
    }
}
