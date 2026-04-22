package com.seoul.congestion.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "place_master")
@Getter
@NoArgsConstructor
public class PlaceMaster {

    @Id
    @Column(name = "place_id", length = 50)
    private String placeId;

    @Column(name = "place_name", nullable = false, length = 100)
    private String placeName;

    @Column(name = "category", length = 50)
    private String category;
}
