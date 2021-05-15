package com.zeepy.server.building.service;

import com.zeepy.server.building.domain.AreaCode;
import com.zeepy.server.building.dto.AreaCodeRequestDto;
import com.zeepy.server.building.repository.AreaCodeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Minky on 2021-05-15
 */

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class AreaCodeService {
    private final AreaCodeRepository areaCodeRepository;

    // CREATE
    @Transactional
    public Long create(AreaCodeRequestDto areaCodeRequestDto) {
        AreaCode areaCode = areaCodeRepository.save(areaCodeRequestDto.returnAreaCodeEntity());
        return areaCode.getAreaCode();
    }
}
