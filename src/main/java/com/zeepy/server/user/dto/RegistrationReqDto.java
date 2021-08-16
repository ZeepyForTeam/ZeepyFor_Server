package com.zeepy.server.user.dto;

import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zeepy.server.user.domain.Role;
import com.zeepy.server.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RegistrationReqDto {
    @NotNull(message = "이름은 필수값입니다.")
    private String name;
    @NotNull(message = "이메일은 필수값입니다.")
    private String email;
    @NotNull(message = "비밀번호는 필수값입니다.")
    private String password;
    private String address;
    private String building;
    @NotNull(message = "이메일 여부는 필수값입니다.")
    private Boolean sendMailCheck;

    @Builder
    public RegistrationReqDto(String name, String email, String password, String address, String building,
        Boolean sendMailCheck) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.building = building;
        this.sendMailCheck = sendMailCheck;
    }

    public User toEntity() {
        return User.builder()
            .name(name)
            .email(email)
            .password(setBCryptEncoding())
            .address(address)
            .building(building)
            .accessNotify(false)
            .sendMailCheck(sendMailCheck)
            .role(Role.ROLE_USER)
            // 모든 사용자 기본 프로필 이미지로 설정 -> 추후 기능 업데이트 시 변경 예정
            .profileImage("https://zeepy.s3.ap-northeast-2.amazonaws.com/zeepyImage/dummyprofile_28pt.png")
            .build();
    }

    private String setBCryptEncoding() {
        return new BCryptPasswordEncoder().encode(password);
    }
}
