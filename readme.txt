Spring REST-API : it is used with maven build.

//start jetty server with maven plugin
>>mvn clean jetty:run

Open browsers :
http://localhost:8080/api/countries/v1/all


-CRUD REST-APIs :

GET http://localhost:8080/api/categories/v1/all
GET http://localhost:8080/api/cagetoires/v1/{id}

POST http://localhost:8080/api/cagetoires/v1/{id}
DELETE http://localhost:8080/api/cagetoires/v1/{id}
PUT http://localhost:8080/api/cagetoires/v1/{id}

Spring Backend REST-APIs with Security Design

- checkout branch security-api-key : git checkout origin/security-api-key
This branch will show how to implement security with defining own pattern key algorithm. This will use Filter Security pattern. defiend api_key and X-API-Key and whitelist Ips address

- checkout branch security_basic_auth : git checkout origin/security_basic_auth
This branch will show how to implement security with Basic Authentication Header. It also uses Filter Security

- checkout branch interceptor_basic_auth : git checkout origin/interceptor_basic_auth
This branch will show how to implement security with Basic Authentication Hader. It uses Spring Interceptor


