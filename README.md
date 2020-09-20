sOnline-Banking-System
It was made using Spring Boot, Spring Security,Spring Data JPA, Spring Data REST,Database is in memory (H2).

about

database schema and data exist in schema.sql and data.sql 

How to run & access the application

The following accounts may be used:
Username: user1

Password: user1 (has normal user rights)

Username: admin

Password: admin (has admin rights)

Use cases illustrated
Add a new client

in postman add login request  with url localhost:8083/login and enter username and password in request body and header
/login request is for genereate a token for used in each requests.



