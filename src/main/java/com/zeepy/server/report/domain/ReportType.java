package com.zeepy.server.report.domain;

/**
 * Created by Minky on 2021-07-18
 */

/**
 * 신고 종류
 * 자취방 후기 글이 아니에요
 * 비방과 욕설을 사용했어요
 * 허위사실을 기재했어요
 * 사기 글이에요
 * 기타 사유 선택
 */
public enum ReportType {
    MISMATCHING,
    ABUSE,
    FALSE_FACTS,
    SCAM,
    ETC
}
