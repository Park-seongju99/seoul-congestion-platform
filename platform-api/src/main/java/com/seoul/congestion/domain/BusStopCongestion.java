package com.seoul.congestion.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "bus_stop_congestion")
@Getter
@NoArgsConstructor
public class BusStopCongestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stop_id", nullable = false, length = 50)
    private String stopId;

    @Column(name = "stop_name", length = 100)
    private String stopName;

    @Column(name = "congestion_level", length = 20)
    private String congestionLevel;

    @Column(name = "collected_at", nullable = false)
    private LocalDateTime collectedAt;

    @Builder
    public BusStopCongestion(String stopId, String stopName, String congestionLevel, LocalDateTime collectedAt) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.congestionLevel = congestionLevel;
        this.collectedAt = collectedAt;
    }
}
