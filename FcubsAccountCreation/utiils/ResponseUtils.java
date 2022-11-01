package com.ecobank.FcubsAccountCreation.utiils;

import com.ecobank.FcubsAccountCreation.constant.ResponseCodes;
import com.ecobank.FcubsAccountCreation.dto.ResponseDto;
import com.ecobank.FcubsAccountCreation.dao.ResponseDao;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {


    /**
     * Returns successful response for controllers with method return holders
     * @param payload: the payload
     * @param <T>: the type
     * @return the response
     */
    public static <T> ResponseEntity<ResponseDto<T>> getSuccessfulControllerResponse(T payload){

        return ResponseEntity.ok().body(
                new ResponseDto<T>()
                        .setResponseCode(ResponseCodes.SUCCESS.getCode())
                        .setMessage(ResponseCodes.SUCCESS.getMessage())
                        .setPayload(payload)
        );
    }

    /**
     * Returns successful response for controllers with general response holder
     * @param payload: the general responsepayload
     * @param <T>: the type
     * @return the response
     */
    public static <T> ResponseDao<T> getSuccessfulRepositoryResponse(T payload, String message, String code) {

        return new ResponseDao<T>()
                .setResponseCode(code)
                .setMessage(message)
                .setPayload(payload);
    }
}
