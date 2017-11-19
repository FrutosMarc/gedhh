package com.hh.ged.mvc;

import com.hh.ged.dtos.Response;
import com.hh.ged.exceptions.GedHhRestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GedHhRestErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GedHhRestErrorHandler.class);

    /**
     * Erreur applicative ged hh
     * @param request
     * @param ex
     * @param gre
     * @return
     */
    @ExceptionHandler(GedHhRestException.class)
    ResponseEntity<?> handleDolRestException(HttpServletRequest request, Throwable e, GedHhRestException gre) {
        LOG.error(e.getMessage(), e);
        return ResponseEntity.status(getStatus(request)).body(gre.getResponseObj());
    }

    /**
     * Erreur du framework
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?>
    defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

        LOG.error(e.getMessage(), e);
        Response<Boolean> response = new Response<Boolean>(true,e.getMessage());
        return ResponseEntity.status(getStatus(req)).body(response);
    }


    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }


}
