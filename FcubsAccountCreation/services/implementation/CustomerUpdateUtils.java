package com.ecobank.FcubsAccountCreation.services.implementation;

import com.ecobank.FcubsAccountCreation.dao.Response;
import com.ecobank.FcubsAccountCreation.restmodel.CustomerUpdateReq;
import com.ecobank.FcubsAccountCreation.services.CustomerUpdateService;
import com.ecobank.FcubsAccountCreation.utiils.DataSources;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

@Repository
public class CustomerUpdateUtils implements CustomerUpdateService {

    private final static  Logger logger  =   Logger.getLogger(CustomerUpdateUtils.class);

    public Response callUpdatesCustomer(CustomerUpdateReq customerUpdateReq) {
        logger.info("customerUpdateReq ======Payload++++ "+ customerUpdateReq);
        Response response =new Response();
        Connection  con = null;
        CallableStatement stmt  = null;
        ResultSet rs = null;

        try{
            logger.info("customerUpdateReq +++ Affiliate "+ customerUpdateReq.getAffiliate());
            con = DataSources.getDataAffBaseConnection2(customerUpdateReq.getAffiliate());
            stmt = con.prepareCall("{call eco_maint_utility_pkg.fn_cust_idcard_update(?,?,?,?,?,?,?,?,?)}");
            stmt.setString(1,customerUpdateReq.getCustomerNo());
            stmt.setString(2,customerUpdateReq.getIdType());
            stmt.setString(3,customerUpdateReq.getIdNo());
            stmt.setString(4,customerUpdateReq.getExpireDate());
            stmt.setString(5,customerUpdateReq.getIssueDate());
            stmt.setString(6,customerUpdateReq.getMobileNumber());
            stmt.setString(7,customerUpdateReq.getEmail());
            stmt.registerOutParameter(8, Types.VARCHAR);
            stmt.registerOutParameter(9,Types.VARCHAR);

            stmt.execute();

            logger.info("response Code +++ "+ stmt.getString(8));
            logger.info("Response  mesg ++ "+  stmt.getString(9));

            response.setResponseCode(stmt.getString(8));
            response.setResponseMessage(stmt.getString(9));

            return response;

        }catch (Exception  e){
            logger.info(e.getMessage());
            response.setResponseCode("99");
            response.setResponseMessage("DATABASE ERROR");
        }finally {
            DataSources.closeFinally(con,stmt,rs);
        }
        logger.info("response  Data "+ response);

        return response;
    }
}
