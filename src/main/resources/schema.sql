-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    password_hash TEXT,
    email TEXT UNIQUE,
    full_name TEXT,
    age INTEGER,
    gender TEXT CHECK(gender IN ('MALE', 'FEMALE', 'OTHER')),
    phone TEXT,
    avatar TEXT,
    google_id TEXT UNIQUE,
    login_type TEXT NOT NULL DEFAULT 'PASSWORD' CHECK(login_type IN ('PASSWORD', 'GOOGLE')),
    is_active BOOLEAN NOT NULL DEFAULT 1,
    created_at TEXT NOT NULL,
    updated_at TEXT
);

-- Roles table
CREATE TABLE IF NOT EXISTS roles (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE,
    description TEXT,
    created_at TEXT NOT NULL
);

-- Permissions table
CREATE TABLE IF NOT EXISTS permissions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE,
    description TEXT,
    resource TEXT NOT NULL,
    action TEXT NOT NULL,
    created_at TEXT NOT NULL
);

-- User roles mapping
CREATE TABLE IF NOT EXISTS user_roles (
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    created_at TEXT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Role permissions mapping
CREATE TABLE IF NOT EXISTS role_permissions (
    role_id INTEGER NOT NULL,
    permission_id INTEGER NOT NULL,
    created_at TEXT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

-- Insert default roles
INSERT OR IGNORE INTO roles (name, description, created_at) VALUES 
('ADMIN', '系统管理员', datetime('now')),
('USER', '普通用户', datetime('now')),
('DOCTOR', '医生', datetime('now'));

-- Insert default permissions
INSERT OR IGNORE INTO permissions (name, description, resource, action, created_at) VALUES 
('USER_READ', '查看用户信息', 'USER', 'READ', datetime('now')),
('USER_WRITE', '修改用户信息', 'USER', 'WRITE', datetime('now')),
('USER_DELETE', '删除用户', 'USER', 'DELETE', datetime('now')),
('HEALTH_DATA_READ', '查看健康数据', 'HEALTH_DATA', 'READ', datetime('now')),
('HEALTH_DATA_WRITE', '录入健康数据', 'HEALTH_DATA', 'WRITE', datetime('now')),
('HEALTH_DATA_DELETE', '删除健康数据', 'HEALTH_DATA', 'DELETE', datetime('now')),
('SYSTEM_ADMIN', '系统管理', 'SYSTEM', 'ADMIN', datetime('now'));

-- Assign permissions to roles
INSERT OR IGNORE INTO role_permissions (role_id, permission_id, created_at) 
SELECT r.id, p.id, datetime('now')
FROM roles r, permissions p
WHERE r.name = 'ADMIN';

INSERT OR IGNORE INTO role_permissions (role_id, permission_id, created_at) 
SELECT r.id, p.id, datetime('now')
FROM roles r, permissions p
WHERE r.name = 'USER' AND p.name IN ('USER_READ', 'USER_WRITE', 'HEALTH_DATA_READ', 'HEALTH_DATA_WRITE');

INSERT OR IGNORE INTO role_permissions (role_id, permission_id, created_at) 
SELECT r.id, p.id, datetime('now')
FROM roles r, permissions p
WHERE r.name = 'DOCTOR' AND p.name IN ('USER_READ', 'HEALTH_DATA_READ', 'HEALTH_DATA_WRITE');

