# Getting Started

## Goal of the project is to compare batch insert into database performance using different approaches

### Prerequisites

* running postgreSql server


### Building and running application
The following guides illustrate how to build and run the application:

* open terminal
* execute command `gradlew clean build`
* execute command `gradlew bootRun`


### How to execute benchmark locally
After microservice is running, send one of the following requests:

* [http://localhost:8080/save/100000/](http://localhost:8080/save/100000/) - for saving 100000 rows of data
* [http://localhost:8080/saveAll/100000/](http://localhost:8080/saveAll/100000/) - for saving 100000 rows of data
* [http://localhost:8080/savePS/100000/](http://localhost:8080/savePS/100000/) - for saving 100000 rows of data

### References (used articles)
#### JPA/Hibernate
* https://www.baeldung.com/jpa-hibernate-batch-insert-update
#### PostgreSql
* https://jdbc.postgresql.org/documentation/head/connect.html - reWriteBatchedInserts

#### Liqibase
* https://www.baeldung.com/liquibase-refactor-schema-of-java-app

# Result of benchmark

| used method                      | number of inserts           | seconds          | ram used|
| ---------------------------------|----------------------------:|-----------------:|--------:|
| PreparedStatement [native query] | 100 000                     |             0.738|       1G|
|                                  | 1 000 000                   |             9.784|       1G|
|                                  | 5 000 000                   |  OutOfMemoryError|       1G|
| Hibernate [saveAll]              | 100 000                     |            20.702|       1G|
|                                  | 1 000 000                   |           209.759|       1G|
|                                  | 5 000 000                   |  OutOfMemoryError|       1G|
| Hibernate [save]                 | 100 000                     |                  |       1G|
|                                  | 1 000 000                   |                  |       1G|
|                                  | 5 000 000                   |  OutOfMemoryError|       1G|
| Hibernate [entityManager persist]| 100 000                     |             5.864|       1G|
|                                  | 1 000 000                   |            59.329|       1G|
|                                  | 5 000 000                   |  OutOfMemoryError|       1G|
| Hibernate [sessionFactory]       | 100 000                     |             16.27|       1G|
|                                  | 1 000 000                   |           163.872|       1G|
|                                  | 5 000 000                   |                  |       1G|
| JDBC Template [jdbcTemplate]     | 100 000                     |             0.795|       1G|
|                                  | 1 000 000                   |            10.270|       1G|
|                                  | 5 000 000                   |  OutOfMemoryError|       1G|