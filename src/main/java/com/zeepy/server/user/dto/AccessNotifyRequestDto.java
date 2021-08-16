package com.zeepy.server.user.dto;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

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
