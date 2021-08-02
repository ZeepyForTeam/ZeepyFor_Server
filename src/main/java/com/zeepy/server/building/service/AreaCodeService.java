package com.zeepy.server.building.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.building.domain.AreaCode;
import com.zeepy.server.building.dto.AreaCodeRequestDto;
import com.zeepy.server.building.repository.AreaCodeRepository;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NoContentException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

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

    // UPDATE
    @Transactional
    public void update(Long id, AreaCodeRequestDto areaCodeRequestDto) {
        AreaCode areaCode = areaCodeRepository
            .findById(id)
            .orElseThrow(NoContentException::new);
        areaCode.update(areaCodeRequestDto);
        areaCodeRepository.save(areaCode);
    }

    // DELETE
    @Transactional
    public void deleteById(Long id) {
        areaCodeRepository.deleteById(id);
    }
}
