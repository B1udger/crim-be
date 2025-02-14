
---

## **📌 README за Backend (Spring Boot)**
Създай файл **`README.md`** в **backend проекта** и сложи този текст:

```md
# CRIM Backend (Spring Boot)

## 📜 Project Description
This is the backend for the **CRIM Chat System**, built with **Spring Boot**.  
It provides REST APIs for user authentication, chat messaging, channel management, and friend requests.

## 🛠️ Tech Stack
- **Spring Boot** (REST API)
- **Spring Data JPA** (Database interaction)
- **H2** (Database)
- **Postman** (API testing)

📂 src/main/java/com/example/crim
 ├── 📂 controller/    # REST Controllers (User, Channel, Message)
 ├── 📂 model/         # Database entities
 ├── 📂 repository/    # JPA Repositories
 ├── 📂 service/       # Business logic
 ├── 📂 config/        # Security & app configs
 ├── 📂 migrations/    # Database schema (Flyway)
 ├── Application.java  # Main Spring Boot application

🔥 API Endpoints
Endpoint	Method	Description
/api/auth/register	POST	Register new user
/api/auth/login	POST	User login
/api/users/{id}/dashboard	GET	Get user dashboard
/api/users/{id}/friends/{friendId}	POST	Send friend request
/api/users/{id}/friend-requests	GET	Get friend requests
/api/channels	POST	Create channel
/api/channels/{id}	DELETE	Delete channel
/api/messages/private	POST	Send private message
/api/messages/channel	POST	Send message in a channel

![image](https://github.com/user-attachments/assets/7b8a9b73-e9ac-4be1-b51c-ca6c95c97704)

🛠️ Built With
Spring Boot
Spring Security
Spring Data JPA
H2
Flyway (Database Migrations)
Postman (API testing)

## 🚀 Installation & Setup

### 1️⃣ Clone the Repository
```sh
git clone https://github.com/B1udger/crim-be.git
cd crim-be
