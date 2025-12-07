# My Backend for the HMCTS dev challenge

I used the https://github.com/hmcts/hmcts-dev-test-backend as an initial framework for my backend.

Run ./gradlew build

Then the Spring application. Ensure PostgreSQL is running locally, and has the DB created (demo_db) and configured as per the application.yaml (port and passwords etc)



# Task API Documentation

## Base URL
`/`

---

## Controller: TaskController

Manages creation of tasks in the system.

---

## Endpoint: Create Task

- **URL:** `/create-task`
- **Method:** `POST`
- **Description:** Creates a new task with default status and a calculated due date.

### Request

- **Headers:**  
  `Content-Type: application/json`

- **Body Parameters:**

| Field         | Type   | Required | Description                         |
|---------------|--------|----------|-------------------------------------|
| `title`       | String | Yes      | Title of the task.                  |
| `description` | String | No       | Detailed description of the task.   |

**Example Request Body:**
```json
{
  "title": "Complete Project Report",
  "description": "Draft and submit the project report by the due date."
}
```

**Response:**

Status Code: 200 OK

Body: JSON object representing the created task.

Example Response Body:
```json
{
  "id": 1,
  "title": "Complete Project Report",
  "description": "Draft and submit the project report by the due date.",
  "status": "PENDING",
  "dueDate": "2025-12-12T14:30:00"
}
```
