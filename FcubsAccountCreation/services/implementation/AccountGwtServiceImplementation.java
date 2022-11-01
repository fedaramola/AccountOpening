package com.ecobank.FcubsAccountCreation.services.implementation;

import com.ecobank.FcubsAccountCreation.dao.Response;
import com.ecobank.FcubsAccountCreation.restmodel.CorpCustRequest;
import com.ecobank.FcubsAccountCreation.restmodel.CreateAccountRequest;
import com.ecobank.FcubsAccountCreation.restmodel.GwtIndCustomerRequest;
import com.ecobank.FcubsAccountCreation.restmodel.GwtIndividualAcctRequest;
import com.ecobank.FcubsAccountCreation.services.AccountGatewayService;
import com.ecobank.FcubsAccountCreation.utiils.HttpsServiceCertificateByPasser;
import com.ecobank.FcubsAccountCreation.utiils.accountCreationProperties;
import io.netty.handler.codec.http.HttpHeaders;
import org.apache.log4j.Logger;
import com.ecobank.FcubsAccountCreation.utiils.*;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service("AccountGatewayService")
public class AccountGwtServiceImplementation implements AccountGatewayService {

    private final static Logger logger = Logger.getLogger(AccountGwtServiceImplementation.class);
    HttpsURLConnection conn = null;
    HttpHeaders headers;

    private HttpsServiceCertificateByPasser bypass = new HttpsServiceCertificateByPasser();

