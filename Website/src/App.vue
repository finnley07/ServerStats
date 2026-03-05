<template>
  <div id="app">
    <header class="app-header">
      <h1>{{ config.serverName }}</h1>
      <div class="header-controls">
        <LanguageSwitcher />
        <button @click="toggleDarkMode" class="theme-toggle">
          <i :class="darkMode ? 'pi pi-sun' : 'pi pi-moon'"></i>
          <span>{{ darkMode ? $t('theme.light') : $t('theme.dark') }}</span>
        </button>
      </div>
    </header>
    <main class="app-main">
      <router-view />
    </main>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import LanguageSwitcher from '@/components/LanguageSwitcher.vue'

export default {
  name: 'App',
  components: {
    LanguageSwitcher,
  },
  setup() {
    const darkMode = ref(false)

    const config = window.SERVER_STATS_CONFIG || {
      serverName: 'Dashboard',
    }

    const toggleDarkMode = () => {
      darkMode.value = !darkMode.value
      if (darkMode.value) {
        document.documentElement.setAttribute('data-theme', 'dark')
        localStorage.setItem('theme', 'dark')
      } else {
        document.documentElement.removeAttribute('data-theme')
        localStorage.setItem('theme', 'light')
      }
    }

    const loadTheme = () => {
      const savedTheme = localStorage.getItem('theme')
      if (savedTheme === 'dark') {
        darkMode.value = true
        document.documentElement.setAttribute('data-theme', 'dark')
      }
    }

    onMounted(() => {
      loadTheme()
    })

    return {
      darkMode,
      toggleDarkMode,
      config,
    }
  },
}
</script>

<style>
.app-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem 2rem;
  background-color: var(--surface-color);
  border-bottom: 2px solid var(--border-color);
  position: sticky;
  top: 0;
  z-index: 100;
  backdrop-filter: blur(10px);
}

.app-header h1 {
  color: var(--text-color);
  font-size: 2.2rem;
  font-weight: 700;
  margin: 0;
}

.header-controls {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.theme-toggle {
  background: var(--surface-color-dark);
  border: 1px solid var(--border-color);
  color: var(--text-color);
  padding: 0.6rem 1.2rem;
  font-weight: 500;
  transition: all 0.2s ease;
  white-space: nowrap;
  cursor: pointer;
  border-radius: 6px;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.theme-toggle:hover {
  background: var(--surface-color);
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

@media (max-width: 768px) {
  .app-header {
    flex-direction: column;
    gap: 1rem;
    text-align: center;
    padding: 1rem;
  }

  .app-header h1 {
    font-size: 1.8rem;
  }

  .header-controls {
    width: 100%;
    justify-content: center;
  }
}

@media (max-width: 480px) {
  .app-header {
    padding: 0.75rem;
  }

  .app-header h1 {
    font-size: 1.5rem;
  }

  .header-controls {
    flex-direction: column;
    gap: 0.5rem;
  }

  .theme-toggle {
    padding: 0.5rem 1rem;
    font-size: 0.9rem;
  }
}
</style>
