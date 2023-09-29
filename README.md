# Spring-boot-coordinate-api
A restful web service to calculate the distance between two postal codes in the UK

1. Step to create Spring boot project
   a) Go to this website: https://start.spring.io/ to create a template for the spring boot project
   b) For this project we use Maven, so click Maven
   c) Language click Java
   d) Specify the Project Metadata
   e) For the packaging click Jar and Java version is 17(Default)
   
2. Choose the dependencies of the project by clicking the 'add dependencies' button.
3. The dependencies that we need are:
   a) Spring Web
   b) Spring Data JPA
   c) MySQL Driver
   d) Lombok
   c) To use swagger-ui we can add this dependency
   ```
   	<dependency>
		<groupId>org.springdoc</groupId>
		<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
		<version>2.2.0</version>
	</dependency>
   ```
5. After that import the project as Maven in a preferred IDE like Eclipse or IntelliJ
6. Create a new Schema in the connected server, put the schema name and click Apply button
7. Create a new Database table
8. 
      ```
	CREATE TABLE ukpostcodesmysql.`postcodelatlng`  (
  	`id` INT PRIMARY KEY AUTO_INCREMENT,
  	`postcode` varchar(8) NOT NULL,
  	`latitude` decimal(10,7) NULL,
  	`longitude` decimal(10,7) NULL,
  	PRIMARY KEY (`id`)
	);
     ```
9. Download exsiting data from this website: https://www.freemaptools.com/download-uk-postcode-lat-lng.htm. and choose MySQL ukpostcodesmysql.zip
10. Go to MySQL WorkBench and click Server > Data import > Import from self-contained file > select the downloaded file path > click start Import
11. Go to application.properties to setup database properties

```
spring.datasource.url=jdbc:mysql://localhost:3306/ukpostcodesmysql?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=au153E5Y9%
spring.jpa.show-sql = true

## Hibernate Properties
# The SQL dialect makes Hibernate generate better SQL for the chosen database
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQLDialect
```

API URLs to the the services
1. Calculate Distance between Two Postal Codes:
   a) HTTP Method: GET
   b) URL: http://localhost:8080/PostcodeDistance/calculateDistance?postalCode1=AB10 1XG&postalCode2=AB10 6RM
   Response Body:
```
{
  "location1": {
    "postalCode": "AB10 1XG",
    "latitude": 57.144156,
    "longitude": -2.114864
  },
  "location2": {
    "postalCode": "AB10 6RM",
    "latitude": 57.137872,
    "longitude": -2.121488
  },
  "distance": 0.8,
  "unit": "km"
}
```
2. Update Postal Code Coordinates
   a) HTTP Method: PUT
   b) URL: http://localhost:8080/PostcodeDistance/{postcodeLocationId}
   c) Request body:
   
    ```
	{
  	"postcodeLocationId": 0,
  	"postcode": "string",
  	"latitude": 0,
  	"longitude": 0
	}
    ```
  d) Response body
     ```
	{
  	"postcodeLocationId": 1,
  	"postcode": "AB10 6RN",
  	"latitude": 57.137872,
  	"longitude": -2.121488
	}
      ```

