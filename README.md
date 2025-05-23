# The_News_Blog_App

## Project Description

This project is a news site management system. The application is developed using **Spring Boot** and provides functionality for managing news, comments, site users and their registration on the site, loading resources such as images and files.

## Project architecture

The project is divided into modules with a clear division of responsibilities. The main components are:

### 1. Controllers

Process HTTP requests and associate them with services.

- RegistrationController - User registration
- UserController - Management from the site user
- SecurityController - Managing the User Security Section
- NewsController - Managing the news section of the site
- CommentsController - Managing site comments
- FileController - Managing website file uploads
- ImgController - Managing website image loading

### 2. Services

  Contains business logic such as user registration, updating user data, deleting a user from the site, adding a news article to the site, updating, deleting, and getting by ID, you can also get a list of news. Adding comments, updating, getting and deleting, logic for uploading and downloading files from the site.

### 3. Repositories  

Working with a database using Spring Data JPA.

### 4. Entities

Define business models for the database.

### 5. Configuration

- LogInterceptor: logging all requests
- AppConfig: Configuring SpringBoot and connecting interceptors.

  ## Project technologies
- Java 21 is the main working environment.
- Spring Boot is the main framework.
- Spring Data JPA - interaction with the database.
- PostgreSQL is a database for storing information.
- Swagger - for web application documentation
- Logging slf4j - logging application activities
- AOP - for calculating the work of methods and logging through requests
  