    @Override
    public Response callAccountCreationGateway(CreateAccountRequest createAccountRequest) throws IOException {

        HttpUtils httpUtils = new HttpUtils();
        Response resp = new Response();
        String connectionUrl = "";


        try {
            String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:fcub=\"http://fcubs.ofss.com/service/FCUBSAccService\">"
                    + "   <soapenv:Header/>"
                    + "   <soapenv:Body>"
                    + "      <fcub:CREATECUSTACC_FSFS_REQ xmlns=\"http://fcubs.ofss.com/service/FCUBSAccService\">"
                    + "         <FCUBS_HEADER>"
                    + "            <SOURCE>" + createAccountRequest.getSOURCE() + "</SOURCE>"
                    + "            <UBSCOMP>FCUBS</UBSCOMP>"
                    + "            <MSGID>" + createAccountRequest.getMSGID() + "</MSGID>"
                    + "            <CORRELID>" + createAccountRequest.getCORRELID() + "</CORRELID>"
                    + "            <USERID>" + createAccountRequest.getUSERID() + "</USERID>"
                    + "            <BRANCH>" + createAccountRequest.getAFFCODE() + "</BRANCH>"
                    + "            <MODULEID>ST</MODULEID>"
                    + "            <SERVICE>FCUBSAccService</SERVICE>"
                    + "            <OPERATION>CreateCustAcc</OPERATION>"
                    + "            <SOURCE_OPERATION>CreateCustAcc</SOURCE_OPERATION>"
                    + "            <FUNCTIONID>STDCUSAC</FUNCTIONID>"
                    + "            <ACTION>NEW</ACTION>"
                    + "            <MSGSTAT>SUCCESS</MSGSTAT>"
                    + "            <ADDL>"
                    + "               <PARAM>"
                    + "                  <NAME>SERVERSTAT</NAME>"
                    + "                  <VALUE>HOST</VALUE>"
                    + "               </PARAM>"
                    + "            </ADDL>"
                    + "         </FCUBS_HEADER>"
                    + "         <FCUBS_BODY>"
                    + "            <Cust-Account-Full>"
                    + "             <BRN>" + createAccountRequest.getBRN() + "</BRN>"
                    + "             <ACC>" + createAccountRequest.getACC() + "</ACC>"
                    + "             <CUSTNO>" + createAccountRequest.getCUSTNO() + "</CUSTNO>"
                    + "             <ACCLS>" + createAccountRequest.getACCLS() + "</ACCLS>"
                    + "             <CCY>" + createAccountRequest.getCCY() + "</CCY>"
                    + "             <ADESC>" + createAccountRequest.getADESC() + "</ADESC>"
                    + "             <ACSTATNODR>" + createAccountRequest.getACSTATNOCR() + "</ACSTATNODR>"
                    + "             <ACSTATNOCR>" + createAccountRequest.getACSTATNOCR() + "</ACSTATNOCR>"
                    + "             <FROZEN>" + createAccountRequest.getFROZEN() + "</FROZEN>"
                    + "             <ADDRESS_1>" + createAccountRequest.getADDRESS_1() + "</ADDRESS_1>"
                    + "             <ADDRESS_2>" + createAccountRequest.getADDRESS_2() + "</ADDRESS_2>"
                    + "             <ADDRESS_3>" + createAccountRequest.getADDRESS_3() + "</ADDRESS_3>"
                    + "             <ADDRESS_4>" + createAccountRequest.getADDRESS_4() + "</ADDRESS_4>"
                    + "            <POSTALLOWED>Y</POSTALLOWED>"
                    + "             <LOC>" + createAccountRequest.getLOC() + "</LOC>"
                    + "             <CHQBOOK>" + createAccountRequest.getCHQBOOK() + "</CHQBOOK>"
                    + "             <PASSBOOK>" + createAccountRequest.getPASSBOOK() + "</PASSBOOK>"
                    + "             <ATM>" + createAccountRequest.getATM() + "</ATM>"
                    + "            <CustAcc>"
                    + "               <Misdetails>"
                    + "                  <POOLCD>CURRENT</POOLCD>"
                    + "                  <TXNMIS2>10000</TXNMIS2>"
                    + "              </Misdetails>"
                    + "            </CustAcc>"
                    + "                <UDFDETAILS>"
                    + "                      <FLDNAM>TURNOVER_COVENANT</FLDNAM>"
                    + "                      <FLDVAL>1000</FLDVAL>"
                    + "                </UDFDETAILS>"
                    + "            </Cust-Account-Full>"
                    + "         </FCUBS_BODY>"
                    + "      </fcub:CREATECUSTACC_FSFS_REQ>"
                    + "   </soapenv:Body>"
                    + "</soapenv:Envelope>";

            logger.info(String.format("Raw Xml form " + xmlInput));
            bypass.disableCertificateValidation();

            String cluster = accountCreationProperties.getMessage(createAccountRequest.getAFFCODE());
            String flexheader = "CREATECUSTACC_FSFS_REQ";

            if (!cluster.equalsIgnoreCase("ENG")) {
                connectionUrl = accountCreationProperties.getMessage(cluster + "CREATEACCT");
            } else {
                connectionUrl = accountCreationProperties.getMessage("ENGCREATEACCT");
            }
            logger.info("***Calling Url *** " + connectionUrl + " *** ~Cluster*** " + cluster);

            String result = httpUtils.postRequest(xmlInput, connectionUrl, flexheader);

            String rawcreateResp = httpUtils.getFlexValue(result, "FCUBS_WARNING_RESP");
            String getAccount = httpUtils.getFlexValue(result, "ACC");
            String customerNo = httpUtils.getFlexValue(result, "CUSTNO");
            logger.info("****** Account ******* " + getAccount);
            logger.info("+++++customer+++"+ customerNo);

            if (!"".equals(rawcreateResp) && !"WCODE NOT GENERATED".equals(rawcreateResp) && !"WCODE NOT FOUND".equals(rawcreateResp)) {
                resp.setResponseCode("000");
                resp.setResponseMessage("SUCCESS");
                resp.setObject(getAccount+" CustNo: "+customerNo);
                resp.addArtefact("FCUBS_WARNING_RESP", rawcreateResp);

                Map<String, Object> jobj = new HashMap<String, Object>();
                jobj.put("FCUBS_WARNING_RESP", rawcreateResp);
                jobj.put("CUSTNO", customerNo);
                jobj.put("ACC", getAccount);
                resp.setData(jobj);
            } else {
                resp.setResponseCode("E99");
                resp.setResponseMessage("Create Account FAILED DUE TO: " + httpUtils.getFlexValue(result, "EDESC"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }


    @Override
    public Response callCreateCustomerGateway(CorpCustRequest corpCustRequest) throws IOException {
        Response resp = new Response();
        String connectionUrl = "";
        HttpUtils httpUtils = new HttpUtils();


        try {
            String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:fcub=\"http://fcubs.ofss.com/service/FCUBSCustomerService\">"
                    + "   <soapenv:Header/>"
                    + "   <soapenv:Body>"
                    + "      <fcub:CREATECUSTOMER_FSFS_REQ xmlns=\"http://fcubs.ofss.com/service/FCUBSCustomerService\">"
                    + "         <FCUBS_HEADER>"
                    + "            <SOURCE>" + corpCustRequest.getSOURCE() + "</SOURCE>"
                    + "            <UBSCOMP>FCUBS</UBSCOMP>"
                    + "            <MSGID>" + corpCustRequest.getMSGID() + "</MSGID>"
                    + "            <CORRELID>" + corpCustRequest.getCORRELID() + "</CORRELID>"
                    + "            <USERID>" + corpCustRequest.getUSERID() + "</USERID>"
                    + "            <BRANCH>" + corpCustRequest.getAFFCODE() + "</BRANCH>"
                    + "            <MODULEID>ST</MODULEID>"
                    + "            <SERVICE>FCUBSCustomerService</SERVICE>"
                    + "            <OPERATION>CreateCustomer</OPERATION>"
                    + "            <SOURCE_OPERATION>CreateCustomer</SOURCE_OPERATION>"
                    + "            <FUNCTIONID>STDCIF</FUNCTIONID>"
                    + "            <ACTION>NEW</ACTION>"
                    + "            <MSGSTAT>SUCCESS</MSGSTAT>"
                    + "            <ADDL>"
                    + "               <PARAM>"
                    + "                  <NAME>SERVERSTAT</NAME>"
                    + "                  <VALUE>HOST</VALUE>"
                    + "               </PARAM>"
                    + "            </ADDL>"
                    + "         </FCUBS_HEADER>"
                    + "         <FCUBS_BODY>"
                    + "            <Customer-Full>"
                    + "             <CTYPE>" + corpCustRequest.getCTYPE() + "</CTYPE>"
                    + "             <NAME>" + corpCustRequest.getNAME() + "</NAME>"
                    + "             <ADDRLN1>" + corpCustRequest.getADDRLN1() + "</ADDRLN1>"
                    + "             <ADDRLN2>" + corpCustRequest.getADDRLN2() + "</ADDRLN2>"
                    + "             <ADDRLN3>" + corpCustRequest.getADDRLN3() + "</ADDRLN3>"
                    + "             <ADDRLN4>" + corpCustRequest.getADDRLN4() + "</ADDRLN4>"
                    + "             <COUNTRY>" + corpCustRequest.getCOUNTRY() + "</COUNTRY>"
                    + "             <SNAME>" + corpCustRequest.getSNAME() + "</SNAME>"
                    + "             <LBRN>" + corpCustRequest.getLBRN() + "</LBRN>"
                    + "             <CCATEG>" + corpCustRequest.getCCATEG() + "</CCATEG>"
                    + "             <FULLNAME>" + corpCustRequest.getFULLNAME() + "</FULLNAME>"
                    + "            <ISELCMCUST>Y</ISELCMCUST>"
                    + "            <UIDNAME>REGISTRATION_NUMBER</UIDNAME>"
                    + "             <UIDVAL>" + corpCustRequest.getUIDVAL() + "</UIDVAL>"
                    + "            <MEDIA>MAIL</MEDIA>"
                    + "             <LOC>" + corpCustRequest.getLOC() + "</LOC>"
                    + "            <TRACK_LIMITS>Y</TRACK_LIMITS>"
                    + "             <LMCCY>" + corpCustRequest.getLMCCY() + "</LMCCY>"
                    + "             <UDF2>" + corpCustRequest.getUDF2() + "</UDF2>"
                    + "             <UDF3>" + corpCustRequest.getUDF3() + "</UDF3>"
                    + "                <Custcorp>"
                    + "             <CORPNAME>" + corpCustRequest.getCORPNAME() + "</CORPNAME>"
                    + "             <AMTCCY>" + corpCustRequest.getLMCCY() + "</AMTCCY>"
                    + "             <INCORPDT>" + corpCustRequest.getINCORPDT() + "</INCORPDT>"
                    + "             <CADDR1>" + corpCustRequest.getCADDR1() + "</CADDR1>"
                    + "             <CADDR2>" + corpCustRequest.getCADDR2() + "</CADDR2>"
                    + "             <CADDR3>" + corpCustRequest.getCADDR3() + "</CADDR3>"
                    + "             <CADDR4>" + corpCustRequest.getCADDR4() + "</CADDR4>"
                    + "             <LANGUAGE>" + corpCustRequest.getLANGUAGE() + "</LANGUAGE>"
                    + "             <CNAME>" + corpCustRequest.getCNAME() + "</CNAME>"
                    + "            <UIDNAME>REGISTRATION_NUMBER</UIDNAME>"
                    + "             <UIDVALUE>" + corpCustRequest.getUIDVAL() + "</UIDVALUE>"
                    + "             <TELEPHONE>" + corpCustRequest.getTELEPHONE() + "</TELEPHONE>"
                    + "             <EMAILID>" + corpCustRequest.getEMAILID() + "</EMAILID>"
                    + "             <NATIONID>" + corpCustRequest.getUIDVAL() + "</NATIONID>"
                    + "            </Custcorp>"
                    + "                <Cust-Liab>"
                    + "                      <LIABILTY_NAME>" + corpCustRequest.getLIABILTY_NAME() + "</LIABILTY_NAME>"
                    + "              </Cust-Liab>"
                    + "                <UDFDETAILS>"
                    + "                      <FLDNAM>CUSTOMER_MOBILE_PHONE_NUMBER</FLDNAM>"
                    + "                      <FLDVAL>" + corpCustRequest.getMOBILENUMBER() + "</FLDVAL>"
                    + "                </UDFDETAILS>"
                    + "            </Customer-Full>"
                    + "         </FCUBS_BODY>"
                    + "      </fcub:CREATECUSTOMER_FSFS_REQ>"
                    + "   </soapenv:Body>"
                    + "</soapenv:Envelope>";

            logger.info(String.format("Raw Xml form " + xmlInput));
            bypass.disableCertificateValidation();

            String flexHeader = "CREATECUSTOMER_FSFS_REQ";
            String cluster = accountCreationProperties.getMessage(corpCustRequest.getAFFCODE());

            if (!cluster.equalsIgnoreCase("ENG")) {
                connectionUrl = accountCreationProperties.getMessage(cluster + "CREATECORP");
            } else {
                connectionUrl = accountCreationProperties.getMessage("ENGCREATECORP");
            }
            logger.info("***Calling Url *** " + connectionUrl + " *** ~Cluster*** " + cluster);

            String result = httpUtils.postRequest(xmlInput, connectionUrl, flexHeader);

            String rawcorpResp = httpUtils.getFlexValue(result, "FCUBS_WARNING_RESP");
            String getCustomer = httpUtils.getFlexValue(result, "CUSTNO");
            String  dupError = httpUtils.getFlexValueError(result,"ECODE");

            logger.info("**** Account generate **** " + getCustomer);
            logger.info("**** dupError generate **** " + dupError);

            if (dupError.equalsIgnoreCase("ST-CIF999")){
                getCustomer = httpUtils.getFlexValueDup(result,"EDESC");

                logger.info("**** Account generate dup **** " + getCustomer);

                resp.setResponseCode("000");
                resp.setResponseMessage("SUCCESS");
                resp.setObject(getCustomer);
                resp.addArtefact("FCUBS_WARNING_RESP", rawcorpResp);

                Map<String, Object> jobj = new HashMap<String, Object>();
                jobj.put("FCUBS_WARNING_RESP", rawcorpResp);
                jobj.put("CUSTNO", getCustomer);
                resp.setData(jobj);

                return resp;
            }


            if (!"".equals(rawcorpResp) && !"WCODE NOT GENERATED".equals(rawcorpResp) && !"WCODE NOT FOUND".equals(rawcorpResp) ) {
                resp.setResponseCode("000");
                resp.setResponseMessage("SUCCESS");
                resp.setObject(getCustomer);
                resp.addArtefact("FCUBS_WARNING_RESP", rawcorpResp);

                Map<String, Object> jobj = new HashMap<String, Object>();
                jobj.put("FCUBS_WARNING_RESP", rawcorpResp);
                jobj.put("CUSTNO", getCustomer);
                resp.setData(jobj);
            } else {
                resp.setResponseCode("E99");
                resp.setResponseMessage("Create corp Customer FAILED DUE TO: " + httpUtils.getFlexValue(result, "EDESC"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    public Response callCreateIndividualCustomerGateway(GwtIndCustomerRequest gwtIndCustomerRequest) throws IOException {
        Response resp = new Response();
        String connectionUrl = "";
        HttpUtils httpUtils = new HttpUtils();



        try {
            String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:fcub=\"http://fcubs.ofss.com/service/FCUBSCustomerService\">"
                    + "   <soapenv:Header/>"
                    + "   <soapenv:Body>"
                    + "      <fcub:CREATECUSTOMER_FSFS_REQ xmlns=\"http://fcubs.ofss.com/service/FCUBSCustomerService\">"
                    + "         <FCUBS_HEADER>"
                    + "            <SOURCE>" + gwtIndCustomerRequest.getSOURCE() + "</SOURCE>"
                    + "            <UBSCOMP>FCUBS</UBSCOMP>"
                    + "            <MSGID>" + gwtIndCustomerRequest.getMSGID() + "</MSGID>"
                    + "            <CORRELID>" + gwtIndCustomerRequest.getCORRELID() + "</CORRELID>"
                    + "            <USERID>" + gwtIndCustomerRequest.getUSERID() + "</USERID>"
                    + "            <BRANCH>" + gwtIndCustomerRequest.getAFFCODE() + "</BRANCH>"
                    + "            <MODULEID>ST</MODULEID>"
                    + "            <SERVICE>FCUBSCustomerService</SERVICE>"
                    + "            <OPERATION>CreateCustomer</OPERATION>"
                    + "            <SOURCE_OPERATION>CreateCustomer</SOURCE_OPERATION>"
                    + "            <FUNCTIONID>STDCIF</FUNCTIONID>"
                    + "            <ACTION>NEW</ACTION>"
                    + "            <MSGSTAT>SUCCESS</MSGSTAT>"
                    + "            <ADDL>"
                    + "               <PARAM>"
                    + "                  <NAME>SERVERSTAT</NAME>"
                    + "                  <VALUE>HOST</VALUE>"
                    + "               </PARAM>"
                    + "            </ADDL>"
                    + "         </FCUBS_HEADER>"
                    + "         <FCUBS_BODY>"
                    + "            <Customer-Full>"
                    + "             <CTYPE>" + gwtIndCustomerRequest.getCTYPE() + "</CTYPE>"
                    + "             <NAME>" + gwtIndCustomerRequest.getNAME() + "</NAME>"
                    + "             <ADDRLN1>" + gwtIndCustomerRequest.getADDRLN1() + "</ADDRLN1>"
                    + "             <ADDRLN2>" + gwtIndCustomerRequest.getADDRLN2() + "</ADDRLN2>"
                    + "             <ADDRLN3>" + gwtIndCustomerRequest.getADDRLN3() + "</ADDRLN3>"
                    + "             <ADDRLN4>" + gwtIndCustomerRequest.getADDRLN4() + "</ADDRLN4>"
                    + "             <COUNTRY>" + gwtIndCustomerRequest.getCOUNTRY() + "</COUNTRY>"
                    + "             <SNAME>" + gwtIndCustomerRequest.getSNAME() + "</SNAME>"
                    + "             <NLTY>" + gwtIndCustomerRequest.getNLTY() + "</NLTY>"
                    + "             <LBRN>" + gwtIndCustomerRequest.getLBRN() + "</LBRN>"
                    + "             <CCATEG>" + gwtIndCustomerRequest.getCCATEG() + "</CCATEG>"
                    + "             <FULLNAME>" + gwtIndCustomerRequest.getFULLNAME() + "</FULLNAME>"
                    + "            <ISELCMCUST>Y</ISELCMCUST>"
                    + "            <UIDNAME>REGISTRATION_NUMBER</UIDNAME>"
                    + "             <UIDVAL>" + gwtIndCustomerRequest.getUIDVAL() + "</UIDVAL>"
                    + "            <MEDIA>MAIL</MEDIA>"
                    + "             <LOC>" + gwtIndCustomerRequest.getLOC() + "</LOC>"
                    + "            <TRACK_LIMITS>Y</TRACK_LIMITS>"
                    + "             <LMCCY>" + gwtIndCustomerRequest.getLMCCY() + "</LMCCY>"
                    + "             <UDF2>" + gwtIndCustomerRequest.getUDF2() + "</UDF2>"
                    + "             <UDF3>" + gwtIndCustomerRequest.getUDF3() + "</UDF3>"
                    + "                <Custpersonal>"
                    + "             <FSTNAME>" + gwtIndCustomerRequest.getFSTNAME() + "</FSTNAME>"
                    + "             <MIDNAME>" + gwtIndCustomerRequest.getMIDNAME() + "</MIDNAME>"
                    + "             <LSTNAME>" + gwtIndCustomerRequest.getLSTNAME() + "</LSTNAME>"
                    + "             <DOB>" + gwtIndCustomerRequest.getUDF3() + "</DOB>"
                    + "             <GENDR>" + gwtIndCustomerRequest.getGENDR() + "</GENDR>"
                    + "             <TELEPHNO>" + gwtIndCustomerRequest.getTELEPHNO() + "</TELEPHNO>"
                    + "             <EMAILID>" + gwtIndCustomerRequest.getEMAILID() + "</EMAILID>"
                    + "             <MOBNUM>" + gwtIndCustomerRequest.getMOBNUM() + "</MOBNUM>"
                    + "             <LANG>" + gwtIndCustomerRequest.getLANGUAGE() + "</LANG>"
                    + "            </Custpersonal>"
                    + "            </Customer-Full>"
                    + "         </FCUBS_BODY>"
                    + "      </fcub:CREATECUSTOMER_FSFS_REQ>"
                    + "   </soapenv:Body>"
                    + "</soapenv:Envelope>";

            logger.info(String.format("Raw Xml form " + xmlInput));
            bypass.disableCertificateValidation();

            String flexHeader = "CREATECUSTOMER_FSFS_REQ";
            String cluster = accountCreationProperties.getMessage(gwtIndCustomerRequest.getAFFCODE());

            if (!cluster.equalsIgnoreCase("ENG")) {
                connectionUrl = accountCreationProperties.getMessage(cluster + "CREATECORP");
            } else {
                connectionUrl = accountCreationProperties.getMessage("ENGCREATECORP");
            }
            logger.info("***Calling Url *** " + connectionUrl + " *** ~Cluster*** " + cluster);

            String result = httpUtils.postRequest(xmlInput, connectionUrl, flexHeader);

            String rawcorpResp = httpUtils.getFlexValue(result, "FCUBS_WARNING_RESP");
            String getCustomer = httpUtils.getFlexValue(result, "CUSTNO");
            String  dupError = httpUtils.getFlexValueError(result,"ECODE");

            logger.info("**** Account generate **** " + getCustomer);
            logger.info("**** dupError generate **** " + dupError);

            if (dupError.equalsIgnoreCase("ST-CIF999")){
                getCustomer = httpUtils.getFlexValueDup(result,"EDESC");

                logger.info("**** Account generate dup **** " + getCustomer);

                resp.setResponseCode("000");
                resp.setResponseMessage("SUCCESS");
                resp.setObject(getCustomer);
                resp.addArtefact("FCUBS_WARNING_RESP", rawcorpResp);

                Map<String, Object> jobj = new HashMap<String, Object>();
                jobj.put("FCUBS_WARNING_RESP", rawcorpResp);
                jobj.put("CUSTNO", getCustomer);
                resp.setData(jobj);

                return resp;
            }

            if (!"".equals(rawcorpResp) && !"WCODE NOT GENERATED".equals(rawcorpResp) && !"WCODE NOT FOUND".equals(rawcorpResp) && dupError.equalsIgnoreCase("ST-CIF999")) {
                resp.setResponseCode("000");
                resp.setResponseMessage("SUCCESS");
                resp.setObject(getCustomer);
                resp.addArtefact("FCUBS_WARNING_RESP", rawcorpResp);

                Map<String, Object> jobj = new HashMap<String, Object>();
                jobj.put("FCUBS_WARNING_RESP", rawcorpResp);
                jobj.put("CUSTNO", getCustomer);
                resp.setData(jobj);
            } else {
                resp.setResponseCode("E99");
                resp.setResponseMessage("Create corp Customer FAILED DUE TO: " + httpUtils.getFlexValue(result, "EDESC"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    public Response callIndividualAccountCreationGateway(GwtIndividualAcctRequest gwtIndividualAcctRequest) throws IOException {

        HttpUtils httpUtils = new HttpUtils();
        Response resp = new Response();
        String connectionUrl = "";


        try {
            String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:fcub=\"http://fcubs.ofss.com/service/FCUBSAccService\">"
                    + "   <soapenv:Header/>"
                    + "   <soapenv:Body>"
                    + "      <fcub:CREATECUSTACC_FSFS_REQ xmlns=\"http://fcubs.ofss.com/service/FCUBSAccService\">"
                    + "         <FCUBS_HEADER>"
                    + "            <SOURCE>" + gwtIndividualAcctRequest.getSOURCE() + "</SOURCE>"
                    + "            <UBSCOMP>FCUBS</UBSCOMP>"
                    + "            <MSGID>" + gwtIndividualAcctRequest.getMSGID() + "</MSGID>"
                    + "            <CORRELID>" + gwtIndividualAcctRequest.getCORRELID() + "</CORRELID>"
                    + "            <USERID>" + gwtIndividualAcctRequest.getUSERID() + "</USERID>"
                    + "            <BRANCH>" + gwtIndividualAcctRequest.getBRANCH() + "</BRANCH>"
                    + "            <MODULEID>ST</MODULEID>"
                    + "            <SERVICE>FCUBSAccService</SERVICE>"
                    + "            <OPERATION>CreateCustAcc</OPERATION>"
                    + "            <SOURCE_OPERATION>CreateCustAcc</SOURCE_OPERATION>"
                    + "            <FUNCTIONID>STDCUSAC</FUNCTIONID>"
                    + "            <ACTION>NEW</ACTION>"
                    + "            <MSGSTAT>SUCCESS</MSGSTAT>"
                    + "            <ADDL>"
                    + "               <PARAM>"
                    + "                  <NAME>SERVERSTAT</NAME>"
                    + "                  <VALUE>HOST</VALUE>"
                    + "               </PARAM>"
                    + "            </ADDL>"
                    + "         </FCUBS_HEADER>"
                    + "         <FCUBS_BODY>"
                    + "            <Cust-Account-Full>"
                    + "             <BRN>" + gwtIndividualAcctRequest.getBRN() + "</BRN>"
                    + "             <ACC>" + gwtIndividualAcctRequest.getACC() + "</ACC>"
                    + "             <CUSTNO>" + gwtIndividualAcctRequest.getCUSTNO() + "</CUSTNO>"
                    + "             <ACCLS>" + gwtIndividualAcctRequest.getACCLS() + "</ACCLS>"
                    + "             <CCY>" + gwtIndividualAcctRequest.getCCY() + "</CCY>"
                    + "             <ADESC>" + gwtIndividualAcctRequest.getADESC() + "</ADESC>"
                    + "             <ACSTATNODR>" + gwtIndividualAcctRequest.getACSTATNOCR() + "</ACSTATNODR>"
                    + "             <ACSTATNOCR>" + gwtIndividualAcctRequest.getACSTATNOCR() + "</ACSTATNOCR>"
                    + "             <FROZEN>" + gwtIndividualAcctRequest.getFROZEN() + "</FROZEN>"
                    + "             <ADDRESS_1>" + gwtIndividualAcctRequest.getADDRESS_1() + "</ADDRESS_1>"
                    + "             <ADDRESS_2>" + gwtIndividualAcctRequest.getADDRESS_2() + "</ADDRESS_2>"
                    + "             <ADDRESS_3>" + gwtIndividualAcctRequest.getADDRESS_3() + "</ADDRESS_3>"
                    + "             <ADDRESS_4>" + gwtIndividualAcctRequest.getADDRESS_4() + "</ADDRESS_4>"
                    + "            <POSTALLOWED>Y</POSTALLOWED>"
                    + "             <LOC>" + gwtIndividualAcctRequest.getLOC() + "</LOC>"
                    + "             <CHQBOOK>" + gwtIndividualAcctRequest.getCHQBOOK() + "</CHQBOOK>"
                    + "             <PASSBOOK>" + gwtIndividualAcctRequest.getPASSBOOK() + "</PASSBOOK>"
                    + "             <ATM>" + gwtIndividualAcctRequest.getATM() + "</ATM>"
                    + "            <CustAcc>"
                    + "               <Misdetails>"
                    + "                  <POOLCD>SAVINGS</POOLCD>"
                    + "                  <TXNMIS2>10000</TXNMIS2>"
                    + "              </Misdetails>"
                    + "            </CustAcc>"
                    + "                <UDFDETAILS>"
                    + "                      <FLDNAM>TURNOVER_COVENANT</FLDNAM>"
                    + "                      <FLDVAL>1000</FLDVAL>"
                    + "                </UDFDETAILS>"
                    + "            </Cust-Account-Full>"
                    + "         </FCUBS_BODY>"
                    + "      </fcub:CREATECUSTACC_FSFS_REQ>"
                    + "   </soapenv:Body>"
                    + "</soapenv:Envelope>";

            logger.info(String.format("Raw Xml form " + xmlInput));
            bypass.disableCertificateValidation();

            String cluster = accountCreationProperties.getMessage(gwtIndividualAcctRequest.getAFFCODE());
            String flexheader = "CREATECUSTACC_FSFS_REQ";

            if (!cluster.equalsIgnoreCase("ENG")) {
                connectionUrl = accountCreationProperties.getMessage(cluster + "CREATEACCT");
            } else {
                connectionUrl = accountCreationProperties.getMessage("ENGCREATEACCT");
            }
            logger.info("***Calling Url *** " + connectionUrl + " *** ~Cluster*** " + cluster);

            String result = httpUtils.postRequest(xmlInput, connectionUrl, flexheader);

            String rawcreateResp = httpUtils.getFlexValue(result, "FCUBS_WARNING_RESP");
            String customerNo = httpUtils.getFlexValue(result, "CUSTNO");
            String getAccount = httpUtils.getFlexValue(result, "ACC");
            logger.info("****** Account ******* " + getAccount);
            logger.info("****** Account ******* " + customerNo);

            if (!"".equals(rawcreateResp) && !"WCODE NOT GENERATED".equals(rawcreateResp) && !"WCODE NOT FOUND".equals(rawcreateResp)) {
                resp.setResponseCode("000");
                resp.setResponseMessage("SUCCESS");
                resp.setObject(getAccount);
                resp.addArtefact("FCUBS_WARNING_RESP", rawcreateResp);

                Map<String, Object> jobj = new HashMap<String, Object>();
                jobj.put("FCUBS_WARNING_RESP", rawcreateResp);
                jobj.put("CUSTNO",customerNo);
                jobj.put("ACC", getAccount);
                resp.setData(jobj);
            } else {
                resp.setResponseCode("E99");
                resp.setResponseMessage("Create Account FAILED DUE TO: " + httpUtils.getFlexValue(result, "EDESC"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

}
