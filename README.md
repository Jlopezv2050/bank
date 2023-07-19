# bank

Little API Bank REST. Take in consideration that the present repo is an aproximation of a real enterprise project. In real situation:
* Do a deeper domain design establishing contexts, aggregates, values objects and consider split in multiple micros.
* Consider implement Command Query and Domain events patterns to fit with the bank features of intensive views of the balance while movement transactions are doing.
* Save timestamps and do a distributed trace in order to audit the system.
* Consider the sync/async operations flavor.
* Implement writing code conventions, do validations and all the test related with each layer/function.

## FeatureSet
* User and related account creation.
  * User have to be unique.
* Deposit into the user's account.
  * If N deposits are done concurrently, the amount remain optimistic lock by de first request then is release into the next and so on. 
* Tranfer amount from user's acconunt to another user's account.
  * Check there are enough founds. 
* Account's movements and balance.

## Getting Started

Standalone maven based app, run:

```bash
$ export JAVA_HOME=/home/javadev/.jdks/openjdk-20.0.1/
$ mvn spring-boot:run
```

### Prerequisites

* Oracle Open JDK 20.0.0 (only do the export (is session life) if it's not your java version.
* Maven 3.3.9

### DataBase Section

H2 in memory bbdd used.
Access UI H2 Dashboard via: [H2 UI Login](http://localhost:8080/h2-ui/login.jsp) 
* JDBC URL: jdbc:h2:mem:testdb
* No pwd required

## Services exposure

Services availables at: [Swagger](http://localhost:8080/swagger-ui/index.html) 

<img src="swagger.png" width="340" height="290">


