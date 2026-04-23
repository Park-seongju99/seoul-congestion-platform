package com.seoul.congestion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CongestionMessage {
    private String placeId;
    private String congestionLevel;
    private LocalDateTime collectedAt;
}
