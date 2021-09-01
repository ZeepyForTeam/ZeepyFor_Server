package com.zeepy.server.s3.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import com.zeepy.server.common.ControllerTest;
import com.zeepy.server.s3.dto.ImageUrlResDto;
import com.zeepy.server.s3.dto.ImageUrlResDtos;
import com.zeepy.server.s3.service.S3Service;

@WebMvcTest(controllers = {S3Controller.class}, includeFilters = @ComponentScan.Filter(classes = {
	EnableWebSecurity.class}))
@MockBean(JpaMetamodelMappingContext.class)
class S3ControllerTest extends ControllerTest {
	@MockBean
	private S3Service s3Service;

	@Override
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		super.setUp(webApplicationContext);
	}

	@Test
	@WithMockUser(username = "user", password = "123123", roles = "USER")
	void uploadImage() throws Exception {
		ImageUrlResDtos expect = new ImageUrlResDtos(
			Arrays.asList(new ImageUrlResDto("https://s3.amazon.com/zeepyImage/hi.jpeg")));
		given(s3Service.uploadImages(any(), any())).willReturn(expect);

		MockMultipartFile file = new MockMultipartFile("file", "hello.png", MediaType.IMAGE_PNG_VALUE, "HI".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("file2", "hello2.png", MediaType.IMAGE_PNG_VALUE,
			"HIHI".getBytes());

		mockMvc.perform(multipart("/api/s3")
			.file(file)
			.file(file2))
			.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}
}