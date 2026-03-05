<!-- src/components/LanguageSwitcher.vue -->
<template>
  <div class="language-switcher">
    <select :value="currentLocale" @change="changeLanguage" class="language-select">
      <option v-for="lang in languageOptions" :key="lang.value" :value="lang.value">
        {{ lang.label }}
      </option>
    </select>
  </div>
</template>

<script>
import { computed } from 'vue'
import { useI18n } from 'vue-i18n'

export default {
  name: 'LanguageSwitcher',
  setup() {
    const { locale } = useI18n()

    const languageOptions = [
      { label: '🇩🇪 Deutsch', value: 'de' },
      { label: '🇬🇧 English', value: 'en' },
    ]

    const currentLocale = computed(() => locale.value)

    const changeLanguage = (event) => {
      const newLocale = event.target.value
      locale.value = newLocale
      localStorage.setItem('locale', newLocale)
    }

    return {
      currentLocale,
      languageOptions,
      changeLanguage,
    }
  },
}
</script>

<style scoped>
.language-switcher {
  position: relative;
  min-width: 150px;
}

.language-select {
  width: 100%;
  padding: 0.6rem 1.2rem;
  border-radius: 6px;
  border: 1px solid var(--border-color);
  background: var(--surface-color-dark);
  color: var(--text-color);
  font-size: 0.95rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  appearance: none;
  background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpolyline points='6 9 12 15 18 9'%3e%3c/polyline%3e%3c/svg%3e");
  background-repeat: no-repeat;
  background-position: right 1rem center;
  background-size: 1rem;
  padding-right: 2.5rem;
}

.language-select:hover {
  background-color: var(--surface-color);
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.language-select:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.2);
}

/* Dark Mode Support */
@media (prefers-color-scheme: dark) {
  .language-select {
    background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='white' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpolyline points='6 9 12 15 18 9'%3e%3c/polyline%3e%3c/svg%3e");
  }
}
</style>
