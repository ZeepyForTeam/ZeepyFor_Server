package com.zeepy.server.common.CustomExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.AlreadyParticipationException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.BadRequestCommentException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.CustomException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NoContentException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundCommunityException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundParticipationException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundPasswordException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundUserException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.OverflowAchievementRateException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.RefreshTokenException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

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

	@ExceptionHandler(NotFoundPasswordException.class)
	public ResponseEntity<ErrorResponse> notfoundPasswordException(NotFoundPasswordException e) {
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

	@ExceptionHandler(RefreshTokenException.class)
	public ResponseEntity<ErrorResponse> refreshToeknException(RefreshTokenException e) {
		ErrorResponse response = setErrorResponseOnlyStatusMessage(e);

		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
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
