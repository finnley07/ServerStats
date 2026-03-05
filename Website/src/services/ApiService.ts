import axios from 'axios'
import type { AxiosInstance } from 'axios'

interface AllServerData {
  status: any
  players: any
  playerCount: any
  serverInfo: any
  health: any
  hasError: boolean
}

class ApiService {
  private api: AxiosInstance

  constructor() {
    const config = window.SERVER_STATS_CONFIG
    const baseURL = config?.apiUrl

    this.api = axios.create({
      baseURL: baseURL,
      timeout: 5000,
    })
  }

  async getPlayers(): Promise<any> {
    try {
      const response = await this.api.get('/api/players')
      return response.data
    } catch (error) {
      throw this.handleError(error)
    }
  }

  async getStatus(): Promise<any> {
    try {
      const response = await this.api.get('/api/status')
      return response.data
    } catch (error) {
      throw this.handleError(error)
    }
  }

  async getPlayerCount(): Promise<any> {
    try {
      const response = await this.api.get('/api/playercount')
      return response.data
    } catch (error) {
      throw this.handleError(error)
    }
  }

  async getServerInfo(): Promise<any> {
    try {
      const response = await this.api.get('/api/info')
      return response.data
    } catch (error) {
      throw this.handleError(error)
    }
  }

  async getServerHealth(): Promise<any> {
    try {
      const response = await this.api.get('/api/health')
      return response.data
    } catch (error) {
      throw this.handleError(error)
    }
  }

  // Kombinierte Methode für alle Daten mit Promise.allSettled
  async getAllServerData(): Promise<AllServerData> {
    const responses = await Promise.allSettled([
      this.getStatus(),
      this.getPlayers(),
      this.getPlayerCount(),
      this.getServerInfo(),
      this.getServerHealth(),
    ])

    const [statusRes, playersRes, countRes, infoRes, healthRes] = responses

    return {
      status: statusRes.status === 'fulfilled' ? statusRes.value : null,
      players: playersRes.status === 'fulfilled' ? playersRes.value : null,
      playerCount: countRes.status === 'fulfilled' ? countRes.value : null,
      serverInfo: infoRes.status === 'fulfilled' ? infoRes.value : null,
      health: healthRes.status === 'fulfilled' ? healthRes.value : null,
      hasError: responses.some((r) => r.status === 'rejected'),
    }
  }

  private handleError(error: any): Error {
    if (axios.isAxiosError(error)) {
      if (error.response) {
        return new Error(`API Error: ${error.response.status} - ${error.response.statusText}`)
      } else if (error.request) {
        return new Error("Can't connect to server - check your config.js and firewall")
      }
    }
    return new Error(`Request Error: ${error.message}`)
  }
}

export default new ApiService()
