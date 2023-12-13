package com.teamseven.cafeplatform.domain.stamp.common;

import lombok.Getter;

/**
 * 스탬프 설정 중 총 스탬프 개수에 대한 ENUM
 * 현재는 10개와 20개 둘 중 하나 선택가능 <br><br>
 * <strong>number</strong> - 실제 int 값 <br>
 * <strong>display</strong> - 화면에 보여줄 문자열
 */
@Getter
public enum TotalStamp {
    TEN(10, "10개"), TWENTY(20, "20개");

    private final int number;
    private final String display;

    TotalStamp(int number, String display){
        this.number = number;
        this.display = display;
    }
}
