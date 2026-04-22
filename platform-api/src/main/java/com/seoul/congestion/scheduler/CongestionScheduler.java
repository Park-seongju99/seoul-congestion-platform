package com.seoul.congestion.scheduler;

import com.seoul.congestion.domain.PlaceCongestion;
import com.seoul.congestion.repository.PlaceCongestionRepository;
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
public class CongestionScheduler {

    private final PlaceCongestionRepository placeCongestionRepository;
    private final RestTemplate restTemplate;

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

                            PlaceCongestion congestion = PlaceCongestion.builder()
                                .placeId(placeId)
                                .congestionLevel(level)
                                .collectedAt(LocalDateTime.now())
                                .build();

                            placeCongestionRepository.save(congestion);
                            log.info("저장 완료 - 장소: {}, 혼잡도: {}", placeId, level);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("수집 실패 - 장소: {}, 오류: {}", placeId, e.getMessage());
            }
        }
    }
}
