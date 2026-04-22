package com.seoul.congestion.controller;

import com.seoul.congestion.dto.PlaceCongestionDto;
import com.seoul.congestion.service.CongestionAnalysisService;
import com.seoul.congestion.service.PlaceCollectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/place")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceCollectService placeCollectService;
    private final CongestionAnalysisService congestionAnalysisService;

    @GetMapping("/{placeId}/congestion")
    public List<PlaceCongestionDto> getPlaceCongestion(@PathVariable String placeId) {
        return placeCollectService.getPlaceCongestion(placeId);
    }

    @GetMapping("/congestion/all")
    public Map<String, String> getAllLatest() {
        return congestionAnalysisService.getLatestAllPlaces();
    }
}
