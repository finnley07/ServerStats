<template>
  <div class="server-dashboard">
    <div v-if="loading" class="loading-container">
      <div class="loading-spinner">
        <div class="spinner"></div>
      </div>
      <p>{{ $t('common.loading') }}</p>
    </div>

    <div v-else-if="error" class="error-container">
      <div class="error-card">
        <i class="fas fa-exclamation-triangle error-icon"></i>
        <h3>{{ $t('common.error') }}</h3>
        <p>{{ error }}</p>
        <button @click="refreshData" class="retry-button">
          <i class="fas fa-sync-alt"></i>
          <span>{{ $t('common.retry') }}</span>
        </button>
      </div>
    </div>

    <div v-else class="dashboard-content">
      <!-- Status Cards -->
      <div class="status-grid">
        <div class="status-card">
          <div class="card-header">
            <font-awesome-icon :icon="['fas', 'server']" />
            <span>{{ $t('dashboard.serverStatus') }}</span>
          </div>
          <div class="card-content">
            <div class="status-badge" :class="getStatusClass(serverData.status?.status)">
              {{ (serverData.status?.status || 'offline').toUpperCase() }}
            </div>
            <div v-if="serverData.serverInfo?.motd" class="motd">
              <strong>{{ $t('dashboard.motd') }}:</strong> {{ serverData.serverInfo.motd }}
            </div>
          </div>
        </div>

        <div class="status-card">
          <div class="card-header">
            <font-awesome-icon :icon="['fas', 'user']" />
            <span>{{ $t('dashboard.players') }}</span>
          </div>
          <div class="card-content">
            <div class="player-count">
              <span class="count-number">
                {{ serverData.playerCount?.online || 0 }}
              </span>
              <span class="count-separator">/</span>
              <span class="count-max">
                {{ serverData.playerCount?.max || 0 }}
              </span>
              <span class="count-label">{{ $t('dashboard.playersOnline') }}</span>
            </div>
          </div>
        </div>

        <div class="status-card">
          <div class="card-header">
            <font-awesome-icon :icon="['fas', 'signal']" />
            <span>{{ $t('dashboard.systemHealth') }}</span>
          </div>
          <div class="card-content">
            <div class="health-status" :class="getHealthClass(serverData.health?.status)">
              {{ (serverData.health?.status || 'unknown').toUpperCase() }}
            </div>
            <div class="last-update">
              <small>
                <i class="fas fa-clock"></i>
                {{ formatDate(serverData.health?.timestamp) }}
              </small>
            </div>
          </div>
        </div>
      </div>

      <!-- Spielerliste -->
      <div class="players-card">
        <div class="card-header">
          <font-awesome-icon :icon="['fas', 'users']" />
          <span>{{ $t('dashboard.activePlayers') }}</span>
          <span class="player-count-badge">
            {{ serverData.players?.players?.length || 0 }}
          </span>
        </div>
        <div class="card-content">
          <div v-if="serverData.players?.players?.length > 0" class="players-container">
            <div class="players-grid">
              <div
                v-for="(player, index) in serverData.players.players"
                :key="index"
                class="player-chip"
              >
                <div class="player-avatar">{{ player.charAt(0) }}</div>
                <span class="player-name">{{ player }}</span>
              </div>
            </div>
          </div>
          <div v-else-if="serverData.playerCount?.online > 0" class="no-data">
            <i class="fas fa-info-circle"></i>
            <p>{{ $t('dashboard.playersAvailableButHidden') }}</p>
          </div>
          <div v-else class="no-data">
            <i class="fas fa-user-slash"></i>
            <p>{{ $t('dashboard.noPlayersOnline') }}</p>
          </div>
        </div>
      </div>

      <!-- Server Info -->
      <div class="info-card">
        <div class="card-header">
          <font-awesome-icon :icon="['fas', 'info']" />
          <span>{{ $t('dashboard.serverInfo') }}</span>
        </div>
        <div class="card-content">
          <div class="info-grid">
            <div class="info-item">
              <div class="info-label">{{ $t('dashboard.onlinePlayers') }}</div>
              <div class="info-value">
                <i class="fas fa-user"></i>
                {{ serverData.serverInfo?.onlinePlayers || 0 }}
              </div>
            </div>
            <div class="info-item">
              <div class="info-label">{{ $t('dashboard.maxPlayers') }}</div>
              <div class="info-value">
                <i class="fas fa-users"></i>
                {{ serverData.serverInfo?.maxPlayers || 0 }}
              </div>
            </div>
            <div class="info-item">
              <div class="info-label">{{ $t('dashboard.status') }}</div>
              <div class="info-value">
                <i :class="getStatusIcon(serverData.status?.status)"></i>
                {{ serverData.status?.status || $t('common.unknown') }}
              </div>
            </div>
            <div class="info-item">
              <div class="info-label">{{ $t('dashboard.lastUpdate') }}</div>
              <div class="info-value">
                <i class="fas fa-history"></i>
                {{ formatDate(serverData.health?.timestamp) }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted } from 'vue'
