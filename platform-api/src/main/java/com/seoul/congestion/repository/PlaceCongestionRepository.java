package com.seoul.congestion.repository;

import com.seoul.congestion.domain.PlaceCongestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlaceCongestionRepository extends JpaRepository<PlaceCongestion, Long> {
    List<PlaceCongestion> findTop10ByPlaceIdOrderByCollectedAtDesc(String placeId);
}
