# Personal Health Management System

A comprehensive web-based health management platform built with modern technologies to facilitate personal health data management, role-based access control, and AI-powered health consultation.

## 🚀 Project Overview

The Personal Health Management System is a full-stack web application designed to help users manage their health information, interact with healthcare providers, and receive AI-assisted health guidance. The system supports multiple user roles including administrators, healthcare providers (doctors), and regular users.

### Key Features

- **User Authentication & Authorization**: Secure login with password and Google OAuth integration
- **Role-Based Access Control**: Three-tier permission system (Admin, Doctor, User)
- **Health Data Management**: Comprehensive personal health information tracking
- **AI Health Assistant**: Real-time WebSocket-based AI consultation
- **Responsive Design**: Mobile-friendly interface with modern UI components
- **Multi-language Support**: Internationalization ready

## 🏗️ Architecture

### Technology Stack

**Backend:**
- **Framework**: Spring Boot 2.x
- **Database**: SQLite with HikariCP connection pooling
- **ORM**: MyBatis 3.x
- **Security**: BCrypt password encryption, OAuth 2.0
- **Build Tool**: Maven
- **Java Version**: 8+

**Frontend:**
- **Framework**: Vue.js 2.6.10
- **UI Library**: Element UI 2.13.2
- **State Management**: Vuex 3.1.0
- **Routing**: Vue Router 3.0.6
- **HTTP Client**: Axios 0.18.1
- **Build Tool**: Vue CLI 4.4.4

### System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                        │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │   Vue.js SPA    │  │   Element UI    │  │  WebSocket   │ │
│  │   Frontend      │  │   Components    │  │   Client     │ │
│  └─────────────────┘  └─────────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                    Business Logic Layer                      │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │  Spring Boot    │  │   Controllers   │  │   Services   │ │
│  │   Application   │  │   (REST API)    │  │   (Business  │ │
│  │                 │  │                 │  │    Logic)    │ │
│  └─────────────────┘  └─────────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                    Data Access Layer                         │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │    MyBatis      │  │     SQLite      │  │   Schema     │ │
│  │    Mappers      │  │    Database     │  │ Management   │ │
│  └─────────────────┘  └─────────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

## 📊 Database Schema

The system uses a comprehensive database schema with the following main entities:

- **Users**: User account information with authentication details
- **Roles**: System roles (Admin, Doctor, User)
- **Permissions**: Granular permission system
- **User_Roles**: Many-to-many relationship between users and roles
- **Role_Permissions**: Many-to-many relationship between roles and permissions
- **Health Records**: Personal health data including vital signs, lifestyle information

## 🚦 Current Status

### ✅ Completed Features

**Backend:**
- User authentication and registration system
- Google OAuth integration
- Role-based permission system
- RESTful API endpoints for user/role/permission management
- Database schema with automatic initialization
- Password encryption with BCrypt
- MyBatis integration for data persistence

**Frontend:**
- Vue.js application with Element UI components
- User authentication flow (login/register)
- Dashboard interface
- AI Assistant chat interface with WebSocket
- Responsive layout and navigation
- Route guards for protected pages

### 🔄 In Progress / Planned

- JWT token implementation for stateless authentication
- Unified API response format
- Enhanced security configurations
- Frontend-backend API integration optimization
- Comprehensive health data management features
- Advanced AI health consultation capabilities

## 🛠️ Installation & Setup

### Prerequisites

- Java 8 or higher
- Node.js 14.x or higher
- Maven 3.6+
- Git

### Backend Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd cs673a2f25team2
   ```

2. **Configure database**
   - SQLite database will be automatically created at `${user.home}/CS673/health/health.db`
   - Schema initialization runs automatically on startup

3. **Run the Spring Boot application**
   ```bash
   mvn spring-boot:run
   ```
   
   The backend server will start on `http://localhost:8000`

### Frontend Setup

1. **Navigate to frontend directory**
   ```bash
   cd frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start development server**
   ```bash
   npm run dev
   ```
   
   The frontend will be available at `http://localhost:8080`

### Development Environment

- **Frontend Dev Server**: `http://localhost:8080`
- **Backend API**: `http://localhost:8000`
- **Database**: SQLite file-based storage

## 📚 API Documentation

### Authentication Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | User registration |
| POST | `/api/auth/login` | User login |
| POST | `/api/auth/google-login` | Google OAuth login |
| PUT | `/api/auth/change-password` | Change password |
| PUT | `/api/auth/update-profile` | Update user profile |

### User Management Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users/{id}` | Get user details |
| GET | `/api/users/{id}/roles` | Get user roles |
| GET | `/api/users/{id}/permissions` | Get user permissions |
| POST | `/api/users/{id}/roles/{roleId}` | Assign role to user |
| DELETE | `/api/users/{id}/roles/{roleId}` | Remove role from user |

### Role Management Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/roles` | List all roles |
| GET | `/api/roles/{id}` | Get role details |
| POST | `/api/roles` | Create new role |
| PUT | `/api/roles/{id}` | Update role |
| DELETE | `/api/roles/{id}` | Delete role |

## 🧪 Testing

The project includes comprehensive testing documentation:

- **Unit Tests**: Backend services and frontend components
- **Integration Tests**: API endpoints and database operations
- **Security Tests**: Authentication, authorization, and vulnerability assessment
- **Performance Tests**: Load testing and system performance validation

### Running Tests

**Backend Tests:**
```bash
mvn test
```

**Frontend Tests:**
```bash
cd frontend
npm run test:unit
```

## 🔒 Security Features

- **Password Security**: BCrypt hashing with salt
- **Authentication**: Multi-factor authentication support
- **Authorization**: Role-based access control (RBAC)
- **Input Validation**: Server-side validation for all endpoints
- **SQL Injection Prevention**: MyBatis parameterized queries
- **XSS Protection**: Vue.js template escaping

## 📱 User Roles & Permissions

### Administrator
- Full system access
- User management capabilities
- Role and permission management
- System configuration access

### Doctor/Healthcare Provider
- Access to patient health data (read/write)
- Health data analysis tools
- Limited user management access

### Regular User
- Personal health data management
- Profile management
- AI health consultation access
- Read-only access to own data

## 🤖 AI Assistant

The system features an AI-powered health assistant that provides:
- Real-time health consultation via WebSocket
- Personalized health recommendations
- Interactive chat interface
- Connection resilience and error handling

## 🚀 Deployment

### Development Deployment
- Frontend: Vue CLI development server
- Backend: Spring Boot embedded Tomcat
- Database: SQLite file-based storage

### Production Deployment (Recommended)
- **Containerization**: Docker with docker-compose
- **Web Server**: Nginx for static files and reverse proxy
- **Application Server**: Spring Boot JAR deployment
- **Database**: SQLite or migration to PostgreSQL/MySQL
- **SSL/TLS**: Let's Encrypt certificates

## 📖 Documentation

- **Software Design Document (SDD)**: `docs/Software_Design_Document.md`
- **Software Test Document (STD)**: `docs/Software_Test_Document.md`
- **Project Progress**: `PROGRESS.md`

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is developed as part of CS673 coursework. Please refer to the course guidelines for usage and distribution terms.

## 👥 Team

**CS673 Team 2** - Personal Health Management System Development Team

## 📞 Support

For questions, issues, or contributions, please refer to the project documentation or contact the development team through the course communication channels.

---

**Note**: This project is currently in active development. Some features may be incomplete or subject to change. Please refer to the `PROGRESS.md` file for the most current development status.