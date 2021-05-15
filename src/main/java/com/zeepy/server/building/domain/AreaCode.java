package com.zeepy.server.building.domain;

import com.zeepy.server.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * Created by Minky on 2021-05-15
 */

/**
 * DB 구조
 * 지역 코드
 * 지역 이름
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class AreaCode extends BaseTimeEntity {
    @Id
    private long areaCode;

    @NotEmpty
    private String name;

    @Builder
    public AreaCode(
            Long areaCode,
            String name
    ) {
        this.areaCode = areaCode;
        this.name = name;
    }
}
