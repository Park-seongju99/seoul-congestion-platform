package com.seoul.congestion.scheduler;

import com.seoul.congestion.domain.BusStopCongestion;
import com.seoul.congestion.repository.BusStopCongestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class BusScheduler {

    private final BusStopCongestionRepository busStopCongestionRepository;
    private final RestTemplate restTemplate;

    @Value("${bus.api.key}")
    private String apiKey;

    @Value("${bus.api.url}")
    private String apiUrl;

    @Scheduled(fixedDelay = 300000)
    public void collectBusCongestion() {
        log.info("버스 혼잡도 수집 시작: {}", LocalDateTime.now());

        try {
            String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("apiKey", apiKey)
                .queryParam("type", "json")
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 50)
                .toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> resp = restTemplate.exchange(
                url, HttpMethod.GET, entity, Map.class);
            Map response = resp.getBody();

            if (response != null) {
                List<Map> rows = (List<Map>) response.get("list");
                if (rows != null) {
                    for (Map row : rows) {
                        String itisCd = String.valueOf(row.get("itisCd"));
                        String level = "1546".equals(itisCd) ? "혼잡" : "보통";
                        String sttnId = String.valueOf(row.get("sttnId"));

                        BusStopCongestion congestion = BusStopCongestion.builder()
                            .stopId(sttnId)
                            .stopName(sttnId)
                            .congestionLevel(level)
                            .collectedAt(LocalDateTime.now())
                            .build();

                        busStopCongestionRepository.save(congestion);
                    }
                    log.info("버스 혼잡도 {}건 저장 완료", rows.size());
                }
            }
        } catch (Exception e) {
            log.error("버스 혼잡도 수집 실패: {}", e.getMessage());
        }
    }
}