import { useI18n } from 'vue-i18n'
import ApiService from '@/services/ApiService'

export default {
  name: 'DashboardView',
  setup() {
    const { t } = useI18n()

    const serverData = ref({
      status: null,
      players: null,
      playerCount: null,
      serverInfo: null,
      health: null,
    })

    const loading = ref(true)
    const error = ref(null)
    const refreshInterval = ref(null)

    // Daten laden
    const loadData = async (showLoading = true) => {
      try {
        error.value = null
        if (showLoading) {
          loading.value = true
        }

        const data = await ApiService.getAllServerData()
        serverData.value = data
      } catch (err) {
        console.error('Fehler beim Laden der Daten:', err)
        error.value = err.message || 'Verbindung zum Server fehlgeschlagen'
      } finally {
        if (showLoading) {
          loading.value = false
        }
      }
    }

    const getStatusClass = (status) => {
      switch (status?.toLowerCase()) {
        case 'online':
          return 'status-online'
        case 'offline':
          return 'status-offline'
        default:
          return 'status-unknown'
      }
    }

    const getStatusIcon = (status) => {
      switch (status?.toLowerCase()) {
        case 'online':
          return 'fas fa-check-circle'
        case 'offline':
          return 'fas fa-times-circle'
        default:
          return 'fas fa-question-circle'
      }
    }

    const getHealthClass = (status) => {
      return status?.toLowerCase() === 'healthy' ? 'health-good' : 'health-warning'
    }

    const formatDate = (timestamp) => {
      if (!timestamp) return t('common.unknown')

      const date = new Date(timestamp)
      const locale = localStorage.getItem('locale') || 'de'

      return date.toLocaleString(locale === 'de' ? 'de-DE' : 'en-US', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
      })
    }

    const startAutoRefresh = () => {
      const configInterval = window.SERVER_STATS_CONFIG?.refreshInterval

      const intervalMs =
        typeof configInterval === 'number' && configInterval >= 1000 ? configInterval : 10000

      refreshInterval.value = setInterval(autoRefreshData, intervalMs)
    }

    const refreshData = () => {
      loadData(true)
    }

    const autoRefreshData = () => {
      loadData(false)
    }

    const stopAutoRefresh = () => {
      if (refreshInterval.value) {
        clearInterval(refreshInterval.value)
        refreshInterval.value = null
      }
    }

    onMounted(() => {
      loadData()
      startAutoRefresh()
    })

    onUnmounted(() => {
      stopAutoRefresh()
    })

    return {
      serverData,
      loading,
      error,
      loadData,
      refreshData,
      getStatusClass,
      getStatusIcon,
      getHealthClass,
      formatDate,
    }
  },
}
</script>

<style scoped>
.server-dashboard {
  padding: 2rem;
  background-color: var(--background-color);
  transition: all 0.3s ease;
}

.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 4rem;
  color: var(--text-color-secondary);
}

.loading-spinner {
  width: 50px;
  height: 50px;
  margin-bottom: 1rem;
}

.spinner {
  width: 100%;
  height: 100%;
  border: 4px solid rgba(0, 0, 0, 0.1);
  border-top: 4px solid var(--primary-color);
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.loading-container p {
  margin-top: 1.5rem;
  font-size: 1.2rem;
  font-weight: 500;
}

.error-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 60vh;
}

.error-card {
  text-align: center;
  padding: 3rem;
  background: var(--card-background);
  border-radius: 16px;
  box-shadow: var(--card-shadow);
  border: 2px solid var(--border-color);
  max-width: 500px;
  width: 100%;
}

