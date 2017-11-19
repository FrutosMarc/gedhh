package com.hh.ged.exceptions;


import com.hh.ged.dtos.Response;

/**
 * Created by rvl on 10/11/2017.
 */
public class GedHhRestException extends Exception {

    private Response<?> responseObj;

    public GedHhRestException(String message, Throwable cause) {
        super(message, cause);
    }

    public GedHhRestException(Response<?> responseObj) {
        this.responseObj = responseObj;
    }

    public Response<?> getResponseObj() {
        return responseObj;
    }
}
