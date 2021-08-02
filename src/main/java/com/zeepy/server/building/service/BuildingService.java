package com.zeepy.server.building.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zeepy.server.building.domain.Building;
import com.zeepy.server.building.domain.DealType;
import com.zeepy.server.building.domain.QBuilding;
import com.zeepy.server.building.domain.QBuildingDeal;
import com.zeepy.server.building.domain.QBuildingLike;
import com.zeepy.server.building.dto.BuildingAddressResponseDto;
import com.zeepy.server.building.dto.BuildingRequestDto;
import com.zeepy.server.building.dto.BuildingResponseDto;
import com.zeepy.server.building.repository.BuildingRepository;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.InvalidRequestParameterException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NoContentException;
import com.zeepy.server.review.domain.Furniture;
import com.zeepy.server.review.domain.QReview;
import com.zeepy.server.review.domain.RoomCount;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Created by Minky on 2021-05-15
 */

/**
 * Query DSL
 * 환경 세팅 -> Gradle 참조
 * 빌드 시 Gradle Build 를 따로 해주셔야 Q Class 가 생성됩니다.
 * Q Class 는 QueryDsl 에서 사용하는 Entity Class 로 생각하시면 편합니다.
 */

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class BuildingService {
    private final BuildingRepository buildingRepository;
    private final JPAQueryFactory jpaQueryFactory;

    private QBuilding qBuilding = QBuilding.building;
    private QBuildingDeal qBuildingDeal = QBuildingDeal.buildingDeal;
    private QBuildingLike qBuildingLike = QBuildingLike.buildingLike;
    private QReview qReview = QReview.review1;

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

    private BooleanExpression eqRoomCount(String roomCount) {
        if (roomCount == null) {
            return null;
        }

        try {
            RoomCount.valueOf(roomCount);
        } catch (Exception e) {
            throw new InvalidRequestParameterException();
        }

        return qReview
                .roomCount
                .eq(RoomCount.valueOf(roomCount));
    }

    private BooleanExpression inFurnitures(List<Furniture> furnitures) {
        if (furnitures == null) {
            return null;
        }
        return qReview
                .furnitures
                .any()
                .in(furnitures);
    }

    // CREATE
    @Transactional
    public Long create(BuildingRequestDto buildingRequestDto) {
        Building building = buildingRepository
                .save(buildingRequestDto.returnBuildingEntity());
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
            String roomCount,
            List<Furniture> furnitures,
            Pageable pageable
    ) {
        /**
         * 조건 검색
         * 하단에 Query는 다음과 같이 생각하시면 됩니다.
         * SELECT * FROM BUILDING INNER JOIN BUILDING_DEAL ON BULDING_ID WHERE [조건문]
         * 조금 다른 것은 Object Mapping 이 되어 있기 때문에 빌딩 좋아요, 거래 정보가 다 조인이 되어 있습니다.
         * 하지만 제가 조인을 사용한 이유는 빌딩 거래정보에 조건문을 삽입해야 하기 때문에 사용했습니다.
         * 가장 먼저 FROM 에 해당하는 Entity를 selectFrom 절에 삽입하시면 됩니다.
         * ex) From 에 있는 Entity 정보 전체를 조회하겠다라는 뜻이 됩니다.
         * QURDY DSL에서 조인 검색 시 해당 연관관계를 매핑해주시면 됩니다.
         * ex) 빌딩 안에 빌딩 거래정보 buildingDeals 와 BuildingDeal 엔티티를 매핑시키는 것
         * INNER JOIN 시 WHERE 조건 문으로 조인한 데이터 까지 필터링이 가능하지만 OUTER JOIN 을 한 경우 ON 절에서 필터링 하셔야합니다.
         * OFFSET 은 몇번째 페이지를 가져 올것인가 입니다. 해당 데이터는 pagable 객체에서 인용해서 사용합니다.
         * LIMIT은 페이징 시 몇 개까지 페이징 할 것인가 입니다. 해당 데이터 또한 pagable 객체에서 인용해서 사용합니다
         * fetchJoin 을 통하여 N + 1문제를 해결합니다. 만약 일반 JOIN을 사용하실 경우 N + 1 문제가 발생하게 되는데 이때 타파 법은
         * DISTINCT 를 사용하시면 됩니다. DISTINCT는 아시다 싶이 중복제거 키워드 입니다.
         * ex) N + 1 문제란 검색 조건으로 인해 중복된 데이터가 검색 되는 것으로 N개의 결과물을 예상하였으나 N보다 많은 검색물이 조회되는
         * 대표적인 문제 입니다.
         * FETCHRESULT 를 사용한 이유는 해당 결과에 대한 page 결과를 알기 위해서 입니다. 즉 제가 offset, limit 을 사용하였기 때문에
         * 해당 키워드를 사용한 것이고 페이징이 없는 경우에는 fetch로 가지고 오시면 됩니다.
         */
        QueryResults<Building> fetchResults = jpaQueryFactory
                .selectFrom(qBuilding)
                .leftJoin(qBuilding.reviews, qReview)
                .innerJoin(qBuilding.buildingDeals, qBuildingDeal)
                .where(
                        goeMonthlyRent(greaterMonthlyRent),
                        loeMonthlyRent(lesserMonthlyRent),
                        goeDeposit(greaterDeposit),
                        loeDeposit(lesserDeposit),
                        neDealType(notEqualDealType),
                        eqRoomCount(roomCount),
                        inFurnitures(furnitures)
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
        building.update(buildingRequestDto);
        buildingRepository.save(building);
    }

    // DELETE
    @Transactional
    public void deleteById(Long id) {
        buildingRepository.deleteById(id);
    }
}
