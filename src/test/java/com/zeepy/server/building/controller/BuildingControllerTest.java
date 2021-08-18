package com.zeepy.server.building.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.zeepy.server.building.domain.BuildingType;
import com.zeepy.server.building.dto.BuildingAutoCompleteResponseDto;
import com.zeepy.server.building.dto.BuildingRequestDto;
import com.zeepy.server.building.dto.BuildingResponseDto;
import com.zeepy.server.building.service.BuildingService;
import com.zeepy.server.common.ControllerTest;

/**
 * Created by Minky on 2021-05-19
 */
@DisplayName("Building Controller Test")
@WebMvcTest(controllers = {BuildingController.class}, includeFilters = @ComponentScan.Filter(classes = {
    EnableWebSecurity.class}))
@MockBean(JpaMetamodelMappingContext.class)
class BuildingControllerTest extends ControllerTest {
    @MockBean
    private BuildingService buildingService;

    private BuildingRequestDto makeBuildingRequestDto() {
        return new BuildingRequestDto(
            0,
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            0.1f,
            1100,
            32.0,
            124.0,
            BuildingType.OFFICETEL.name()
        );
    }

    private BuildingResponseDto makeBuildingResponseDto() {
        return new BuildingResponseDto(
            1L,
            0,
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            0.1f,
            1100,
            32.0,
            124.0,
            BuildingType.OFFICETEL.name(),
            null,
            null,
            null
        );
    }

    private BuildingAutoCompleteResponseDto makeBuildingAddressResponseDto() {
        return new BuildingAutoCompleteResponseDto(
            1L,
            "test",
            "test",
            "test",
            "test",
            "test",
            "test"
        );
    }

    private List<BuildingResponseDto> makeBuildingResponseDtoList() {
        return Arrays.asList(makeBuildingResponseDto(), makeBuildingResponseDto());
    }

    @Override
    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        super.setUp(webApplicationContext);
    }

    @Test
    @DisplayName("GET Buildings Test")
    void getBuildings() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        doGet("/api/buildings", params);
    }

    @Test
    @DisplayName("GET Building By Address Test")
    void getBuildingByAddress() throws Exception {
        given(buildingService.getByAddress(any(String.class)))
            .willReturn(makeBuildingResponseDto());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("address", "test");
        doGet("/api/buildings/address", params);

    }

    @Test
    @DisplayName("GET Building Addresses (AutoComplete) Test")
    void getBuildingAddresses() throws Exception {
        given(buildingService.getBuildingAddressesByAddress(any(String.class), any()))
            .willReturn(new PageImpl<>(Arrays.asList(
                makeBuildingAddressResponseDto(),
                makeBuildingAddressResponseDto()
            )));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("address", "test");
        doGet("/api/buildings/addresses", params);
    }

    @Test
    @DisplayName("GET Buildings By Location Test")
    void getBuildingsByLocation() throws Exception {
        given(buildingService.getByLatitudeAndLongitude(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
            .willReturn(makeBuildingResponseDtoList());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("latitudeGreater", "32.0");
        params.add("latitudeLess", "32.0");
        params.add("longitudeGreater", "124.0");
        params.add("longitudeLess", "124.0");
        doGet("/api/buildings/location", params);
    }

    @Test
    @DisplayName("GET Building Test")
    void getBuilding() throws Exception {
        given(buildingService.getById(anyLong()))
            .willReturn(makeBuildingResponseDto());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        doGet("/api/buildings/1", params);
    }

    @Test
    @DisplayName("CREATE Building Test")
    void uploadBuilding() throws Exception {
        BuildingRequestDto buildingRequestDto = makeBuildingRequestDto();
        given(buildingService.create(any(BuildingRequestDto.class))).willReturn(1L);
        doPost("/api/buildings", buildingRequestDto);
    }

    @Test
    @DisplayName("UPDATE Building Test")
    void updateBuilding() throws Exception {
        BuildingRequestDto buildingRequestDto = makeBuildingRequestDto();
        doNothing().when(buildingService).update(1L, buildingRequestDto);
        doPut("/api/buildings/1", buildingRequestDto);
    }

    @Test
    @DisplayName("DELETE Building Test")
    void deleteBuilding() throws Exception {
        doNothing().when(buildingService).deleteById(1L);
        doDelete("/api/buildings/1");
    }
}