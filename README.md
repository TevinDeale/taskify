# Taskify

Taskify is a task management application that allows users to create, update, delete, and manage tasks. Users can keep track of their tasks effectively, helping them take control of their day.

## Features

- **Create Tasks**: Add new tasks with titles, descriptions, statuses, and due dates.
- **Read Tasks**: Retrieve tasks by ID, status, or by various date ranges.
- **Update Tasks**: Modify existing tasks or mark them as complete.
- **Delete Tasks**: Remove tasks from the list.
- **Search Tasks**: Search for tasks by title or description.
- **Task Statistics**: Retrieve tasks created, updated, or completed on specific dates.

## Technologies Used

- **Java**: The programming language used to develop the application.
- **Spring Boot**: A framework for building RESTful web services.
- **Spring Data JPA**: For database access and entity management.
- **H2 Database**: An in-memory database for development and testing.
- **Spring Security**: For securing the API endpoints.
- **SpringDoc**: For API documentation using OpenAPI.
- **Maven**: For dependency management and building the application.

## Getting Started

### Prerequisites

Make sure you have the following installed on your machine:

- Java 21 or higher
- Maven

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/<your-username>/taskify.git
   cd taskify

2. Build the project using Maven:
    ```bash
   mvn clean install
   
3. Run the application
   ```bash
   mvn spring-boot:run

### Accessing the API
Once the application is running, you can access the API at http://localhost:8080/api/tasks.

### API Documentation
You can view API documentation at http://localhost:8080/swagger-ui.html to explore available endpoints and their usage.

### Example Requests
Here are some example requests you can use to interact with the API.

#### Creating Tasks

Endpoint: POST /api/tasks/create

#### Request Body:
```json
[
  {
    "title": "Get soda",
    "description": "Go to HEB and buy diet Pepsi",
    "status": "PENDING",
    "dueDate": "10/25/2024"
  },
  {
    "title": "Complete homework",
    "description": "Finish the math assignment",
    "status": "PENDING",
    "dueDate": "10/24/2024"
  },
  {
    "title": "Call Mom",
    "description": "Check in and see how she is doing",
    "status": "PENDING",
    "dueDate": "10/26/2024"
  },
  {
    "title": "Grocery shopping",
    "description": "Buy ingredients for dinner",
    "status": "PENDING",
    "dueDate": "10/27/2024"
  }
]
```

### Running Tests
To run the test suite, execute the following command:
```bash
mvn test
```

### Contributing
Contributions are welcome! Please feel free to submit a pull request or open an issue if you find any bugs or have suggestions for improvements.

### License
This project is licensed under the MIT License.