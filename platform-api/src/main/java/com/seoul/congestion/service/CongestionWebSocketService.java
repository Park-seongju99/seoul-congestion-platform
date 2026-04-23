package com.seoul.congestion.service;

import com.seoul.congestion.dto.CongestionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CongestionWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public void broadcast(CongestionMessage message) {
        messagingTemplate.convertAndSend("/topic/congestion", message);
        log.info("WebSocket 브로드캐스트 - 장소: {}, 혼잡도: {}", message.getPlaceId(), message.getCongestionLevel());
    }
}
