package com.zeepy.server.common.CustomExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.NoContentException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NoSuchCommunityException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NoSuchUserException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> invalidCommunitySaveDtoException(MethodArgumentNotValidException e) {
		logger.error("InvalidBody_Exception!!: " + e);

		BindingResult bindingResult = e.getBindingResult();
		ErrorCode errorCode = ErrorCode.INVALID_BODY;
		ErrorResponse errorResponse = ErrorResponse.create()
			.status(errorCode.getStatus())
			.message(errorCode.getMessage())
			.errors(bindingResult);

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoContentException.class)
	public ResponseEntity<ErrorResponse> getNullResultSoNoContentException(NoContentException e) {
		final ErrorCode errorCode = e.getErrorCode();
		final ErrorResponse response = ErrorResponse.create()
			.status(errorCode.getStatus())
			.message(errorCode.getMessage());

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoSuchUserException.class)
	public ResponseEntity<ErrorResponse> noSuchUserException(NoSuchUserException e) {
		final ErrorCode errorCode = e.getErrorCode();
		final ErrorResponse response = ErrorResponse.create()
			.status(errorCode.getStatus())
			.message(errorCode.getMessage());

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoSuchCommunityException.class)
	public ResponseEntity<ErrorResponse> noSuchCommunityException(NoSuchCommunityException e) {
		final ErrorCode errorCode = e.getErrorCode();
		final ErrorResponse response = ErrorResponse.create()
			.status(errorCode.getStatus())
			.message(errorCode.getMessage());

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
}
