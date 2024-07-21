# Lottery System REST API

## Overview

The Lottery System REST API allows users to create tickets with multiple lines, check the status of tickets, and manage ticket lines based on specified rules. The system follows a simple set of rules to determine the outcome of each line on a ticket.

## Features

- Create a ticket with multiple lines.
- Retrieve the status of a ticket.
- Amend a ticket before checking its status.
- Retrieve details of a specific ticket or a list of all tickets.
- Interactive API documentation with Swagger.

## Technology Stack

- Java 17
- Spring Boot
- JPA
- h2 in-memory database
- JUnit & Mockito
- Swagger

## Getting Started

### Prerequisites

- Java 17
- Maven
- h2 in-memory database

### Setup Instructions

1. Clone the repository:
- git clone https://github.com/your-repo/lottery-system.git
- cd lottery-system
   
2. Configure the database:

- spring.application.name=lotterysystem
- spring.datasource.url=jdbc:h2:mem:testdb;NON_KEYWORDS=USER
- spring.h2.console.enabled=true
- spring.data.jpa.repositories.bootstrap-mode=default
- spring.jpa.defer-datasource-initialization=true

3. Build the project:
- mvn clean install

4. Run the application:
- mvn spring-boot:run

5. Accessing Swagger UI
- springfox.documentation.swagger-ui.enabled=true
- http://localhost:8080/swagger-ui/


## API Endponts

1. Create a Ticket:

- Endpoint: /ticket
- Method: POST
- Description: Generates a ticket with a specified number of lines.
- Request Body:
   {
     "lines": 5
   }
- Response
   
   {
    "tickets": [
        {
            "ticketId": 1,
            "checked": false,
            "lines": [
                {
                    "number1": 0,
                    "number2": 2,
                    "number3": 2,
                    "result": 0
                },
                {
                    "number1": 2,
                    "number2": 0,
                    "number3": 2,
                    "result": 0
                }
            ]
        }
    ],
    "error": null,
    "success": true
}

2. Retrieve All Tickets

- Endpoint: /ticket
- Method: GET
- Description: Retrieves a list of all tickets.
- Response 
	{
	    "tickets": [
	        {
	            "ticketId": 1,
	            "checked": false,
	            "lines": [
	                {
	                    "number1": 2,
	                    "number2": 0,
	                    "number3": 1,
	                    "result": 0
	                }
	            ]
	        }
	        .....
	    ],
	    "error": null,
	    "success": true
	}

3. Retrieve Specific Ticket

- Endpoint: /ticket/{id}
- Method: GET
- Description: Retrieves details of an individual ticket by ID.
- Response

{
    "tickets": [
        {
            "ticketId": 1,
            "checked": false,
            "lines": [
                {
                    "number1": 2,
                    "number2": 0,
                    "number3": 1,
                    "result": 0
                }
            ]
        }
    ],
    "error": null,
    "success": true
} 

4. Amend Ticket

- Endpoint: /ticket/{id}
- Method: PUT
- Description: Amends the ticket by adding specified number of lines.
- Request 
{
  "lines": 1
}

- Response

{
    "tickets": [
        {
            "ticketId": 1,
            "checked": false,
            "lines": [
                {
                    "number1": 2,
                    "number2": 0,
                    "number3": 1,
                    "result": 0
                },
                {
                    "number1": 2,
                    "number2": 0,
                    "number3": 2,
                    "result": 0
                }
            ]
        }
    ],
    "error": null,
    "success": true
} 


5. Check Ticket Status
- Endpoint: /status/{id}
- Method: PUT
- Description: Retrieves the status of the ticket (i.e., outcomes of lines).
- Response
 {
    "tickets": [
        {
            "ticketId": 1,
            "checked": false,
            "lines": [
                {
                    "number1": 2,
                    "number2": 0,
                    "number3": 1,
                    "result": 1
                }
            ]
        }
    ],
    "error": null,
    "success": true
}

## Business Logic

Lottery Rules Explanation

  1. Line Definition:
  - A ticket consists of multiple lines.
  - Each line contains exactly 3 numbers.
  - Each number can be 0, 1, or 2.
  
  2. Line Outcome Calculation:
  
  - Result 10: If the sum of the values on a line is 2.
  - Result 5: If all three numbers on the line are the same.
  - Result 1: If both the 2nd and 3rd numbers are different from the 1st.
  - Result 0: Otherwise.

## Ticket State Management

 - Tickets can be amended before checking the status.
 - Once the ticket status is checked, it cannot be amended.
 
## Exception Handling

1. Custom Exceptions
  - TicketNotFoundException: Raised when a requested ticket is not found.
  - TicketCheckedException: Raised when attempting to amend a checked ticket.
  - TicketCreationException : Raised while unexpected exception raised by DB during creation 
2. Global Exception Handler
  - Handles and returns appropriate HTTP responses for exceptions.
  
## Exception Handling

 1. Unit Tests
 - TicketControllerTest: Tests for API endpoints using mock data and services.
 
 2. Integration Tests
  - Tests to ensure end-to-end functionality and integration with the database.
 
  
## Data Validation 
  Validate input data to prevent injection attacks and ensure data integrity.
 
	
