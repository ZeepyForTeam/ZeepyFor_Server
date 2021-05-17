package com.zeepy.server.building.service;

import com.zeepy.server.building.domain.Building;
import com.zeepy.server.building.domain.BuildingDeal;
import com.zeepy.server.building.dto.BuildingDealRequestDto;
import com.zeepy.server.building.repository.BuildingDealRepository;
import com.zeepy.server.building.repository.BuildingRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * Created by Minky on 2021-05-15
 */

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class BuildingDealService {
    private final BuildingDealRepository buildingDealRepository;
    private final BuildingRepository buildingRepository;

    // CREATE
    @Transactional
    public Long create(BuildingDealRequestDto buildingDealRequestDto) {
        Building building = buildingRepository.findById(buildingDealRequestDto.getBuildingId()).get();
        BuildingDeal buildingDealIsNotSave = buildingDealRequestDto.returnBuildingDealEntity();
        buildingDealIsNotSave.setBuilding(building);
        BuildingDeal buildingDeal = buildingDealRepository.save(buildingDealIsNotSave);
        return buildingDeal.getId();
    }

    // UPDATE
    @Transactional
    public void update(Long id, BuildingDealRequestDto buildingDealRequestDto) {
        buildingDealRepository.findById(id).ifPresent(buildingDeal -> {
            buildingDeal.setDealDate(new Timestamp(buildingDealRequestDto.getDealDate()));
            buildingDeal.setDeposit(buildingDealRequestDto.getDeposit());
            buildingDeal.setMonthlyRent(buildingDealRequestDto.getMonthlyRent());
            buildingDealRepository.save(buildingDeal);
        });
    }

    // DELETE
    @Transactional
    public void deleteById(Long id) {
        buildingDealRepository.deleteById(id);
    }
}
