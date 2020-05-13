package com.octo.bankoperations.web.common;

import com.octo.bankoperations.exceptions.ClientNotFoundException;
import com.octo.bankoperations.exceptions.NegativeOrNullAmountException;
import com.octo.bankoperations.exceptions.ObligationNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.ConnectException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<String> connectionException(ConnectException ex, WebRequest request) {
        return error(ex, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ExecutionException.class)
    public ResponseEntity<String> outOfBoundException(ExecutionException ex, WebRequest request) {
        return error(ex, HttpStatus.UPGRADE_REQUIRED);
    }

    @ExceptionHandler(ObligationNotFoundException.class)
    public ResponseEntity<String> handleObligationNotFoundException(ObligationNotFoundException ex, WebRequest request) {
        return error(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<String> handleUtilisateurNotFoundException(ClientNotFoundException ex, WebRequest request) {
        return error(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NegativeOrNullAmountException.class)
    public ResponseEntity<String> handleNegativeOrNullAmountException(NegativeOrNullAmountException ex, WebRequest request) {
        return error(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> assertionException(final IllegalArgumentException e) {
        return error(e, HttpStatus.NOT_FOUND);
    }


    private ResponseEntity<String> error(final Exception exception, final HttpStatus httpStatus) {
        if(exception instanceof IndexOutOfBoundsException)
            return new ResponseEntity<>("" , httpStatus);
        final String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(message , httpStatus);
    }
}