.error-icon {
  font-size: 4rem;
  color: var(--danger-color);
  margin-bottom: 1.5rem;
}

.error-card h3 {
  font-size: 1.8rem;
  margin-bottom: 1rem;
  color: var(--text-color);
}

.error-card p {
  font-size: 1.1rem;
  margin-bottom: 2rem;
  color: var(--text-color-secondary);
}

.retry-button {
  margin-top: 1rem;
  background: var(--primary-color);
  border: none;
  padding: 0.8rem 2rem;
  font-size: 1.1rem;
  font-weight: 500;
  transition: all 0.2s ease;
  color: white;
  border-radius: 6px;
  cursor: pointer;
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
}

.retry-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.2);
}

.status-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  gap: 2rem;
  margin-bottom: 2.5rem;
}

.status-card {
  background: var(--card-background);
  border-radius: 16px;
  box-shadow: var(--card-shadow);
  border: 2px solid var(--border-color);
  transition: all 0.3s ease;
  padding: 1.5rem;
  height: 100%;
}

.status-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 30px -8px rgba(0, 0, 0, 0.15);
}

.card-header {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  color: var(--text-color);
  font-size: 1.3rem;
  font-weight: 600;
  margin-bottom: 1.5rem;
  padding-bottom: 0.75rem;
  border-bottom: 1px solid var(--border-color-light);
}

.card-header i {
  color: var(--primary-color);
  font-size: 1.2rem;
}

.status-badge {
  display: inline-flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.75rem 1.5rem;
  border-radius: 25px;
  font-weight: 600;
  font-size: 1rem;
  text-transform: uppercase;
  margin-bottom: 1.5rem;
}

.status-online {
  background: rgba(16, 185, 129, 0.15);
  color: var(--status-online);
  border: 2px solid rgba(16, 185, 129, 0.4);
}

.status-offline {
  background: rgba(239, 68, 68, 0.15);
  color: var(--status-offline);
  border: 2px solid rgba(239, 68, 68, 0.4);
}

.status-unknown {
  background: rgba(245, 158, 11, 0.15);
  color: var(--status-unknown);
  border: 2px solid rgba(245, 158, 11, 0.4);
}

.motd {
  margin-top: 1.5rem;
  padding: 1.25rem;
  background: var(--surface-color);
  border-radius: 10px;
  font-size: 1rem;
  color: var(--text-color-secondary);
  border-left: 4px solid var(--primary-color);
}

.motd strong {
  color: var(--text-color);
}

.player-count {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  font-size: 1.7rem;
  font-weight: 700;
  margin: 1.5rem 0;
  justify-content: center;
}

.count-number {
  color: var(--primary-color);
  font-size: 2.5rem;
  font-weight: 800;
}

.count-separator {
  color: var(--text-color-secondary);
  font-size: 1.5rem;
}

.count-max {
  color: var(--text-color-secondary);
  font-size: 1.8rem;
}

.count-label {
  margin-left: 1rem;
  font-size: 1.1rem;
  font-weight: 500;
  color: var(--text-color-secondary);
  white-space: nowrap;
}

.health-status {
  display: inline-flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.75rem 1.5rem;
  border-radius: 25px;
  font-weight: 600;
  font-size: 1rem;
  margin-bottom: 1.5rem;
}

.health-good {
  background: rgba(16, 185, 129, 0.15);
  color: var(--status-online);
  border: 2px solid rgba(16, 185, 129, 0.4);
}

.health-warning {
  background: rgba(245, 158, 11, 0.15);
  color: var(--status-unknown);
  border: 2px solid rgba(245, 158, 11, 0.4);
}

.last-update {
  margin-top: 1.5rem;
  color: var(--text-color-secondary);
  text-align: center;
}

.last-update small {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  font-size: 0.95rem;
}

.last-update i {
  color: var(--primary-color);
}

.players-card {
  background: var(--card-background);
  border-radius: 16px;
  box-shadow: var(--card-shadow);
  border: 2px solid var(--border-color);
  margin-bottom: 2.5rem;
  padding: 1.5rem;
}

.player-count-badge {
  margin-left: auto;
  background: var(--primary-color);
  font-weight: 600;
  padding: 0.25rem 0.75rem;
  border-radius: 12px;
  color: white;
}

