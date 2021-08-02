package com.zeepy.server.building.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zeepy.server.building.domain.AreaCode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Minky on 2021-05-15
 */

@NoArgsConstructor
@Setter
@Getter
public class AreaCodeRequestDto {
    @NotNull(message = "areaCode cannot be Null")
    private Long areaCode;

    @NotBlank(message = "name cannot be Empty")
    private String name;

    public AreaCodeRequestDto(
        Long areaCode,
        String name
    ) {
        this.areaCode = areaCode;
        this.name = name;
    }

    public AreaCode returnAreaCodeEntity() {
        return new AreaCode(
            this.areaCode,
            this.name
        );
    }
}
