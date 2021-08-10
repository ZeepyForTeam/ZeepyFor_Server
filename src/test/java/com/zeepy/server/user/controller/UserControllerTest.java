package com.zeepy.server.user.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.web.context.WebApplicationContext;

import com.zeepy.server.common.ControllerTest;
import com.zeepy.server.user.domain.Address;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.dto.AddAddressReqDto;
import com.zeepy.server.user.dto.AddressDto;
import com.zeepy.server.user.dto.AddressResDto;
import com.zeepy.server.user.service.UserService;

@DisplayName("UserController_테스트_클래스")
@WebMvcTest(controllers = UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class UserControllerTest extends ControllerTest {
	private final User user = User.builder()
		.id(1L)
		.name("작성자")
		.nickname("흰수염범고래")
		.email("test@gmail.com")
		.build();
	@MockBean
	private UserService userService;

	@BeforeEach
	@Override
	public void setUp(WebApplicationContext webApplicationContext) {
		super.setUp(webApplicationContext);
	}

	@Test
	@DisplayName("유저 주소 등록&수정 테스트 ")
	public void saveUserAddress() throws Exception {
		//given
		String userEmail = user.getEmail();

		AddressDto addressDto1 = new AddressDto("서울특별시 용산구", "용산2가동 새싹빌딩");
		AddressDto addressDto2 = new AddressDto("서울특별시 용산구", "김치나베우동 배고픈빌딩");
		AddressDto addressDto3 = new AddressDto("서울특별시 중구", "시키드나동 드루와빌딩");

		List<AddressDto> addressDtoList = new ArrayList<>(Arrays.asList(addressDto1, addressDto2, addressDto3));
		AddAddressReqDto addAddressReqDto = AddAddressReqDto.builder()
			.addresses(addressDtoList)
			.build();

		//when
		doNothing().when(userService).addAddress(addAddressReqDto, userEmail);

		//then
		doPut("/api/user/address", addAddressReqDto);
	}

	@Test
	@DisplayName("유저 주소 불러오기 테스트")
	public void getUserAddress() throws Exception {
		//given
		Address address1 = new Address("서울특별시 용산구", "용산2가동 새싹빌딩");
		Address address2 = new Address("서울특별시 용산구", "김치나베우동 배고픈빌딩");
		Address address3 = new Address("서울특별시 중구", "시키드나동 드루와빌딩");

		List<Address> addressList = new ArrayList<>(Arrays.asList(address1, address2, address3));

		AddressResDto addressResDto = new AddressResDto(addressList);

		//when
		given(userService.getAddresses(any(String.class))).willReturn(addressResDto);

		//then
		doGet("/api/user/address");
	}
}
