package com.seoul.congestion.service;

import com.seoul.congestion.repository.PlaceCongestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CongestionAnalysisService {

    private final PlaceCongestionRepository placeCongestionRepository;

    public Map<String, String> getLatestAllPlaces() {
        List<String> placeIds = List.of(
            "POI001","POI002","POI003","POI004",
            "POI005","POI006","POI007","POI008"
        );

        Map<String, String> result = new HashMap<>();
        for (String placeId : placeIds) {
            placeCongestionRepository
                .findTop10ByPlaceIdOrderByCollectedAtDesc(placeId)
                .stream().findFirst()
                .ifPresent(c -> result.put(placeId, c.getCongestionLevel()));
        }
        return result;
    }
}
