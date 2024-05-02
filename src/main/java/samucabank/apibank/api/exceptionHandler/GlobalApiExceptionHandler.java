package samucabank.apibank.api.exceptionHandler;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import samucabank.apibank.api.exceptionHandler.beanValidationError.StandardErrorFieldValid;
import samucabank.apibank.api.exceptionHandler.beanValidationError.ValidationError;
import samucabank.apibank.api.exceptionHandler.commonStandardError.StandardError;
import samucabank.apibank.domain.service.customException.card.CardLimitExceededException;
import samucabank.apibank.domain.service.customException.card.CardNotFoundException;
import samucabank.apibank.domain.service.customException.transaction.TransactionIdEqualityException;
import samucabank.apibank.domain.service.customException.user.*;
import samucabank.apibank.domain.service.customException.transaction.InsufficientBalanceException;
import samucabank.apibank.domain.service.customException.wallet.WalletAlreadyRegisteredException;
import samucabank.apibank.domain.service.customException.wallet.WalletNotFoundException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalApiExceptionHandler{

    private final MessageSource messageSource;

    private final Logger logger = LoggerFactory.getLogger(GlobalApiExceptionHandler.class);

    public ResponseEntity<StandardError> handleException(Exception ex, HttpStatus status, HttpServletRequest request, String error) {
        StandardError err = new StandardError(Instant.now(), status.value(), error, request.getRequestURI());
        log(ex);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ValidationError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        String path = request.getRequestURI();
        String error = "One or more parameters are invalid";

        List<StandardErrorFieldValid> errorFieldValids = new ArrayList<>();

        List<FieldError> fieldErrors = ex.getFieldErrors();
        fieldErrors.forEach(fieldError -> {
            String field = fieldError.getField();
            String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            errorFieldValids.add(new StandardErrorFieldValid(field, message));
        });

        log(ex);

        return ResponseEntity.status(status).body(new ValidationError(Instant.now(), status.value(), error, path, errorFieldValids));
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<StandardError> handleUncaught(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String error = "An unexpected system error has occurred. Try again and if the problem persists, contact your system administrator";
        return handleException(ex, status, request, error);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private ResponseEntity<StandardError> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        String error = "Method not allowed";
        return handleException(ex, status, request, error);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    private ResponseEntity<StandardError> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String error = String.format("The resource %s you tried to access is non-existent", ex.getRequestURL());
        return handleException(ex, status, request, error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<StandardError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Information being passed wrong to the server";
        return handleException(ex, status, request, error);
    }

    @ExceptionHandler(WalletAlreadyRegisteredException.class)
    public ResponseEntity<StandardError> handleWalletAlreadyRegisteredException(WalletAlreadyRegisteredException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        return handleException(ex, status, request, ex.getMessage());
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<StandardError> handleWalletNotFoundException(WalletNotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handleException(ex, status, request, ex.getMessage());
    }


    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<StandardError> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handleException(ex, status, request, ex.getMessage());
    }

    @ExceptionHandler(AgeEligibleForRegistrationException.class)
    private ResponseEntity<StandardError> handleAgeEligibleForRegistrationException(AgeEligibleForRegistrationException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return handleException(ex, status, request, ex.getMessage());
    }

    @ExceptionHandler(PhoneAlreadyRegisteredException.class)
    private ResponseEntity<StandardError> handlePhoneAlreadyException(PhoneAlreadyRegisteredException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        return handleException(ex, status, request, ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    private ResponseEntity<StandardError> handleEmailAlreadyException(EmailAlreadyRegisteredException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        return handleException(ex, status, request, ex.getMessage());
    }

    @ExceptionHandler(DocumentAlreadyRegisteredException.class)
    private ResponseEntity<StandardError> handleDocumentAlreadyException(DocumentAlreadyRegisteredException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        return handleException(ex, status, request, ex.getMessage());
    }

    @ExceptionHandler(CardNotFoundException.class)
    private ResponseEntity<StandardError> handleCardNotFoundException(CardNotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handleException(ex, status, request, ex.getMessage());
    }

    @ExceptionHandler(CardLimitExceededException.class)
    private ResponseEntity<StandardError> handleCardLimitExceededException(CardLimitExceededException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        return handleException(ex, status, request, ex.getMessage());
    }
    @ExceptionHandler(InsufficientBalanceException.class)
    private ResponseEntity<StandardError> handleInsufficientBalanceException(InsufficientBalanceException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        return handleException(ex, status, request, ex.getMessage());
    }

    @ExceptionHandler(ZipCodeDoesNotExistException.class)
    private ResponseEntity<StandardError> handleZipCodeDoesNotExistException(ZipCodeDoesNotExistException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handleException(ex, status, request, ex.getMessage());
    }

    @ExceptionHandler(TransactionIdEqualityException.class)
    private ResponseEntity<StandardError> handleTransactionIdEqualityException(TransactionIdEqualityException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        return handleException(ex, status, request, ex.getMessage());
    }

    private void log(Throwable ex) {
        logger.error("error message {}. Details: ", ex.getMessage(), ex);
    }
}
