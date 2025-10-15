# Database Setup

Create a PostgreSQL database for the STEM Guide platform:

```sql
CREATE DATABASE stem_guide;
CREATE USER stem_guide WITH ENCRYPTED PASSWORD 'stem_guide';
GRANT ALL PRIVILEGES ON DATABASE stem_guide TO stem_guide;
```

Spring Boot is configured to run schema migrations automatically using JPA's `ddl-auto=update`. For production deployments consider using Flyway or Liquibase migrations instead.
