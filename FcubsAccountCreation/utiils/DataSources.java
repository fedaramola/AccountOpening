package com.ecobank.FcubsAccountCreation.utiils;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;

public class DataSources {

    private static final Logger logger = Logger.getLogger(DataSources.class);
//    public static Connection getConnections(String affiliate) {
//        Context initCtx;
//        DataSources ds = null;
//        Connection conn = null;
//        try {
//            initCtx = new InitialContext();
//            String JndiConnection = accountCreationProperties.getMessage(affiliate);
//            //    String JndiConnection = "ENG";
//            logger.info("JDBC: "+ "jdbc/" + JndiConnection + "FlexDataSourceFWS");
//            ds = (DataSources)initCtx.lookup("jdbc/" + JndiConnection + "FlexDataSourceFWS");
//            conn = ds.getConnection();
//        } catch (SQLException ex) {
//            logger.info("SQLException thrown in getDataBaseConnection. Reason:" + ex.getMessage());
//            logger.info("SQLException " + Arrays.toString((Object[])ex.getStackTrace()).replaceAll(", ", "\n"));
//        } catch (NamingException ex) {
//            logger.info("Exception thrown in getDataBaseConnection. Reason:" + ex.getMessage());
//            logger.info("Exception " + Arrays.toString((Object[])ex.getStackTrace()).replaceAll(", ", "\n"));
//        } catch (Throwable ex) {
//            logger.info("Exception thrown in getDataBaseConnection. Reason:" + ex.getMessage());
//            logger.info("Exception " + Arrays.toString((Object[])ex.getStackTrace()).replaceAll(", ", "\n"));
//        }
//        return conn;
//    }


    public static Connection getDataAffBaseConnection2(String AffiliateCode) {
        Context initCtx = null;
        DataSource ds = null;
        Connection conn = null;
        try {
            initCtx = new InitialContext();
            String JndiConnection = accountCreationProperties.getMessage(AffiliateCode);
            logger.info("jdbc: "+ "jdbc/" + JndiConnection + "FlexDataSourceFWS");
            ds = (DataSource) initCtx.lookup("jdbc/" + JndiConnection + "FlexDataSourceFWS");
            conn = ds.getConnection();
            //  logger.info("Connection retrieved. SCHEMA: {" + conn.getSchema() + "} affcode: "+ AffiliateCode);
            //logger.info("Connection " + conn.toString());
        } catch (SQLException ex) {
            logger.info("SQLException thrown in getDataBaseConnection. Reason:" + ex.getMessage());
            logger.info("SQLException " + Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
        } catch (NamingException ex) {
            logger.info("Exception thrown in getDataBaseConnection. Reason:" + ex.getMessage());
            logger.info("Exception " + Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
        } catch (Throwable ex) {
            logger.info("Exception thrown in getDataBaseConnection. Reason:" + ex.getMessage());
            logger.info("Exception " + Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
        }
        return conn;
    }

    public static void closeFinally(Connection conn, CallableStatement callStmt, ResultSet resultSet) {

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception ex) {
            }
        }
        if (callStmt != null) {
            try {
                callStmt.close();
            } catch (Exception ex) {
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception ex) {
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {
                }
            }
        }
    }
}
