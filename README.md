# Backend Spring Boot Rest Api 
Basic backend for an e-commerce made in spring boot using a SQL database.

You can see the endpoints documentation in the following [link (wait 2 minutes and refresh)](https://angel-garces-back-end-e-commerce.onrender.com/docs/swagger-ui/index.html)

Also, you can check the front-end of the application in this [link ](https://github.com/AngelVzla99/front-end-e-commerce-next)

# Technology
* JPA and Hibernate - ORM to map the SQL model
* Flyway - To handle migrations in the database
* JWT - User authentication and authorization
* JUnit - For integration testing
* OpenAPI 3.0 and springDocs - REST API documentation

# Database information

The DBMS used is Postgres ( the relational model can be found in src/main/resources/db.migration/V1__Create_first_table.sql ). I'm creating the database in a docker container (can be found in the docker-compose file).

# Documentation
The documentation is generated using springDocs, you can see all the endpoints in the following link after the server running

```
http://BASE_URL/docs/swagger-ui/index.html
```

# Run and test the server locally

## Running the server
Requires Maven and Java17+ to run.
```sh
$ cd backend-e-commerce
$ mvn clean install
$ maven package
$ java -jar target/backend-0.0.1-SNAPSHOT.jar
```

You can also run Spring Boot app with Maven plugin :

```sh
$ mvn spring-boot:run
```

Once the server is setup you should be able to access following URL :
- http://localhost:8080

## Testing

```sh
$ mvn test
```

## Run database in docker container

```sh
$ docker-compose up
```

The data will be saved in a volume folder db-data

# TODO

* Install library for mappers
* Install library for partial updates
* Save user image 
* Save User genre 
* More test cases
