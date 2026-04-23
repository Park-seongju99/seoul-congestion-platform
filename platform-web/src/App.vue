<template>
  <div id="app">
    <header>
      <h1>🗺️ 서울 실시간 혼잡도</h1>
      <span :class="['status', connected ? 'connected' : 'disconnected']">
        {{ connected ? '● 실시간 연결됨' : '● 연결 끊김' }}
      </span>
    </header>
    <main>
      <CongestionGrid :congestions="congestions" />
    </main>
  </div>
</template>

<script>
import { Client } from '@stomp/stompjs'
import SockJS from 'sockjs-client'
import CongestionGrid from './components/CongestionGrid.vue'
import axios from 'axios'

const API_URL = 'http://localhost:8080'

export default {
  components: { CongestionGrid },
  data() {
    return {
      congestions: {},
      connected: false,
      stompClient: null
    }
  },
  async mounted() {
    // 초기 데이터 로드
    try {
      const res = await axios.get(`${API_URL}/api/place/congestion/all`)
      this.congestions = res.data
    } catch (e) {
      console.error('초기 데이터 로드 실패:', e)
    }

    // WebSocket 연결
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS(`${API_URL}/ws`),
      onConnect: () => {
        this.connected = true
        this.stompClient.subscribe('/topic/congestion', (msg) => {
          const data = JSON.parse(msg.body)
          this.congestions[data.placeId] = data.congestionLevel
        })
      },
      onDisconnect: () => { this.connected = false },
      reconnectDelay: 5000
    })
    this.stompClient.activate()
  },
  beforeUnmount() {
    this.stompClient?.deactivate()
  }
}
</script>

<style>
* { box-sizing: border-box; margin: 0; padding: 0; }
body { font-family: 'Segoe UI', sans-serif; background: #f0f2f5; color: #333; }
#app { max-width: 900px; margin: 0 auto; padding: 24px 16px; }
header {
  display: flex; justify-content: space-between; align-items: center;
  margin-bottom: 24px;
}
h1 { font-size: 1.5rem; font-weight: 700; }
.status { font-size: 0.85rem; padding: 4px 12px; border-radius: 20px; }
.connected { background: #e6f7e6; color: #2e7d32; }
.disconnected { background: #fdecea; color: #c62828; }
</style>
