package com.zeepy.server.common.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Created by KimGyeong 4/19/20.
 */

@Getter
@MappedSuperclass
public abstract class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createdDate = LocalDateTime.now();
}
