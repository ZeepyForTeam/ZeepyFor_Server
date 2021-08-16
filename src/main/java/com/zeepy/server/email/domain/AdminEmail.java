package com.zeepy.server.email.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * Created by Minky on 2021-07-25
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class AdminEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_sequence_gen")
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
