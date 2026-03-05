/// <reference types="vite/client" />
declare module '*.vue' {
  import type { DefineComponent } from 'vue'
  const component: DefineComponent<{}, {}, any>
  export default component
}

interface AppConfig {
  apiUrl: string
  serverName: string
  refreshInterval: number
  debugMode: boolean
}

interface Window {
  SERVER_STATS_CONFIG: AppConfig
}
