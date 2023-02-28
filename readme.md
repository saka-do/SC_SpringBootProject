# Surabhi Restaurents

Surabi is a chain of restaurants.They want to come online. So I created a 
spring boot application as part of my GreatLearning MiniProject that provides Api's to perform following tasks.

### Admin tasks
1. Admin  should be able to perform CRUD on Users
2. Admin  should be able to see all the bills getting generated today.
3. Admin  should be able to see the total sale from this month.
4. Admin  should be able to see all the orders done by a specific user.
5. Admin  should be able to perform CRUD on menu and menu items


### Customer tasks
1. Customer should be able to see all the items available along with the price.
2. Customer should be able to select the item I want to order.
3. Customer should be able to order n number of plates per item,
4. Customer should be able to order more than one item.
5. Customer should be able to see my final bill on the screen.
6. Customer should be able to my cart and my order History


### Common Api
1. User can login  into application
2. User can  register into application

## Technologies used
    Java v1.8
    mysql-connector-java v8.0.31
    springfox-swagger2,UI
    jjwt(Java JsonWebToken) v0.9.1
    lombok v1.18.24
    PostMan APi Client
    Spring Boot v2.7.7
        spring-boot-starter-web
        spring-boot-starter-data-jpa (ORM framework used Hibernate)
        spring-boot-starter-security
        spring-boot-devtools
       
## Project packages Breakdown
   Each package in project does it's work ,we will see what those are

#### com.gl.surabhiChains2.controllers
This package contains controllers related to admin,customer and user tasks

_Admin_ Api services are intercepted by a controller class that accepts admin apis ,respective program is written in 
**com.gl.surabhiChains2.controllers.AdminController.java** file

_Customer_ Api services are intercepted by a controller class that accepts customer apis ,respective program is written in 
**com.gl.surabhiChains2.controllers.CustomerController.java** file

#### com.gl.surabhiChains2.config
This package contains configuration classes related to spring security,swagger documentation.

#### com.gl.surabhiChains2.entities
This package contains Entity classes helps repository clases to interacted with dataBase.

#### com.gl.surabhiChains2.filters
contains jwtFilter class that helps in security services like Authentication and Authorization

#### com.gl.surabhiChains2.models
This package contains DataTransferObject classes which helps in transeferring data from server to client.

#### com.gl.surabhiChains2.repositories
This package contains Respository interfaces which helps us to perform CRUD operations with Database.

#### com.gl.surabhiChains2.services
This package contains Bussinees Logic performing clases

#### com.gl.surabhiChains2.udExceptions
This package contains custom created Exception classes that helps us to raise Exceptions when needed

#### com.gl.surabhiChains2.utilities
This package contains some utility classes that are needed in our project
**JwtUtil** class used to generate && validate Jwt token
**ErrorControllerAdvise** class handles the error generated in the project
**UserDetailsImpl** class is an implementation of spring security _UserDetails_ interface

### How to Use the Project
We can use PostMan Api client software to fire services to our sprinBoot application.

## Credits
This project is done by me[K A SATHISH KUMAR] as a single person.
For further queries and suggestions please feel free to contact me.