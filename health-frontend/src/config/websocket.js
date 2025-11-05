// WebSocket 配置
const config = {
    // 开发环境
    development: {
        wsUrl: 'ws://localhost:8080/ws/chat'
    },
    // 生产环境
    production: {
        wsUrl: 'ws://your-production-domain.com/ws/chat'
    }
}

// 获取当前环境
const env = import.meta.env.MODE || 'development'

// 导出当前环境的配置
export default config[env] || config.development