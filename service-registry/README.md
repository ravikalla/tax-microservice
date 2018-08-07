# Eureka JHipster Registry

This is the [JHipster](http://www.jhipster.tech/) registry service, based on [Spring Cloud Netflix](http://cloud.spring.io/spring-cloud-netflix/), [Eureka](https://github.com/Netflix/eureka) and [Spring Cloud Config](http://cloud.spring.io/spring-cloud-config/).

There are a few limitations when deploying to Heroku.

* The registry will only work with [native configuration](http://www.jhipster.tech/jhipster-registry/#spring-cloud-config) (and not Git config).
* The registry service cannot be scaled up to multiple dynos to provide redundancy. You must deploy multiple applications (i.e. click the button more than once). This is because Eureka requires distinct URLs to synchronize in-memory state between instances.

## Running locally

To run the cloned repository;
* For development run `./mvnw -Pdev,webpack` to just start in development or run `./mvnw` and run `yarn && yarn start` for hot reload of client side code.
* For production profile run `./mvnw -Pprod`
