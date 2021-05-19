package com.zeepy.server.common;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by KimGyeong 4/19/20.
 */
public abstract class ControllerTest {
    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;

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

    protected <T> ResultActions doDelete(String path, T request) throws Exception {
        return mockMvc.perform(delete(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request))
        )
                .andExpect(status().isNoContent());
    }
}
