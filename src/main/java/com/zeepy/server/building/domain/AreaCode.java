package com.zeepy.server.building.domain;

import com.zeepy.server.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
@Entity
public class AreaCode extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "area_code_sequence_gen")
    @SequenceGenerator(name = "area_code_sequence_gen", sequenceName = "area_code_sequence")
    private long id;

    @NotEmpty
    private String name;
}
