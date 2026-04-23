<template>
  <div class="grid">
    <div
      v-for="(level, placeId) in congestions"
      :key="placeId"
      :class="['card', levelClass(level)]"
    >
      <div class="place-id">{{ placeId }}</div>
      <div class="level">{{ level }}</div>
      <div class="icon">{{ levelIcon(level) }}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: { congestions: Object },
  methods: {
    levelClass(level) {
      if (!level) return ''
      if (level.includes('여유')) return 'low'
      if (level.includes('보통')) return 'medium'
      if (level.includes('약간')) return 'high'
      if (level.includes('붐빔')) return 'very-high'
      return ''
    },
    levelIcon(level) {
      if (!level) return '❓'
      if (level.includes('여유')) return '😊'
      if (level.includes('보통')) return '🙂'
      if (level.includes('약간')) return '😐'
      if (level.includes('붐빔')) return '😰'
      return '❓'
    }
  }
}
</script>

<style scoped>
.grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 16px;
}
.card {
  background: white;
  border-radius: 12px;
  padding: 20px;
  text-align: center;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  transition: transform 0.2s;
}
.card:hover { transform: translateY(-2px); }
.place-id { font-size: 0.85rem; color: #888; margin-bottom: 8px; }
.level { font-size: 1rem; font-weight: 600; margin-bottom: 8px; }
.icon { font-size: 2rem; }

.low    { border-top: 4px solid #4caf50; }
.medium { border-top: 4px solid #ff9800; }
.high   { border-top: 4px solid #f44336; }
.very-high { border-top: 4px solid #9c27b0; background: #fce4ec; }
</style>