.card-header .player-count-badge {
  margin-left: auto;
}

.players-container {
  padding: 0.5rem 0;
  max-height: 250px;
  overflow-y: auto;
  padding-right: 10px;
}

.players-container::-webkit-scrollbar {
  width: 8px;
}
.players-container::-webkit-scrollbar-track {
  background: var(--border-color-light, rgba(0, 0, 0, 0.05));
  border-radius: 4px;
}
.players-container::-webkit-scrollbar-thumb {
  background: var(--primary-color);
  border-radius: 4px;
}
.players-container::-webkit-scrollbar-thumb:hover {
  opacity: 0.8;
}

.players-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  justify-content: flex-start;
}

.player-chip {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.75rem 1.25rem;
  background: var(--surface-color);
  border-radius: 25px;
  transition: all 0.2s ease;
  border: 1px solid var(--border-color);
}

.player-chip:hover {
  background: var(--surface-color-dark);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.player-avatar {
  background: var(--primary-color);
  color: white;
  font-weight: 600;
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1rem;
}

.player-name {
  font-weight: 500;
  color: var(--text-color);
  font-size: 1.05rem;
}

.no-data {
  text-align: center;
  padding: 3rem;
  color: var(--text-color-secondary);
}

.no-data i {
  font-size: 3rem;
  margin-bottom: 1.5rem;
  opacity: 0.7;
  color: var(--warning-color);
}

.no-data p {
  font-size: 1.1rem;
  margin: 0;
  font-weight: 500;
}

.info-card {
  background: var(--card-background);
  border-radius: 16px;
  box-shadow: var(--card-shadow);
  border: 2px solid var(--border-color);
  padding: 1.5rem;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 2rem;
}

.info-item {
  text-align: center;
  padding: 1.5rem;
  background: var(--surface-color);
  border-radius: 12px;
  transition: all 0.3s ease;
  border: 1px solid var(--border-color);
}

.info-item:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 20px -4px rgba(0, 0, 0, 0.15);
}

.info-label {
  font-size: 1rem;
  color: var(--text-color-secondary);
  margin-bottom: 1rem;
  font-weight: 500;
}

.info-value {
  font-size: 1.4rem;
  font-weight: 700;
  color: var(--text-color);
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.75rem;
  flex-wrap: wrap;
}

.info-value i {
  color: var(--primary-color);
  font-size: 1.2rem;
}

@media (max-width: 1024px) {
  .server-dashboard {
    padding: 1.5rem;
  }
  .status-grid {
    gap: 1.5rem;
  }
  .info-grid {
    gap: 1.5rem;
  }
}

@media (max-width: 768px) {
  .server-dashboard {
    padding: 1rem;
  }
  .status-grid {
    grid-template-columns: 1fr;
    gap: 1.5rem;
  }
  .player-count {
    flex-direction: column;
    align-items: center;
    gap: 0.5rem;
    text-align: center;
  }
  .count-label {
    margin-left: 0;
    margin-top: 0.5rem;
  }
  .info-grid {
    grid-template-columns: 1fr;
    gap: 1.25rem;
  }
  .info-item {
    padding: 1.25rem;
  }
  .players-grid {
    justify-content: center;
  }
  .error-card {
    padding: 2rem 1.5rem;
    margin: 0 1rem;
  }
}

@media (max-width: 480px) {
  .server-dashboard {
    padding: 0.75rem;
  }
  .status-card,
  .players-card,
  .info-card {
    padding: 1.25rem;
  }
  .card-header {
    font-size: 1.1rem;
    gap: 0.5rem;
  }
  .status-badge,
  .health-status {
    padding: 0.6rem 1.2rem;
    font-size: 0.9rem;
  }
  .player-count {
    font-size: 1.4rem;
  }
  .count-number {
    font-size: 2rem;
  }
  .info-value {
    font-size: 1.2rem;
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(15px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.status-card,
.players-card,
.info-card {
  animation: fadeIn 0.6s ease-out;
}

@keyframes pulse {
  0% {
    opacity: 1;
  }
  50% {
    opacity: 0.7;
  }
  100% {
    opacity: 1;
  }
}

.loading-container i {
  animation: pulse 2s infinite;
}
</style>
