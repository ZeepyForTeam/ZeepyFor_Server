package com.zeepy.server.common;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeepy.server.common.config.security.CustomAccessDeniedHandler;
import com.zeepy.server.common.config.security.CustomAuthenticationEntryPoint;
import com.zeepy.server.common.config.security.JwtAuthenticationProvider;

/**
 * Created by KimGyeong 4/19/20.
 */
public abstract class ControllerTest {
	protected MockMvc mockMvc;
	protected ObjectMapper objectMapper;
	@MockBean
	protected JwtAuthenticationProvider jwtAuthenticationProvider;
	@MockBean
	protected CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	@MockBean
	protected CustomAccessDeniedHandler customAccessDeniedHandler;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		this.mockMvc = MockMvcBuilders
			.webAppContextSetup(webApplicationContext)
			.addFilters(new CharacterEncodingFilter("UTF-8", true))
			.build();

		this.objectMapper = new ObjectMapper();
	}

	protected <T> ResultActions doPost(String path, T request) throws Exception {
		return mockMvc.perform(post(path)
			.content(objectMapper.writeValueAsBytes(request))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(header().string(HttpHeaders.LOCATION, path + "/1"))
			.andDo(MockMvcResultHandlers.print());
	}

	protected <T> ResultActions doPostThenOk(String path, T request) throws Exception {
		return mockMvc.perform(post(path)
			.content(objectMapper.writeValueAsBytes(request))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	protected <T> ResultActions doPostWithParams(String path, MultiValueMap<String, String> params) throws Exception {
		return mockMvc.perform(post(path)
			.contentType(MediaType.APPLICATION_JSON)
			.params(params))
			.andExpect(status().isCreated())
			.andDo(MockMvcResultHandlers.print());
	}

	protected <T> ResultActions doMultipartPost(String path, T request, MockMultipartFile file) throws
		Exception {
		return mockMvc.perform(multipart(path)
			.file(file)
			.contentType(MediaType.MULTIPART_FORM_DATA)
			.accept(MediaType.MULTIPART_FORM_DATA))
			.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	protected <T> ResultActions doGet(String path) throws Exception {
		return mockMvc.perform(get(path)
			.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk());
	}

	protected ResultActions doGet(String path, MultiValueMap<String, String> params) throws Exception {
		return mockMvc.perform(get(path)
			.params(params)
		)
			.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	protected <T> ResultActions doPut(String path, T request) throws Exception {
		return mockMvc.perform(put(path)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsBytes(request))
		)
			.andExpect(status().isOk());
	}

	protected <T> ResultActions doDeleteWithParams(String path, MultiValueMap<String, String> params) throws Exception {
		return mockMvc.perform(delete(path)
			.contentType(MediaType.APPLICATION_JSON)
			.params(params))
			.andExpect(status().isNoContent())
			.andDo(MockMvcResultHandlers.print());
	}

	protected <T> ResultActions doDelete(String path) throws Exception {
		return mockMvc.perform(delete(path)
			.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isNoContent());
	}

	protected <T> ResultActions doDelete(String path, T request) throws Exception {
		return mockMvc.perform(delete(path)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request))
		)
			.andExpect(status().isNoContent());
	}
}
