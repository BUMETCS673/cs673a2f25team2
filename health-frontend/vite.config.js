/**
 * Code Composition Ratio:
 * - AI Generated: 70%
 * - Manual Development: 30%
 * 
 * Vite build configuration with proxy settings for API development.
 * This follows standard Vite configuration patterns with custom proxy
 * settings for backend API integration.
 */
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
})



