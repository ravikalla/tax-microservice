# wftfv
This is a "microservice" application intended to be part of a microservice architecture.

This application is configured for Service Discovery and Configuration with the Eureka-JHipster-Registry. On launch, it will refuse to start if it is not able to connect to the Eureka-JHipster-Registry at [http://localhost:8761](http://localhost:8761).

## Development

To start your application in the dev profile, simply run:

    ./mvnw


## Building for production

To optimize the wftfv application for production, run:

    ./mvnw -Pprod clean package

To ensure everything worked, run:

    java -jar target/*.war


## Testing

To launch your application's tests, run:

    ./mvnw clean test

For more information, refer to the [Running tests page][].

## Using Docker to simplify development (optional)

For example, to start a mssql database in a docker container, run:

    docker-compose -f src/main/docker/mssql.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/mssql.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw verify -Pprod dockerfile:build dockerfile:tag@version dockerfile:tag@commit

Then run:

    docker-compose -f src/main/docker/app.yml up -d
