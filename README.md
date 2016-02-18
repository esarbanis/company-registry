# Company Registry

## Building

- Prerequisites: `maven`, `JDK 8`
- To build and run tests: `$>mvn clean package`
- To run standalone: 
```sh
$> java -jar target/company-registry-0.0.2-SNAPSHOT.jar \
    --server.port=8888 \
    --spring.jpa.database=h2 \
    --spring.datasource.platform=h2 \
    --spring.datasource.driver-class-name=org.h2.Driver
```
and navigate to [http://localhost:8888](http://localhost:8888)

## Backend API

 URL | HTTP Verb | Functionality
 ---|---|---
 /company | POST | [Create a new company](#create)
 /company | GET | [Retrieve all companies](#get-one)
 /company/\<COMPANY_ID\> | PUT | [Update an existing company](#update)
 /company/\<COMPANY_ID\> | GET | [Retrieve a single company by its ID](#get-one)
 /company/\<COMPANY_ID\> | DELETE | [Remove a single company by its ID](#remove)
 
### <a name="create"></a>Create a new company
 
#### Valid

Request:
 
```sh
curl -H "Content-Type: application/json" \
     -H "Authorization: Basic ZGVtbzpkZW1v" \
     -X POST \
     -d '{"name":"Mattel Inc.", "address":"333 Continental Boulevard, California", "city":"El Segundo", "country":"USA", "owners":[{"name":"Harold Matson"},{"name":"Elliot Handler"},{"name":"Ruth Handler"}]}' \
     http://company-registry.herokuapp.com/company
```
 
Response:
 
```json
{
  "id": 7,
  "name": "Mattel Inc.",
  "address": "333 Continental Boulevard, California",
  " city": "El Segundo",
  "country": "USA",
  "email": null,
  "phone": null,
  "owners": [
    {
      "id": 12,
      "name": "Harold Matson"
    },
    {
      "id": 13,
      "name": "Elliot Handler"
    },
    {
      "id": 14,
      "name": "Ruth Handler"
    }
  ]
}
```
 
#### Invalid

Request:
 
```sh
curl -H "Content-Type: application/json" \
     -H "Authorization: Basic ZGVtbzpkZW1v" \
     -X POST \
     -d '{}' \
     http://company-registry.herokuapp.com/company
```

Response:
```
[
  {
    "path": "name",
    "message": "may not be empty"
  },
  {
    "path": "city",
    "message": "may not be empty"
  },
  {
    "path": "owners",
    "message": "may not be empty"
  },
  {
    "path": "address",
    "message": "may not be empty"
  },
  {
    "path": "country",
    "message": "may not be empty"
  }
]
```

### <a name="get-all"></a>Retrieve all companies
  
Request:
  
```sh
curl -H "Content-Type: application/json" \
     -H "Authorization: Basic ZGVtbzpkZW1v" \
     -X GET \
     http://company-registry.herokuapp.com/company/
``` 

Response:
   
```json
{
  "content": [
    {
      "id": 3,
      "name": "Facebook Inc.",
      "address": "1 Hacker Way,\nCalifornia94025",
      "city": "Menlo Park",
      "country": "USA",
      "email": null,
      "phone": null,
      "owners": [
        {
          "id": 4,
          "name": "Mark-Zuckerberg"
        }
      ]
    },
    {
      "id": 4,
      "name": "Alphabet Inc.",
      "address": "Mountain View, California",
      "city": "Mountain View",
      "country": "USA",
      "email": null,
      "phone": null,
      "owners": [
        {
          "id": 5,
          "name": "Larry Page"
        },
        {
          "id": 6,
          "name": "Sergey Brin"
        }
      ]
    },
    {
      "id": 5,
      "name": "Google Inc.",
      "address": "Menlo Park,California",
      "city": "Menlo Park",
      "country": "USA",
      "email": null,
      "phone": null,
      "owners": [
        {
          "id": 7,
          "name": "Larry-Page"
        },
        {
          "id": 8,
          "name": "Sergey-Brin"
        }
      ]
    },
    {
      "id": 6,
      "name": "Mattel Inc.",
      "address": "333 Continental Boulevard, California",
      "city": "El Segundo",
      "country": "USA",
      "email": null,
      "phone": null,
      "owners": [
        {
          "id": 9,
          "name": "Harold-Matson"
        },
        {
          "id": 10,
          "name": "Elliot-Handler"
        },
        {
          "id": 11,
          "name": "Ruth-Handler"
        }
      ]
    },
    {
      "id": 7,
      "name": "Mattel Inc.",
      "address": "333 Continental Boulevard, California",
      "city": "El Segundo",
      "country": "USA",
      "email": null,
      "phone": null,
      "owners": [
        {
          "id": 12,
          "name": "Harold-Matson"
        },
        {
          "id": 13,
          "name": "Elliot-Handler"
        },
        {
          "id": 14,
          "name": "Ruth-Handler"
        }
      ]
    }
  ],
  "last": true,
  "totalPages": 1,
  "totalElements": 5,
  "first": true,
  "numberOfElements": 5,
  "sort": null,
  "size": 20,
  "number": 0
}
```
   
### <a name="create"></a>Update an existing company

#### Valid
    
Request:
    
```sh
curl -H "Content-Type: application/json" \
     -H "Authorization: Basic ZGVtbzpkZW1v" \
     -X PUT \
     -d '{"id": 7,"name":"Mattel Inc.", "address":"333 Continental Boulevard, California", "city":"El Segundo", "country":"USA", "owners":[{"id": 12,"name":"Harold Matson"},{"id": 13,"name":"Elliot Handler"},{"id": 14,"name":"Ruth Handler"}]}' \
     http://company-registry.herokuapp.com/company/7
```
    
Response:
    
```json
{
  "id": 7,
  "name": "Mattel Inc.",
  "address": "333 Continental Boulevard, California",
  " city": "El Segundo",
  "country": "USA",
  "email": null,
  "phone": null,
  "owners": [
    {
      "id": 12,
      "name": "Harold Matson"
    },
    {
      "id": 13,
      "name": "Elliot Handler"
    },
    {
      "id": 14,
      "name": "Ruth Handler"
    }
  ]
}
```
 
#### Invalid
    
Request:
    
```sh
curl -H "Content-Type: application/json" \
     -H "Authorization: Basic ZGVtbzpkZW1v" \
     -X PUT \
     -d '{"id": 7,"name":"", "address":"333 Continental Boulevard, California", "city":"El Segundo", "country":"USA", "owners":[{"id": 12,"name":"Harold Matson"},{"id": 13,"name":"Elliot Handler"},{"id": 14,"name":"Ruth Handler"}]}' \
     http://company-registry.herokuapp.com/company/7
```
     
Response:

Response:
```json
[
  {
    "path": "name",
    "message": "may not be empty"
  }
]
```

## <a name="get-one"></a>Retrieve a single company by its ID
  
Request:
  
```sh
curl -H "Content-Type: application/json" \
     -H "Authorization: Basic ZGVtbzpkZW1v" \
     -X GET \
     http://company-registry.herokuapp.com/company/7
```
  
Response:

- Company exists:
```json
{
  "id": 7,
  "name": "Mattel Inc.",
  "address": "333 Continental Boulevard, California",
  " city": "El Segundo",
  "country": "USA",
  "email": null,
  "phone": null,
  "owners": [
    {
      "id": 12,
      "name": "Harold Matson"
    },
    {
      "id": 13,
      "name": "Elliot Handler"
    },
    {
      "id": 14,
      "name": "Ruth Handler"
    }
  ]
}
```

- Company does not exist:
```
Status: 404
```

### <a name="remove"></a>Remove an existing company
    
Request:
    
```sh
curl -H "Content-Type: application/json" \
     -H "Authorization: Basic ZGVtbzpkZW1v" \
     -X DELETE \
     http://company-registry.herokuapp.com/company/7
```

Response:

- Company Exists:
```
Status: 204
```

- Company does not exist:
```
Status: 404
```

## Front End

### Demo Site

You can play around at the [demo site](http://company-registry.herokuapp.com/). 

Provide the following credentials:

username: **demo**

password: **demo**

## Authentication
 
An in memory authentication provider has been setup to authenticate all request to the application.

When accessing the REST api you should provide the Basic Authentication header, for your requests to be served.
 
When accessing the application's UI client, you will be prompted to provide credentials and a Form authentication will be performed before allowing you to access the Company Registry management page.

The best way to setup authentication in REST api level, is token authentication or even OAuth2 authentication, but for a simple project as this it would be an overkill.

## Redundancy

For scaling the application architecture should change and the backend should be completely decoupled form the UI client.

Right now the UI client comes bundled with the backend in a single Spring Boot application as shown in the following diagram.

![Standalone](assets/CompanyRegistryStandAlone.png)

In order to have scaling support the application should split the backend and frontend modules and scale them separately. The only problem with this solution is that the separate modules will not be able to use 2nd level cache.

![Scaled No 2nd level cache](assets/CompanyRegistryScaled.png)

In order to use 2nd level cache in the scaling solution, an additional module should be introduced to manage all IO with the persistent store.

![Scaled No 2nd level cache](assets/CompanyRegistryScaledWithMicroservices.png)

## Known issues

[List of known issues](https://github.com/esarbanis/company-registry/issues/)