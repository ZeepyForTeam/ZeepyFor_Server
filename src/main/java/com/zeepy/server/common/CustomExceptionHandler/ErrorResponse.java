package com.zeepy.server.common.CustomExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ErrorResponse {
	private final LocalDateTime timeStamp = LocalDateTime.now();

	private int status;

	private String message;

	private List<CustomFieldError> customFieldErrors;

	public static ErrorResponse create() {
		return new ErrorResponse();
	}

	public ErrorResponse status(int status) {
		this.status = status;
		return this;
	}

	public ErrorResponse message(String message) {
		this.message = message;
		return this;
	}

	public ErrorResponse errors(Errors errors) {
		setCustomFieldErrors(errors.getFieldErrors());
		return this;
	}

	public void setCustomFieldErrors(List<FieldError> fieldErrors) {
		customFieldErrors = new ArrayList<>();

		fieldErrors.forEach(error -> customFieldErrors.add(new CustomFieldError(
			Objects.requireNonNull(error
				.getCodes())[0],
			error.getRejectedValue(),
			error.getDefaultMessage()
		)));
	}

	@Getter
	public static class CustomFieldError {
		private final String field;
		private final Object value;
		private final String reason;

		public CustomFieldError(String field, Object value, String reason) {
			this.field = field;
			this.value = value;
			this.reason = reason;
		}
	}
}
