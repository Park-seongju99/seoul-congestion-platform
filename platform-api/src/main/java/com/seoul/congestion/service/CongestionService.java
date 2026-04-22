package com.seoul.congestion.service;

import com.seoul.congestion.domain.PlaceCongestion;
import com.seoul.congestion.domain.BusStopCongestion;
import com.seoul.congestion.repository.PlaceCongestionRepository;
import com.seoul.congestion.repository.BusStopCongestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CongestionService {

    private final PlaceCongestionRepository placeCongestionRepository;
    private final BusStopCongestionRepository busStopCongestionRepository;

    public List<PlaceCongestion> getPlaceCongestion(String placeId) {
        return placeCongestionRepository.findTop10ByPlaceIdOrderByCollectedAtDesc(placeId);
    }

    public List<BusStopCongestion> getBusStopCongestion(String stopId) {
        return busStopCongestionRepository.findTop10ByStopIdOrderByCollectedAtDesc(stopId);
    }
}
