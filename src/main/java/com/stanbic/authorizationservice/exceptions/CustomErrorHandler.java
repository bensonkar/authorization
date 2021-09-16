package com.stanbic.authorizationservice.exceptions;

import com.stanbic.authorizationservice.wrappers.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bkariuki
 */
@ControllerAdvice
public class CustomErrorHandler implements AuthenticationEntryPoint {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleAllErrors(Exception e){
        ResponseWrapper response = new ResponseWrapper();
        log.error("Something went wrong.Please try again later {}",e);
        response.setCode(500);
        response.setMessage("Sorry something went wrong.Please try again later.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseWrapper> processAccessDeniedError(AccessDeniedException ex) {
        ResponseWrapper response = new ResponseWrapper();
        response.setCode(401);
        Map<String, Object> data = new HashMap();
        data.put("description {}", ex.getMessage());
        response.setData(data);
        response.setMessage("You are not authorized to use this resource");
        return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized resource");

    }
}
