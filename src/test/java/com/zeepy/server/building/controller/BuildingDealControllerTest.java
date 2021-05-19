package com.zeepy.server.building.controller;

import com.zeepy.server.building.dto.BuildingDealRequestDto;
import com.zeepy.server.building.dto.BuildingDealResponseDto;
import com.zeepy.server.building.service.BuildingDealService;
import com.zeepy.server.common.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

/**
 * Created by Minky on 2021-05-20
 */
@DisplayName("Building Deal Controller Test")
@WebMvcTest(controllers = BuildingDealController.class)
class BuildingDealControllerTest extends ControllerTest {

    @MockBean
    private BuildingDealService buildingDealService;

    private BuildingDealRequestDto makeBuildingDealRequestDto() {
        return new BuildingDealRequestDto(
                1L,
                10,
                10,
                10,
                1L
        );
    }

    private BuildingDealResponseDto makeBuildingDealResponseDto() {
        return new BuildingDealResponseDto(
                1L,
                new Timestamp(1L),
                10,
                10,
                10
        );
    }

    @Override
    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        super.setUp(webApplicationContext);
    }

    @Test
    @DisplayName("GET Building Deals Test")
    void getBuildingDeals() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        doGet("/api/deal", params);
    }

    @Test
    @DisplayName("GET Building Deal Test")
    void getBuildingDeal() throws Exception {
        given(buildingDealService.getById(anyLong()))
                .willReturn(makeBuildingDealResponseDto());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        doGet("/api/deal/1", params);
    }

    @Test
    @DisplayName("GET Building Deals By Floor And Building Id Test")
    void getBuildingDealByFloorAndBuildingId() throws Exception {
        given(buildingDealService.getByFloorAndBuildingId(anyInt(), anyLong()))
                .willReturn(makeBuildingDealResponseDto());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        doGet("/api/deal/floor/1/building/1", params);
    }

    @Test
    @DisplayName("CREATE Building Deal Test")
    void uploadBuildingDeal() throws Exception {
        BuildingDealRequestDto buildingDealRequestDto = makeBuildingDealRequestDto();
        given(buildingDealService.create(any(BuildingDealRequestDto.class))).willReturn(1L);
        doPost("/api/deal", buildingDealRequestDto);
    }

    @Test
    @DisplayName("UPDATE Building Deal Test")
    void updateBuildingDeal() throws Exception {
        BuildingDealRequestDto buildingDealRequestDto = makeBuildingDealRequestDto();
        doNothing().when(buildingDealService).update(1L, buildingDealRequestDto);
        doPut("/api/deal/1", buildingDealRequestDto);
    }

    @Test
    @DisplayName("DELETE Building Deal Test")
    void deleteBuildingDeal() throws Exception {
        doNothing().when(buildingDealService).deleteById(1L);
        doDelete("/api/deal/1");
    }
}