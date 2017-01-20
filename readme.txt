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



