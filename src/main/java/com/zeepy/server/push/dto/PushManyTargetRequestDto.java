package com.zeepy.server.push.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Created by Minky on 2021-07-22
 */

@NoArgsConstructor
@Setter
@Getter
public class PushManyTargetRequestDto {

    @NotBlank(message = "title cannot be Empty")
    private String title;

    @NotBlank(message = "body cannot be Empty")
    private String body;

    public PushManyTargetRequestDto(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
