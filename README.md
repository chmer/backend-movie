# Backend Movie API

A scalable movie database management API built with Spring Boot, containerized with Docker Compose, and served via an NGINX reverse proxy.

## Prerequisites

- Java 17
- PostgreSQL 15.4
- Docker Compose

## Setup and Running the Application

### Using Docker Compose
1. Clone the repository
2. Navigate to the project directory
3. Build the Docker images: `docker build -t movieapp:latest .`
4. Start the application stack: `docker-compose up -d`
5. Scaling up the application: `docker-compose up --scale movie-api={number-of-desired-containers}`

### Traditional (Without Docker)
1. Clone the repository
2. Navigate to the project directory
3. Build the application: `./gradlew clean build`
4. Run the application: `./gradlew bootRun`

## API Endpoints 

### Add a Movie

- Endpoint: `POST /api/movies`
- Description: Add a movie to the database.
- Payload:
```json
{
  "name": "Movie Name",
  "releaseYear": 2022,
  "rating": 3
}
```
- Curl Command: `curl -X POST http://localhost:8080/api/movies -H "Content-Type: application/json" -d "{\"name\": \"Movie Name\", \"releaseYear\": 2011, \"rating\": 2}"`

### Delete a Movie
- Endpoint: `DELETE /api/movies/{id}`
- Description: Delete an existing movie from the database.
- Path Parameters: `id` (UUID) - The ID of the movie to delete.
- Curl Command: `curl -X DELETE http://localhost:8080/api/movies/{id}`

### List All Movies
- Endpoint: `GET /api/movies`
- Description: Retrieve a paginated list of all existing movies.
- Curl Command: `curl http://localhost:8080/api/movies`


### Update a Movie
- Endpoint: `PUT /api/movies/{id}`
- Description: Update details of an existing movie.
- Path Parameters: `id` (UUID) - The ID of the movie to update.
- Payload:
```json
{
  "name": "Updated Movie Name",
  "releaseYear": 2023,
  "rating": 2
}
```
- Curl Command: `curl -X PUT http://localhost:8080/api/movies/{id} -H "Content-Type: application/json" -d "{\"name\": \"Updated Movie Name\", \"releaseYear\": 2023, \"rating\": 3}"`

### List All Release Years
- Endpoint: `GET /api/movies/years`
- Description: Retrieve a list of distinct release years for all movies.
- Curl Command: `curl http://localhost:8080/api/movies/years`


### List Movies by Release Year
- Endpoint: `GET /api/movies/year/{year}`
- Description: Retrieve movies released in a specific year.
- Path Parameters: `year` (Integer) - The release year.
- Query Parameters: `page`, `size` - Pagination parameters.
- Curl Command: `curl http://localhost:8080/api/movies/year/{year}?page={page}&size={size}`

## Scaling the application
![Scalable-arch.png](..%2FScalable-arch.png)


## Notes
- This API does not implement authentication or authorization.
- Validation and further tests to be added.
- The application uses caching for improved performance; which can be improved for better caching capabilities.
- When using Docker Compose, the NGINX reverse proxy forwards requests to the Spring Boot application, ensuring a more scalable and production-ready setup.