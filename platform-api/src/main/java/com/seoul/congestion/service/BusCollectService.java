package com.seoul.congestion.service;

import com.seoul.congestion.domain.BusStopCongestion;
import com.seoul.congestion.dto.BusStopCongestionDto;
import com.seoul.congestion.repository.BusStopCongestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusCollectService {

    private final BusStopCongestionRepository busStopCongestionRepository;

    public List<BusStopCongestionDto> getBusStopCongestion(String stopId) {
        return busStopCongestionRepository.findTop10ByStopIdOrderByCollectedAtDesc(stopId)
            .stream().map(BusStopCongestionDto::new).collect(Collectors.toList());
    }
}
