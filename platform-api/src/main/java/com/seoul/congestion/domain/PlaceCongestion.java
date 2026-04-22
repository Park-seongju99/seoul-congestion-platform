package com.seoul.congestion.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "place_congestion")
@Getter
@NoArgsConstructor
public class PlaceCongestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "place_id", nullable = false, length = 50)
    private String placeId;

    @Column(name = "congestion_level", length = 20)
    private String congestionLevel;

    @Column(name = "collected_at", nullable = false)
    private LocalDateTime collectedAt;

    @Builder
    public PlaceCongestion(String placeId, String congestionLevel, LocalDateTime collectedAt) {
        this.placeId = placeId;
        this.congestionLevel = congestionLevel;
        this.collectedAt = collectedAt;
    }
}
