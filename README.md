# Todo App - Spring Boot

A full-stack Todo application built with Spring Boot and vanilla JavaScript featuring check/uncheck functionality.

## Features

- ✅ Create new todos with title and description
- ✅ Check/uncheck todos to mark as completed
- ✅ Filter todos (All, Active, Completed)
- ✅ Delete individual todos
- ✅ Delete all completed todos
- ✅ Real-time statistics (Total, Active, Completed)
- ✅ First-screen authentication (login/register with password)
- ✅ Continue as Guest option
- ✅ User-wise isolated todo lists
- ✅ Responsive and modern UI
- ✅ Persistent storage with H2 database

## Technologies

**Backend:**

- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database
- Lombok
- Maven

**Frontend:**

- HTML5
- CSS3
- JavaScript (Vanilla)

## Project Structure

```
todo_app/
├── src/
│   ├── main/
│   │   ├── java/com/example/todoapp/
│   │   │   ├── TodoApplication.java
│   │   │   ├── controller/
│   │   │   │   └── TodoController.java
│   │   │   ├── model/
│   │   │   │   └── Todo.java
│   │   │   ├── repository/
│   │   │   │   └── TodoRepository.java
│   │   │   └── service/
│   │   │       └── TodoService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   │           └── index.html
├── pom.xml
└── README.md
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. Navigate to the project directory:

```bash
cd todo_app
```

2. Build the project:

```bash
mvn clean install
```

3. Run the application:

```bash
mvn spring-boot:run
```

4. Open your browser and navigate to:

```
http://localhost:8080
```

## API Endpoints

### Auth Endpoints

| Method | Endpoint             | Description                      |
| ------ | -------------------- | -------------------------------- |
| POST   | `/api/auth/register` | Register first-time user         |
| POST   | `/api/auth/login`    | Login with username and password |
| POST   | `/api/auth/guest`    | Continue as guest                |
| POST   | `/api/auth/logout`   | Logout current session           |
| GET    | `/api/auth/me`       | Get current session login status |

### Todo Endpoints

Todo endpoints are user-scoped by authenticated session user.
Header `X-User-Name` is still supported for compatibility.

| Method | Endpoint                        | Description                |
| ------ | ------------------------------- | -------------------------- |
| GET    | `/api/todos`                    | Get all todos              |
| GET    | `/api/todos/{id}`               | Get todo by ID             |
| GET    | `/api/todos/status/{completed}` | Get todos by status        |
| POST   | `/api/todos`                    | Create new todo            |
| PUT    | `/api/todos/{id}`               | Update todo                |
| PATCH  | `/api/todos/{id}/toggle`        | Toggle todo completion     |
| DELETE | `/api/todos/{id}`               | Delete todo                |
| DELETE | `/api/todos/completed`          | Delete all completed todos |

## H2 Console

Access the H2 database console at:

```
http://localhost:8080/h2-console
```

**Connection Details:**

- JDBC URL: `jdbc:h2:file:./data/tododb;AUTO_SERVER=TRUE`
- Username: `sa`
- Password: (leave empty)

## Usage

1. **Authenticate First**: Login, register as first-time user, or continue as guest
2. **Add a Todo**: Enter a title and optional description, then click "Add Todo"
3. **Check/Uncheck**: Click the checkbox to mark a todo as completed or active
4. **Filter**: Use the filter buttons to view All, Active, or Completed todos
5. **Delete**: Click the "Delete" button on any todo to remove it
6. **Clear Completed**: Click "Clear Completed" to remove all completed todos at once

## Features Highlight

### Check/Uncheck Functionality

- Simply click the checkbox next to any todo to toggle its completion status
- Completed todos are visually distinguished with strikethrough text and reduced opacity
- The toggle action uses a PATCH request to the `/api/todos/{id}/toggle` endpoint

### Smart Filtering

- View all todos, only active ones, or only completed ones
- Filter state is maintained while performing other operations
- Real-time statistics update based on current data

## License

This project is open source and available under the MIT License.
