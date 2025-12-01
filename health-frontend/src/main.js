/**
 * Code Composition Ratio:
 * - AI Generated: 70%
 * - Manual Development: 30%
 * 
 * Application entry point with framework initialization. This file follows
 * standard Vue 3 setup patterns. The configuration was primarily based on
 * framework conventions with minor customizations.
 */
import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import en from 'element-plus/dist/locale/en.mjs'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'

const app = createApp(App)
const pinia = createPinia()

// Register all icons
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.use(ElementPlus, {
  locale: en,
})
app.use(router)
app.use(pinia)
app.mount('#app')


