package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class ImageUploadException extends CustomException {
	public ImageUploadException() {
		super(ErrorCode.IMAGE_UPLOAD_ERROR);
	}
}
