// WebSocket configuration
const config = {
    // Development environment
    development: {
        wsUrl: 'ws://localhost:8080/ws/chat'
    },
    // Production environment
    production: {
        wsUrl: 'ws://your-production-domain.com/ws/chat'
    }
}

// Get current environment
const env = import.meta.env.MODE || 'development'

// Export configuration for current environment
export default config[env] || config.development