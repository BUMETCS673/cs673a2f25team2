# CS673 Team 2 Project – Personal Health Management

A fresh scaffold for a demo-friendly, English UI web app with a Spring Boot backend and Vue 3 + Vite frontend.

## Overview
- Backend: Spring Boot 3 (Java 21), H2 file database
- Frontend: Vue 3 + TypeScript + Vite
- Dev OS: Windows 11; Maven 3.9.9; Java 21; Docker optional

## Project Structure
```
.
├── backend/           # Spring Boot backend
│   ├── pom.xml
│   ├── src/main/java
│   └── src/main/resources/application.yml
├── frontend/          # Vue 3 + Vite + TS frontend
│   ├── src/
│   └── vite.config.ts
└── 个人健康管理系统设计文档.md  # Chinese design doc (reference)
```

## Quickstart

### Backend (Java 21 + Maven)
Commands:
- Build: `mvn -f backend/pom.xml -DskipTests package`
- Run: `mvn -f backend/pom.xml spring-boot:run`

Services:
- API base: `http://localhost:8080`
- H2 console: `http://localhost:8080/h2-console`

API Endpoints (demo):
- `GET /api/ping` → `ok`
- `POST /api/users/register` → body `{ username, password, role? }` → returns user id
- `POST /api/users/login` → body `{ username, password }` → returns boolean
- `POST /api/health/records` → body `{ userId, dataType, value, unit, notes? }` → returns record
- `GET /api/health/records/{userId}` → returns list

### Frontend (Node 18+ recommended; Node 24 works)
Commands:
- Install: `cd frontend && npm install`
- Dev: `npm run dev` → open `http://localhost:5173/`

Notes:
- UI is English; code and comments are in English.
- For browser calls from `http://localhost:5173` to backend, CORS is enabled for `/api/**`.

## Docker (optional)
- Build jar first: `mvn -f backend/pom.xml -DskipTests package`
- Build image: `docker build -t health-backend ./backend`
- Run: `docker run -p 8080:8080 health-backend`

## Development
- Keep endpoints simple and well-documented.
- Prefer small, composable commits.
- Security (password hashing, auth) to be added later in phases.

## License
Internal coursework project; no external distribution license added.

This repository contains the source code and documentation for CS673 Team 2's project.

## Project Structure

This is a clean development branch ready for new implementation.

## Getting Started

Instructions will be added as the project develops.

## Development

This branch is set up for fresh development. Please follow the team's coding standards and contribution guidelines.

## Contributing

Please create feature branches from this dev branch for new development work.
