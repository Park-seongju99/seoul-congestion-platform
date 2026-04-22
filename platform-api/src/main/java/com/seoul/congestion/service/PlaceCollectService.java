package com.seoul.congestion.service;

import com.seoul.congestion.domain.PlaceCongestion;
import com.seoul.congestion.dto.PlaceCongestionDto;
import com.seoul.congestion.repository.PlaceCongestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceCollectService {

    private final PlaceCongestionRepository placeCongestionRepository;

    public List<PlaceCongestionDto> getPlaceCongestion(String placeId) {
        return placeCongestionRepository.findTop10ByPlaceIdOrderByCollectedAtDesc(placeId)
            .stream().map(PlaceCongestionDto::new).collect(Collectors.toList());
    }
}
