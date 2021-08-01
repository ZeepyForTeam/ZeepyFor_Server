package com.zeepy.server.building.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import com.zeepy.server.building.dto.AreaCodeRequestDto;
import com.zeepy.server.common.domain.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Minky on 2021-05-15
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class AreaCode extends BaseTimeEntity {
    @Id
    private Long areaCode;

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

    public void update(AreaCodeRequestDto areaCodeRequestDto) {
        this.name = areaCodeRequestDto.getName();
    }
}
