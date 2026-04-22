package com.seoul.congestion.dto;

import com.seoul.congestion.domain.BusStopCongestion;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class BusStopCongestionDto {
    private String stopId;
    private String stopName;
    private String congestionLevel;
    private LocalDateTime collectedAt;

    public BusStopCongestionDto(BusStopCongestion entity) {
        this.stopId = entity.getStopId();
        this.stopName = entity.getStopName();
        this.congestionLevel = entity.getCongestionLevel();
        this.collectedAt = entity.getCollectedAt();
    }
}
