// Simple API client using fetch with token storage
export type HealthData = {
  id?: number
  userId: number
  dataType: string
  value: number
  unit?: string
  notes?: string
  timestamp?: string
}

let inMemoryToken: string | null = null

export function setToken(token: string | null) {
  inMemoryToken = token
  if (token) localStorage.setItem('authToken', token)
  else localStorage.removeItem('authToken')
}

export function getToken(): string | null {
  if (inMemoryToken) return inMemoryToken
  const t = localStorage.getItem('authToken')
  inMemoryToken = t
  return t
}

async function request<T>(path: string, init: RequestInit = {}): Promise<T> {
  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
    ...(init.headers as Record<string, string>),
  }
  const token = getToken()
  if (token) headers['Authorization'] = `Bearer ${token}`

  const res = await fetch(path, { ...init, headers })
  const text = await res.text()
  let data: any = null
  try { data = text ? JSON.parse(text) : null } catch { data = text }
  if (!res.ok) {
    const message = typeof data === 'string' ? data : JSON.stringify(data)
    throw new Error(message || `HTTP ${res.status}`)
  }
  return data as T
}

// Auth APIs
export async function login(username: string, password: string): Promise<string> {
  const data = await request<any>('/api/users/login', {
    method: 'POST',
    body: JSON.stringify({ username, password }),
  })
  const token = typeof data === 'string' ? data : data?.token || data?.accessToken
  if (!token) throw new Error('登录响应未包含令牌')
  setToken(token)
  return token
}

export async function register(username: string, password: string, role: string = 'user'): Promise<any> {
  return request<any>('/api/users/register', {
    method: 'POST',
    body: JSON.stringify({ username, password, role }),
  })
}

// Current user info
export async function me(): Promise<{ id: number; username: string; role: string } | null> {
  try {
    return await request<{ id: number; username: string; role: string }>(
      '/api/me'
    )
  } catch (e) {
    return null
  }
}

// Health data APIs
export async function listHealthRecords(): Promise<HealthData[]> {
  return request<HealthData[]>('/api/health/records')
}

export async function listHealthRecordsByUser(userId: number): Promise<HealthData[]> {
  return request<HealthData[]>(`/api/health/records/${userId}`)
}

export async function createHealthRecord(payload: HealthData): Promise<HealthData> {
  return request<HealthData>('/api/health/records', {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}
