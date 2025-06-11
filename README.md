# Movie Collection Spring Boot Application

A simple Spring Boot REST API for managing a movie collection, fetching movie data from OMDb and TMDb, and storing it in an H2 database.

## Features

- Add movies by title (fetches info from OMDb and images from TMDb)
- List movies with pagination
- Mark movies as watched/unwatched
- Rate movies (1–5)
- Delete movies
- Download and store movie images

## API Endpoints

| Method | Endpoint                        | Description                        |
|--------|---------------------------------|------------------------------------|
| POST   | `/api/movies/{title}`           | Add a movie by title               |
| GET    | `/api/movies`                   | List movies (supports `page` and `size` query params) |
| PATCH  | `/api/movies/{id}/watched`      | Set watched status (`watched=true/false`) |
| PATCH  | `/api/movies/{id}/rating`       | Set rating (`rating=1..5`)         |
| DELETE | `/api/movies/{id}`              | Delete a movie                     |

## Example Usage

Add a movie:
```sh
curl -X POST http://localhost:8080/api/movies/Rocky
```

List movies:
```sh
curl http://localhost:8080/api/movies
```

Mark as watched:
```sh
curl -X PATCH "http://localhost:8080/api/movies/1/watched?watched=true"
```

Set rating:
```sh
curl -X PATCH "http://localhost:8080/api/movies/1/rating?rating=5"
```

Delete a movie:
```sh
curl -X DELETE http://localhost:8080/api/movies/1
```

## Running the Application

1. Clone the repository.
2. Set your OMDb and TMDb API keys in `src/main/resources/application.properties`:
    ```
    omdb.api.key=YOUR_OMDB_KEY
    tmdb.api.key=YOUR_TMDB_KEY
    ```
3. Build and run:
    ```sh
    mvn clean install
    mvn spring-boot:run
    ```
4. Access the API at [http://localhost:8080/api/movies](http://localhost:8080/api/movies)

## Running Tests

```sh
mvn test
```

## Notes

- Images are downloaded to `src/main/resources/static/images/`.
- The app uses an in-memory H2 database by default. Access the H2 console at [http://localhost:8080/h2-console](http://localhost:8080/h2-console) (JDBC URL: `jdbc:h2:mem:moviesdb`).

---

**Enjoy the movie collection!**