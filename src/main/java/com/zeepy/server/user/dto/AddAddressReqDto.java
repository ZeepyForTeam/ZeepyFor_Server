package com.zeepy.server.user.dto;

import java.util.List;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.BadRequestAddressException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddAddressReqDto {
    private List<AddressDto> addresses;

    @Builder
    public AddAddressReqDto(List<AddressDto> addresses) {
        this.addresses = addresses;
    }

    public void validateAddresses() {
        if (addresses.size() == 1) {
            addresses.get(0).setIsAddressCheckToTrue();
        }

        if (addresses.size() > 3) {
            throw new BadRequestAddressException();
        }
    }
}
