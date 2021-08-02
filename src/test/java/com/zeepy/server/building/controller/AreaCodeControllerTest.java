package com.zeepy.server.building.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.web.context.WebApplicationContext;

import com.zeepy.server.building.dto.AreaCodeRequestDto;
import com.zeepy.server.building.service.AreaCodeService;
import com.zeepy.server.common.ControllerTest;

/**
 * Created by Minky on 2021-05-20
 */
@DisplayName("AreaCode Controller Test")
@WebMvcTest(AreaCodeController.class)
@MockBean(JpaMetamodelMappingContext.class)
class AreaCodeControllerTest extends ControllerTest {

	@MockBean
	private AreaCodeService areaCodeService;

	private AreaCodeRequestDto makeAreaCodeRequestDto() {
		return new AreaCodeRequestDto(
			1L,
			"test"
		);
	}

	@Override
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		super.setUp(webApplicationContext);
	}

	@Test
	@DisplayName("CREATE AreaCode Test")
	void uploadAreaCode() throws Exception {
		AreaCodeRequestDto areaCodeRequestDto = makeAreaCodeRequestDto();
		given(areaCodeService.create(any(AreaCodeRequestDto.class))).willReturn(1L);
		doPost("/api/codes", areaCodeRequestDto);
	}

	@Test
	@DisplayName("UPDATE AreaCode Test")
	void updateAreaCode() throws Exception {
		AreaCodeRequestDto areaCodeRequestDto = makeAreaCodeRequestDto();
		doNothing().when(areaCodeService).update(1L, areaCodeRequestDto);
		doPut("/api/codes/1", areaCodeRequestDto);
	}

	@Test
	@DisplayName("DELETE AreaCode Test")
	void deleteAreaCode() throws Exception {
		doNothing().when(areaCodeService).deleteById(1L);
		doDelete("/api/codes/1");
	}
}