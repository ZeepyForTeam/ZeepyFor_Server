package com.zeepy.server.building.domain;

import com.zeepy.server.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Created by Minky on 2021-06-02
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class BuildingLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "building_like_sequence_gen")
    @SequenceGenerator(name = "building_like_sequence_gen", sequenceName = "building_like_sequence")
    private Long id;

    @NotNull
    private Timestamp likeDate;

    @Deprecated
    private Long user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "building_id")
    private Building building;

    @Builder
    public BuildingLike(
            Long id,
            Timestamp likeDate,
            Long user
    ) {
        this.id = id;
        this.likeDate = likeDate;
        this.user = user;
    }
}
