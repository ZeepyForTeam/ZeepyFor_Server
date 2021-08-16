package com.zeepy.server.user.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Minky on 2021-08-16
 */

@NoArgsConstructor
@Setter
@Getter
public class AccessNotifyRequestDto {
	@NotNull(message = "accessNotify cannot be Null")
	private Boolean accessNotify;

	public AccessNotifyRequestDto(Boolean accessNotify) {
		this.accessNotify = accessNotify;
	}

}
