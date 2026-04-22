package com.seoul.congestion.dto;

import com.seoul.congestion.domain.PlaceCongestion;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class PlaceCongestionDto {
    private String placeId;
    private String congestionLevel;
    private LocalDateTime collectedAt;

    public PlaceCongestionDto(PlaceCongestion entity) {
        this.placeId = entity.getPlaceId();
        this.congestionLevel = entity.getCongestionLevel();
        this.collectedAt = entity.getCollectedAt();
    }
}
