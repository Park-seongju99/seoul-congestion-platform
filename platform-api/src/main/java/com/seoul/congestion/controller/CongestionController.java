package com.seoul.congestion.controller;

import com.seoul.congestion.domain.PlaceCongestion;
import com.seoul.congestion.domain.BusStopCongestion;
import com.seoul.congestion.service.CongestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/congestion")
@RequiredArgsConstructor
public class CongestionController {

    private final CongestionService congestionService;

    @GetMapping("/place/{placeId}")
    public List<PlaceCongestion> getPlaceCongestion(@PathVariable String placeId) {
        return congestionService.getPlaceCongestion(placeId);
    }

    @GetMapping("/bus/{stopId}")
    public List<BusStopCongestion> getBusStopCongestion(@PathVariable String stopId) {
        return congestionService.getBusStopCongestion(stopId);
    }
}
