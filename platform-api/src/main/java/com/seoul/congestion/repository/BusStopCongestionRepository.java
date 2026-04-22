package com.seoul.congestion.repository;

import com.seoul.congestion.domain.BusStopCongestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BusStopCongestionRepository extends JpaRepository<BusStopCongestion, Long> {
    List<BusStopCongestion> findTop10ByStopIdOrderByCollectedAtDesc(String stopId);
}
