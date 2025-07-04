## pizza-menu

This service acts as a menu backend for the pizza webapp.


### How to run
Before running, make sure the MongoDB collection is available. You can accomplish this by running `docker-compose up -d` in the root directory of `pizza-project`, which will run all dependencies for all services, or run the same command in the root of this project which will run only the dependencies required for this service.
You can run this service locally using your IDE or the command `mvn spring-boot:run`, which will make the service available on port 8080. Swagger documentation is available [here](http://localhost:8080/swagger-ui/index.html).