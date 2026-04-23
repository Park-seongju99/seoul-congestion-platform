package com.seoul.congestion.scheduler;

import com.seoul.congestion.domain.PlaceCongestion;
import com.seoul.congestion.dto.CongestionMessage;
import com.seoul.congestion.repository.PlaceCongestionRepository;
import com.seoul.congestion.service.CongestionWebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlaceScheduler {

    private final PlaceCongestionRepository placeCongestionRepository;
    private final RestTemplate restTemplate;
    private final CongestionWebSocketService webSocketService;

    @Value("${seoul.api.key}")
    private String apiKey;

    @Value("${seoul.api.url}")
    private String apiUrl;

    private static final List<String> PLACE_IDS = List.of(
        "POI001", "POI002", "POI003", "POI004", "POI005",
        "POI006", "POI007", "POI008"
    );

    @Scheduled(fixedDelay = 300000)
    public void collectPlaceCongestion() {
        log.info("혼잡도 수집 시작: {}", LocalDateTime.now());

        for (String placeId : PLACE_IDS) {
            try {
                String url = apiUrl + "/" + apiKey + "/json/citydata/1/1/" + placeId;
                Map response = restTemplate.getForObject(url, Map.class);

                if (response != null) {
                    Map citydata = (Map) response.get("CITYDATA");
                    if (citydata != null) {
                        List<Map> ppltnList = (List<Map>) citydata.get("LIVE_PPLTN_STTS");
                        if (ppltnList != null && !ppltnList.isEmpty()) {
                            String level = (String) ppltnList.get(0).get("AREA_CONGEST_LVL");
                            LocalDateTime now = LocalDateTime.now();

                            PlaceCongestion congestion = PlaceCongestion.builder()
                                .placeId(placeId)
                                .congestionLevel(level)
                                .collectedAt(now)
                                .build();

                            placeCongestionRepository.save(congestion);
                            log.info("저장 완료 - 장소: {}, 혼잡도: {}", placeId, level);

                            // WebSocket 실시간 푸시
                            webSocketService.broadcast(new CongestionMessage(placeId, level, now));
                        }
                    }
                }
            } catch (Exception e) {
                log.error("수집 실패 - 장소: {}, 오류: {}", placeId, e.getMessage());
            }
        }
    }
}
