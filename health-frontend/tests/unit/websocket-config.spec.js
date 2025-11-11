/**
 * Unit tests for WebSocket configuration
 */
import { describe, it, expect } from 'vitest'

describe('WebSocket Configuration', () => {
  it('should have development config', async () => {
    // Dynamically import to test module
    const { default: config } = await import('@/config/websocket.js')
    
    expect(config).toBeDefined()
    expect(config.wsUrl).toBeDefined()
    expect(typeof config.wsUrl).toBe('string')
  })

  it('should have valid WebSocket URL format', async () => {
    const { default: config } = await import('@/config/websocket.js')
    
    expect(config.wsUrl).toMatch(/^ws:\/\//)
  })

  it('should have localhost in development', async () => {
    const { default: config } = await import('@/config/websocket.js')
    
    // In development mode, should use localhost
    if (import.meta.env.MODE === 'development' || !import.meta.env.MODE) {
      expect(config.wsUrl).toContain('localhost')
    }
  })
})

