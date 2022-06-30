# j4el

# Using this application

# Creating a task

```
POST localhost:8080/task/create
{
    "title": "title 02",
    "description":"description",
    "scheduledDate":"2022-06-30",
    "location":"location",
    "status":"COMPLETED"
}
```

# Grouping by scheduledDate

```
localhost:8080/task?pageNumber=0&pageSize=10&groupBy=scheduledDate
{
    "totalPages": 1,
    "totalElements": 3,
    "pageNumber": 0,
    "pageSize": 10,
    "taskDto": {
        "COMPLETED": [
            {
                "id": 1,
                "title": "title 02",
                "description": "description",
                "scheduledDate": "2022-06-30",
                "location": "location",
                "status": "COMPLETED",
                "updateOn": "2022-06-30"
            }
        ],
        "NOT_COMPLETED": [
            {
                "id": 2,
                "title": "title 04",
                "description": "description",
                "scheduledDate": "2022-06-30",
                "location": "location",
                "status": "NOT_COMPLETED",
                "updateOn": "2022-06-30"
            },
            {
                "id": 3,
                "title": "title 03",
                "description": "description",
                "scheduledDate": "2022-06-30",
                "location": "location",
                "status": "NOT_COMPLETED",
                "updateOn": "2022-06-30"
            }
        ]
    }
}
```

# Grouping by status

```
localhost:8080/task?pageNumber=0&pageSize=10&groupBy=status
{
    "totalPages": 1,
    "totalElements": 4,
    "pageNumber": 0,
    "pageSize": 10,
    "taskDto": {
        "2022-07-30": [
            {
                "id": 4,
                "title": "title 010",
                "description": "description",
                "scheduledDate": "2022-07-30",
                "location": "location",
                "status": "NOT_COMPLETED",
                "updateOn": "2022-06-30"
            }
        ],
        "2022-06-30": [
            {
                "id": 1,
                "title": "title 02",
                "description": "description",
                "scheduledDate": "2022-06-30",
                "location": "location",
                "status": "COMPLETED",
                "updateOn": "2022-06-30"
            },
            {
                "id": 2,
                "title": "title 04",
                "description": "description",
                "scheduledDate": "2022-06-30",
                "location": "location",
                "status": "NOT_COMPLETED",
                "updateOn": "2022-06-30"
            },
            {
                "id": 3,
                "title": "title 03",
                "description": "description",
                "scheduledDate": "2022-06-30",
                "location": "location",
                "status": "NOT_COMPLETED",
                "updateOn": "2022-06-30"
            }
        ]
    }
}
```

# Grouping by location

```
localhost:8080/task?pageNumber=0&pageSize=10&groupBy=location
{
    "totalPages": 1,
    "totalElements": 5,
    "pageNumber": 0,
    "pageSize": 10,
    "taskDto": {
        "location 001": [
            {
                "id": 5,
                "title": "title 200",
                "description": "description",
                "scheduledDate": "2022-07-30",
                "location": "location 001",
                "status": "NOT_COMPLETED",
                "updateOn": "2022-06-30"
            }
        ],
        "location": [
            {
                "id": 1,
                "title": "title 02",
                "description": "description",
                "scheduledDate": "2022-06-30",
                "location": "location",
                "status": "COMPLETED",
                "updateOn": "2022-06-30"
            },
            {
                "id": 2,
                "title": "title 04",
                "description": "description",
                "scheduledDate": "2022-06-30",
                "location": "location",
                "status": "NOT_COMPLETED",
                "updateOn": "2022-06-30"
            },
            {
                "id": 3,
                "title": "title 03",
                "description": "description",
                "scheduledDate": "2022-06-30",
                "location": "location",
                "status": "NOT_COMPLETED",
                "updateOn": "2022-06-30"
            },
            {
                "id": 4,
                "title": "title 010",
                "description": "description",
                "scheduledDate": "2022-07-30",
                "location": "location",
                "status": "NOT_COMPLETED",
                "updateOn": "2022-06-30"
            }
        ]
    }
}
```

