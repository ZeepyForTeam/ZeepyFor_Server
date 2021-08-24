package com.zeepy.server.common.CustomExceptionHandler.CustomException;

import com.zeepy.server.common.CustomExceptionHandler.ErrorCode;

public class JointPurchaseOwner extends CustomException {
	public JointPurchaseOwner() {
		super(ErrorCode.JOINTPURCHASE_OWNER);
	}
}
