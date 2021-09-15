package com.zeepy.server.email.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Minky on 2021-07-25
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class AdminEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "email_sequence_gen")
    @SequenceGenerator(name = "email_sequence_gen", sequenceName = "email_sequence")
    @Column(name = "email_id")
    private Long id; // PK

    @NotEmpty
    @Lob
    private String email; // 상세 설명

    @Builder
    public AdminEmail(
        Long id,
        String email
    ) {
        this.id = id;
        this.email = email;
    }
}
