package com.zeepy.server.building.dto;

import com.zeepy.server.building.domain.AreaCode;
import com.zeepy.server.building.domain.BuildingDeal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Minky on 2021-05-15
 */

@NoArgsConstructor
@Setter
@Getter
public class AreaCodeDto {
    private long areaCode;
    private String name;

    public AreaCodeDto(
            Long areaCode,
            String name) {
        this.areaCode = areaCode;
        this.name = name;
    }

    public AreaCode returnAreaCodeEntity() {
        return new AreaCode(
                null,
                this.name
        );
    }
}
