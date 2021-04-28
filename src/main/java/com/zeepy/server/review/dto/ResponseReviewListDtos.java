package com.zeepy.server.review.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseReviewListDtos {
    private List<ResponseReviewListDto> responseReviewListDtos;

    public ResponseReviewListDtos(List<ResponseReviewListDto> responseReviewListDto){
        this.responseReviewListDtos = responseReviewListDto;
    }
}
