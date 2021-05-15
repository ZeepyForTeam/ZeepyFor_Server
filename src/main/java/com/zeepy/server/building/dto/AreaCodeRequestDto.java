package com.zeepy.server.building.dto;

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
    private Long areaCode;
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
