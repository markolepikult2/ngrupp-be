# Ngrupp Backend

This sample project is a Spring Boot backend for managing events and placing bookings on these events.

## Development strategy
Because of limited time and learning curve roadmap is as follows:
1. Implement basic functionality for events
2. Implment basic functionality for bookings
3. Deploy to Azure
5. Implement security and roles
6. Error handling concepts
7. Logging concepts

Currently steps 1,2,3 are implemented.

## Features
- Event management: list all events, add new events
- Booking management: list bookings for an event, add a booking to an event
- Booking management (one booking per customer per event)
- DTO-based API responses


## API Endpoints

### Events
- `GET /api/events` - List all events
- `POST /api/events` - Add a new event

### Bookings
- `GET /api/booking/{eventId}` - List bookings for an event
- `POST /api/booking` - Add a booking to an event

## Technologies
- Java 17+
- Spring Boot
- Spring Data JPA
- Liquibase
- Jakarta Validation
- Lombok
- H2 Database

## Setup
1. Clone the repository
2. Configure your database in `src/main/resources/application.properties`
3. Run migrations: Liquibase will auto-run on startup
4. Build and run:
   ```bash
   mvn clean install
   mvn spring-boot:run
5.  By default uses embedded H2 database with seeded sample data.

## Database
- Sample data is inserted via Liquibase changelogs in `src/main/resources/db/changelog/`
- Entity concepts:
  - `Event`: Represents an event with a name and date
  - `Booking`: Represents a booking for an event for a customer
  - `Customer`: Represents a customer who can book events

## Development
- DTOs are used for API communication
- Entities, DTO use Lombok
- Input validation is enforced on POST endpoints

## Deployment
Deployment is executed by GitHub Actions and configured for Azure.

## Troubleshooting





