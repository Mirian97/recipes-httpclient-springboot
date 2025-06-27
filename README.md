# Recipes HTTP Client

A RESTful API built with Spring Boot that interacts with the [dummyjson.com/recipes](https://dummyjson.com/recipes) external API to manage recipes. This project provides endpoints to list, search, create, update, and delete recipes, using Java's `HttpClient` for HTTP requests and Jackson for JSON processing.

## Features

- **List all recipes**: Retrieve a list of all recipes.
- **Get recipe by ID**: Fetch details of a specific recipe by its ID.
- **Search recipes by name**: Search for recipes using a query string.
- **Create a recipe**: Add a new recipe to the external API.
- **Update a recipe**: Update an existing recipe by ID.
- **Delete a recipe**: Remove a recipe by ID.

## Technologies

- **Spring Boot**: Framework for building the REST API.
- **Java 17**: Programming language used.
- **HttpClient**: Java's built-in HTTP client (Java 11+) for making requests to the external API.
- **Jackson**: Library for JSON serialization and deserialization.
- **Maven**: Build tool for dependency management.

## Endpoints

| Method | Endpoint                      | Description            |
| ------ | ----------------------------- | ---------------------- |
| GET    | `/recipes`                    | List all recipes       |
| GET    | `/recipes/{id}`               | Get a recipe by ID     |
| GET    | `/recipes/search?name={name}` | Search recipes by name |
| POST   | `/recipes`                    | Create a new recipe    |
| PUT    | `/recipes/{id}`               | Update a recipe by ID  |
| DELETE | `/recipes/{id}`               | Delete a recipe by ID  |

### Example Requests

- **List all recipes**:

  ```bash
  curl http://localhost:8080/recipes
  ```

- **Get a recipe by ID**:

  ```bash
  curl http://localhost:8080/recipes/1
  ```

- **Search recipes by name**:

  ```bash
  curl http://localhost:8080/recipes/search?name=chicken
  ```

- **Create a recipe**:

  ```bash
  curl -X POST http://localhost:8080/recipes \
       -H "Content-Type: application/json" \
       -d '{"name":"New Recipe","ingredients":["ingredient1","ingredient2"],"instructions":["step1","step2"],"prepTimeMinutes":10,"cookTimeMinutes":20,"servings":4,"difficulty":"Easy","cuisine":"Italian","caloriesPerServing":300,"tags":["tag1","tag2"],"userId":1,"image":"url","rating":5,"reviewCount":10,"mealType":["Dinner"]}'
  ```

- **Update a recipe**:

  ```bash
  curl -X PUT http://localhost:8080/recipes/1 \
       -H "Content-Type: application/json" \
       -d '{"name":"Updated Recipe"}'
  ```

- **Delete a recipe**:
  ```bash
  curl -X DELETE http://localhost:8080/recipes/1
  ```

## Prerequisites

- **Java 17** or later
- **Maven** 3.6.0 or later
- **Git** (optional, for cloning the repository)

## Setup and Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/Mirian97/recipes-httpclient-springboot.git
   cd recipes-httpclient-springboot
   ```

2. **Build the project**:

   ```bash
   mvn clean install
   ```

3. **Run the application**:

   ```bash
   mvn spring-boot:run
   ```

4. **Access the API**:
   The API will be available at `http://localhost:8080/recipes`.

## Configuration

The application uses the following configurations:

- **External API**: Consumes `https://dummyjson.com/recipes`.
- **HttpClient**: Configured with HTTP/2 and a 10-second connection timeout.
- **Error Handling**: Custom `ExternalApiException` with global exception handling via `@ControllerAdvice`.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
