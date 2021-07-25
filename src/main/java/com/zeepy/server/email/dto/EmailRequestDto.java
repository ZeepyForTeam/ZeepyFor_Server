package com.zeepy.server.email.dto;

import com.zeepy.server.email.domain.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Created by Minky on 2021-07-25
 */

@NoArgsConstructor
@Setter
@Getter
public class EmailRequestDto {
    @javax.validation.constraints.Email(message = "email cannot be Empty")
    String email;

    @Builder
    public EmailRequestDto(
            String email
    ) {
        this.email = email;
    }

    public Email returnEmailEntity() {
        return new Email(null, this.email);
    }
}
