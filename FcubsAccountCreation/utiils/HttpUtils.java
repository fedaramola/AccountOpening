package com.ecobank.FcubsAccountCreation.utiils;

import com.ecobank.FcubsAccountCreation.services.implementation.AccountGwtServiceImplementation;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public class HttpUtils {
    private final static Logger logger = Logger.getLogger(AccountGwtServiceImplementation.class);

    /**
     * Constructs and return multi-value map for form data
     *
     * @param requestObject: the request object
     * @return the multi-value map object
     */
    public static MultiValueMap<String, String> getFormData(Map<String, Object> requestObject) {

        if (requestObject == null)
            return null;

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        requestObject.forEach((key, val) -> formData.add(key, String.valueOf(val)));
        return formData;
    }

    public static CloseableHttpClient getHttpClient(String url) {
        if (url.toLowerCase().startsWith("https")) {
            try {
                javax.net.ssl.SSLContext sslContext = new org.apache.http.ssl.SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build();
                return HttpClients.custom().setSslcontext(sslContext).setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
            } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
                logger.error(e.getLocalizedMessage());
            }
        } else {
            return HttpClientBuilder.create().build();
        }
        return null;
    }

    public String postRequest(String xmlString, String url, String flexHeader) {
        String retVals = "";

        try {
            CloseableHttpClient client = getHttpClient(url);
            HttpPost post = new HttpPost(url);
            logger.info("calling the {" + url + "} with payload >>>>>>>>>: " + xmlString);
            post.addHeader("Content-Type", "text/xml; charset=utf-8");
            // post.addHeader("SOAPAction", "CREATECUSTACC_FSFS_REQ");
            post.addHeader("SOAPAction", flexHeader);
            post.setEntity(new StringEntity(xmlString, "UTF-8"));
            CloseableHttpResponse response = (CloseableHttpResponse) client.execute(post);
            logger.info("RAW Response Code : " + response.getStatusLine().getStatusCode());
            HttpEntity httpEntity = response.getEntity();
            retVals = EntityUtils.toString(httpEntity);
            logger.info("Response Messages : " + retVals);
            EntityUtils.consumeQuietly(httpEntity);
        } catch (Exception ex) {
            logger.info("Exception " + ex.getMessage());
            logger.info("Reason " + Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
        }
        return retVals;
    }

    public String getFlexValue(String xmlString, String tag) {
        if (null == xmlString || xmlString.isEmpty()) {
            return "WCODE NOT FOUND";
        }
        String startTag = "<" + tag + ">";
        int indxStart = xmlString.indexOf(startTag);
        int indxEnd = xmlString.indexOf("</" + tag + ">");
        logger.info("+" + indxStart + "-" + indxEnd);
        if (indxStart != -1 && indxEnd != -1 && indxEnd > indxStart) {
            return xmlString.substring(indxStart + startTag.length(), indxEnd).trim();
        } else {
            return "WCODE NOT GENERATED";
        }

    }


    public String getFlexValueDup(String xmlString, String tag) {
        String customer = null;
        if (null == xmlString || xmlString.isEmpty()) {
            return "WCODE NOT GENERATED";
        }
        String startTag = "<" + tag + ">";
        int indxStart = xmlString.indexOf(startTag);
        int indxEnd = xmlString.indexOf("</" + tag + ">");
        logger.info("+" + indxStart + "-" + indxEnd);
        if (indxStart != -1 && indxEnd != -1 && indxEnd > indxStart) {
            customer =  xmlString.substring(indxStart + startTag.length(), indxEnd).trim();
            int len = customer.length();
            logger.info("-customer- "+  customer +" - len  -"+len);
            return customer.substring(len-9,len);
        } else {
            return "WCODE NOT GENERATED";
        }

    }

    public String getFlexValueError(String xmlString, String tag) {
        if (null == xmlString || xmlString.isEmpty()) {
            return "WCODE NOT GENERATED";
        }
        String startTag = "<" + tag + ">";
        int indxStart = xmlString.indexOf(startTag);
        int indxEnd = xmlString.indexOf("</" + tag + ">");
        logger.info("+" + indxStart + "-" + indxEnd);
        if (indxStart != -1 && indxEnd != -1 && indxEnd > indxStart) {
            String output =xmlString.substring(indxStart + startTag.length(), indxEnd).trim();
            logger.info("Error -  "+ output);
            return  output;
        } else {
            return "WCODE NOT GENERATED";
        }

    }


    public String formateDate(String  dateString) throws Exception  {

           logger.info(" dateString ++++ "+dateString);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date date = df.parse(dateString);

        df = new SimpleDateFormat("dd-MMM-yyyy");

        logger.info("Formated Date:"+df.format(date));

        return df.format(date);
    }



}

