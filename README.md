# SqlDelight 2.1.x Postgresql migrations with Flyway 

https://github.com/cashapp/sqldelight

Some examples of newer postgresql support in sqldelight SNAPSHOT builds 2.1.x

* CTE (Common Table Expression) can be used to create Parent/Child relationships in one SQL expression, like Many to Many
  * see Actions.sq createBlogWithAuthor 

To find bugs/issues that need to be fixed/implemented

*Bugs*
  * AWAITING FIX https://github.com/cashapp/sqldelight/issues/4938
----

```shell
./gradlew build
./gradlew flywayMigrate
```

Flyway db migrations
https://documentation.red-gate.com/fd/gradle-task-184127407.html
