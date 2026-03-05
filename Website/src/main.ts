import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import i18n from './i18n'
import PrimeVue from 'primevue/config'

// Font Awesome
import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(fas)

const app = createApp(App)

app.use(PrimeVue)
app.use(i18n)
app.use(router)

// Font Awesome
app.component('font-awesome-icon', FontAwesomeIcon)

app.mount('#app')
