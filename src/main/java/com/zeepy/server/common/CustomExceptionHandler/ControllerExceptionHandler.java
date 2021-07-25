package com.zeepy.server.common.CustomExceptionHandler;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException e
    ) {
        String errorMessage = e.getMessage();
        ErrorCode errorCode = ErrorCode.INVALID_PARAMETER;
        int errorStatus = errorCode.getStatus();
        ErrorResponse errorResponse = ErrorResponse
                .create()
                .status(errorStatus)
                .message(errorMessage);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> invalidCommunitySaveDtoException(MethodArgumentNotValidException e) {
        logger.error("InvalidBody_Exception!!: " + e);

        BindingResult bindingResult = e.getBindingResult();
        ErrorCode errorCode = ErrorCode.INVALID_BODY;
        int errorStatus = errorCode.getStatus();
        String errorMessage = errorCode.getMessage();
        ErrorResponse errorResponse = ErrorResponse.create()
                .status(errorStatus)
                .message(errorMessage)
                .errors(bindingResult);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {

        ErrorCode errorCode = ErrorCode.INVALID_PARAMETER;
        int errorStatus = errorCode.getStatus();
        String errorMessage = errorCode.getMessage();
        ErrorResponse errorResponse = ErrorResponse.create()
                .status(errorStatus)
                .message(errorMessage);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestParameterException(
            InvalidRequestParameterException e
    ) {
        ErrorResponse response = setErrorResponseOnlyStatusMessage(e);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<ErrorResponse> getNullResultSoNoContentException(NoContentException e) {
        ErrorResponse response = setErrorResponseOnlyStatusMessage(e);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundCommunityException.class)
    public ResponseEntity<ErrorResponse> notfoundCommunityException(NotFoundCommunityException e) {
        ErrorResponse response = setErrorResponseOnlyStatusMessage(e);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundParticipationException.class)
    public ResponseEntity<ErrorResponse> notfoundParticipationException(NotFoundParticipationException e) {
        ErrorResponse response = setErrorResponseOnlyStatusMessage(e);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<ErrorResponse> notfoundUserException(NotFoundUserException e) {
        ErrorResponse response = setErrorResponseOnlyStatusMessage(e);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestCommentException.class)
    public ResponseEntity<ErrorResponse> badRequestComment(BadRequestCommentException e) {
        ErrorResponse response = setErrorResponseOnlyStatusMessage(e);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OverflowAchievementRateException.class)
    public ResponseEntity<ErrorResponse> overflowAchievementRate(OverflowAchievementRateException e) {
        ErrorResponse response = setErrorResponseOnlyStatusMessage(e);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyParticipationException.class)
    public ResponseEntity<ErrorResponse> alreadyParticipationException(AlreadyParticipationException e) {
        ErrorResponse response = setErrorResponseOnlyStatusMessage(e);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public ErrorResponse setErrorResponseOnlyStatusMessage(CustomException e) {
        final ErrorCode errorCode = e.getErrorCode();
        int errorStatus = errorCode.getStatus();
        String errorMessage = errorCode.getMessage();

        return ErrorResponse.create()
                .status(errorStatus)
                .message(errorMessage);
    }

}
