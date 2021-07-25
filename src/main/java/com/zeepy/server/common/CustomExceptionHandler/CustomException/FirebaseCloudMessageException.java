package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

/**
 * Created by Minky on 2021-07-25
 */
public class FirebaseCloudMessageException extends CustomException{
    public FirebaseCloudMessageException() {
        super(ErrorCode.FCM_NOT_ALLOWED);
    }
}
