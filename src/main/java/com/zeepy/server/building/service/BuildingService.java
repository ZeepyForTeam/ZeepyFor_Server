package com.zeepy.server.building.service;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zeepy.server.building.domain.*;
import com.zeepy.server.building.dto.BuildingAddressResponseDto;
import com.zeepy.server.building.dto.BuildingRequestDto;
import com.zeepy.server.building.dto.BuildingResponseDto;
import com.zeepy.server.building.repository.BuildingRepository;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NoContentException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Minky on 2021-05-15
 */

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class BuildingService {
    private final BuildingRepository buildingRepository;
    private final JPAQueryFactory jpaQueryFactory;

    private QBuilding qBuilding = QBuilding.building;
    private QBuildingDeal qBuildingDeal = QBuildingDeal.buildingDeal;
    private QBuildingLike qBuildingLike = QBuildingLike.buildingLike;

    private BooleanExpression goeMonthlyRent(Integer monthlyRent) {
        if (monthlyRent == null) {
            return null;
        }
        return qBuildingDeal
                .monthlyRent
                .goe(monthlyRent);
    }

    private BooleanExpression loeMonthlyRent(Integer monthlyRent) {
        if (monthlyRent == null) {
            return null;
        }
        return qBuildingDeal
                .monthlyRent
                .loe(monthlyRent);
    }

    private BooleanExpression goeDeposit(Integer deposit) {
        if (deposit == null) {
            return null;
        }
        return qBuildingDeal
                .deposit
                .goe(deposit);
    }

    private BooleanExpression loeDeposit(Integer deposit) {
        if (deposit == null) {
            return null;
        }
        return qBuildingDeal
                .deposit
                .loe(deposit);
    }

    private BooleanExpression neDealType(DealType dealType) {
        if (dealType == null) {
            return null;
        }
        return qBuildingDeal
                .dealType
                .ne(dealType);
    }

    // CREATE
    @Transactional
    public Long create(BuildingRequestDto buildingRequestDto) {
        Building building = buildingRepository.save(buildingRequestDto.returnBuildingEntity());
        return building.getId();
    }

    // READ
    @Transactional(readOnly = true)
    public Page<BuildingResponseDto> getAll(
            Integer greaterMonthlyRent,
            Integer lesserMonthlyRent,
            Integer greaterDeposit,
            Integer lesserDeposit,
            DealType notEqualDealType,
            Pageable pageable
    ) {
        QueryResults<Building> fetchResults = jpaQueryFactory
                .selectFrom(qBuilding)
                .innerJoin(qBuilding.buildingDeals, qBuildingDeal)
                .where(
                        goeMonthlyRent(greaterMonthlyRent),
                        loeMonthlyRent(lesserMonthlyRent),
                        goeDeposit(greaterDeposit),
                        loeDeposit(lesserDeposit),
                        neDealType(notEqualDealType)
                )
                .orderBy(qBuilding.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchJoin()
                .fetchResults();

        return new PageImpl<BuildingResponseDto>(
                BuildingResponseDto.listOf(fetchResults.getResults()),
                pageable,
                fetchResults.getTotal());
    }

    // READ
    @Transactional(readOnly = true)
    public BuildingResponseDto getByAddress(String address) {
        Building building = buildingRepository.findByAddressContaining(address)
                .orElseThrow(NoContentException::new);
        return BuildingResponseDto.of(building);
    }

    // READ
    @Transactional(readOnly = true)
    public List<BuildingResponseDto> getByLatitudeAndLongitude(
            double latitudeGreater,
            double latitudeLess,
            double longitudeGreater,
            double longitudeLess
    ) {
        List<Building> buildingList = buildingRepository
                .findByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                        latitudeGreater,
                        latitudeLess,
                        longitudeGreater,
                        longitudeLess
                );
        return BuildingResponseDto.listOf(buildingList);
    }

    // READ
    @Transactional(readOnly = true)
    public Page<BuildingAddressResponseDto> getBuildingAddressesByAddress(String address, Pageable pageable) {
        Page<Building> buildingList = buildingRepository.findByAddressContaining(address, pageable);
        return new PageImpl<BuildingAddressResponseDto>(
                BuildingAddressResponseDto.listOf(buildingList.getContent()),
                pageable,
                buildingList.getTotalElements());
    }

    // READ
    @Transactional(readOnly = true)
    public BuildingResponseDto getById(Long id) {
        Building building = buildingRepository
                .findById(id)
                .orElseThrow(NoContentException::new);
        return BuildingResponseDto.of(building);
    }

    // UPDATE
    @Transactional
    public void update(Long id, BuildingRequestDto buildingRequestDto) {
        Building building = buildingRepository
                .findById(id)
                .orElseThrow(NoContentException::new);

        building.setBuildYear(buildingRequestDto.getBuildYear());
        building.setApartmentName(buildingRequestDto.getApartmentName());
        building.setShortAddress(buildingRequestDto.getShortAddress());
        building.setAddress(buildingRequestDto.getAddress());
        building.setExclusivePrivateArea(buildingRequestDto.getExclusivePrivateArea());
        building.setAreaCode(buildingRequestDto.getAreaCode());

        buildingRepository.save(building);
    }

    // DELETE
    @Transactional
    public void deleteById(Long id) {
        buildingRepository.deleteById(id);
    }
}
