# personDetails

Person Details Information

This REST API application is built using Spring Boot.
To run this application, simply import this project and Run as a "SpringBootApplication".

# Endpoints:

# Person:
1) POST: /api/v1/person: To create the person details with the address details
2) GET: /api/v1/person: To fetch all the person details saved in the DB
3) PUT: /api/v1/person/{id}: To update a person with their 'id'
4) DELETE: /api/v1/person/{id}: To delete a person with their 'id'

# Sample Request for Creating a Person:
```
{
    "firstName": "Pratik",
    "lastName": "Prabhakar",
    "address": {
        "street": "Primrose Gate",
        "city": "Ranchi",
        "state": "Kildare",
        "postalCode": "125466"
    }
}
```

#Address: 
1) GET: /api/v1/address/{id}: To fetch an address details saved in the DB
2) DELETE: /api/v1/address/{id}: To delete an address with their 'id'
3) PUT: /api/v1/address/{id}: To update an address with their 'id'

# Sample Request for Creating a Person:
```
{
    "street": "Primrose Gate",
    "city": "Ranchi",
    "state": "Kildare",
    "postalCode": "7878484"
}
```

Technologies Used:
1) Spring Boot, REST API has been used to create this application.
2) I have also used the H2 in-memory DB (persistence layer) for the application.
3) Testing is done using Mockito.

# Build
For building the project, please use "mvn clean install"

# Run
To run the application use the following command: "mvn spring-boot:run"

You can use "Postman" to access various endpoints.
# DB: 
You can access the in-memory H2 DB using the following link: http://localhost:8080/h2-console/ with username and password used in the application.properties file.
