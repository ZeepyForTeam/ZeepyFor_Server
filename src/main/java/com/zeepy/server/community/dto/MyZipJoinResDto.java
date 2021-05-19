package com.zeepy.server.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyZipJoinResDto {
    private List<ParticipationResDto> participationResDtoList;
    private List<WriteOutResDto> writeOutResDtoList;

    @Builder
    public MyZipJoinResDto(List<ParticipationResDto> participationResDtoList, List<WriteOutResDto> writeOutResDtoList) {
        this.participationResDtoList = participationResDtoList;
        this.writeOutResDtoList = writeOutResDtoList;
    }
}
