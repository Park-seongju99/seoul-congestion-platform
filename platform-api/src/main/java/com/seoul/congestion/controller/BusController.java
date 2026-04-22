package com.seoul.congestion.controller;

import com.seoul.congestion.dto.BusStopCongestionDto;
import com.seoul.congestion.service.BusCollectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bus")
@RequiredArgsConstructor
public class BusController {

    private final BusCollectService busCollectService;

    @GetMapping("/{stopId}/congestion")
    public List<BusStopCongestionDto> getBusCongestion(@PathVariable String stopId) {
        return busCollectService.getBusStopCongestion(stopId);
    }
}
