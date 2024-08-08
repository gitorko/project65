# Project 65

Spring Webflux & R2DBC

[https://gitorko.github.io/spring-webflux-r2dbc/](https://gitorko.github.io/spring-webflux-r2dbc/)

### Version

Check version

```bash
$java --version
openjdk version "21.0.3" 2024-04-16 LTS
```

### Postgres DB

```bash
docker run -p 5432:5432 --name pg-container -e POSTGRES_PASSWORD=password -d postgres:14
docker ps
docker exec -it pg-container psql -U postgres -W postgres
CREATE USER test WITH PASSWORD 'test@123';
CREATE DATABASE "test-db" WITH OWNER "test" ENCODING UTF8 TEMPLATE template0;
GRANT ALL PRIVILEGES ON DATABASE "test-db" to test;
 
docker stop pg-container
docker start pg-container
```

Ensure you login with test user and create the table.

```bash
docker exec -it pg-container psql -U test -W test-db
\dt
```

Create the table

```sql
CREATE TABLE customer (
   id  SERIAL PRIMARY KEY,
   name VARCHAR(50) NOT NULL,
   age INT NOT NULL
);
```

### Dev

To run the code.

```bash
./gradlew clean build
./gradlew bootRun
```
