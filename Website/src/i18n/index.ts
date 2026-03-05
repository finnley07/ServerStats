import { createI18n } from 'vue-i18n'

const messages = {
  de: {
    dashboard: {
      serverStatus: 'Server Status',
      players: 'Spieler',
      systemHealth: 'System Health',
      activePlayers: 'Aktive Spieler',
      serverInfo: 'Server Informationen',
      onlinePlayers: 'Online Spieler',
      maxPlayers: 'Maximale Spieler',
      status: 'Status',
      lastUpdate: 'Letztes Update',
      motd: 'MOTD',
      playersOnline: 'Spieler online',
      noPlayersOnline: 'Keine Spieler online',
      playersAvailableButHidden: 'Spieler online, aber keine Liste verfügbar',
    },
    common: {
      loading: 'Lade Serverdaten...',
      error: 'Fehler',
      retry: 'Erneut versuchen',
      unknown: 'Unbekannt',
      online: 'ONLINE',
      offline: 'OFFLINE',
      healthy: 'HEALTHY',
      warning: 'WARNING',
    },
    theme: {
      light: 'Light Mode',
      dark: 'Dark Mode',
    },
    languages: {
      de: '🇩🇪 Deutsch',
      en: '🇬🇧 English',
    },
  },
  en: {
    dashboard: {
      serverStatus: 'Server Status',
      players: 'Players',
      systemHealth: 'System Health',
      activePlayers: 'Active Players',
      serverInfo: 'Server Information',
      onlinePlayers: 'Online Players',
      maxPlayers: 'Maximum Players',
      status: 'Status',
      lastUpdate: 'Last Update',
      motd: 'MOTD',
      playersOnline: 'Players online',
      noPlayersOnline: 'No players online',
      playersAvailableButHidden: 'Players online, but no list available',
    },
    common: {
      loading: 'Loading server data...',
      error: 'Error',
      retry: 'Try again',
      unknown: 'Unknown',
      online: 'ONLINE',
      offline: 'OFFLINE',
      healthy: 'HEALTHY',
      warning: 'WARNING',
    },
    theme: {
      light: 'Light Mode',
      dark: 'Dark Mode',
    },
    languages: {
      de: '🇩🇪 German',
      en: '🇬🇧 English',
    },
  },
}

const i18n = createI18n({
  legacy: false,
  locale: localStorage.getItem('locale') || 'de',
  fallbackLocale: 'en',
  messages,
})

export default i18n
