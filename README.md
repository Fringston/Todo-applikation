# Todo-applikation
Spring application with secure registration and login where the user can store their todos.

## Description
The Todo application is a RESTful API service built with Java and Spring Boot. It provides a secure platform for users to manage their todos. 
The application features secure user registration and login functionality, and allows users to create, read, update, and delete todos. 
The application uses MySQL for data persistence and Postman for testing the API endpoints. 
It also leverages Spring Security for authentication and authorization, ensuring that only authenticated users can access their respective todos. 
The application is designed with a focus on simplicity and ease of use, making it a great tool for personal task management. 


## Installation

Before you can start this project, you must follow these steps:

- Download and install Postman on your local machine
- Download and install MySQL and MySQL Workbench on your local machine.
- Clone this repository to your local machine.


## Usage

1. Set up the MySQL database:  
- Open MySQL Workbench.
- Connect to your MySQL server.
- Create a new schema for the application. You can do this by clicking on the "Create a new schema in the connected server" button, entering a name for the schema, and clicking "Apply".
- Ensure that the *schema name* *username*, and *password* in the application.properties file match your MySQL setup.

2. Start the application:  
- Open your IDE and run the TodoApplikationApplication class to start the application.

3. Test the application with Postman:  
- Open Postman.
- Create a new request by clicking on the "New" button and selecting "Request".
- Enter the URL for the endpoint you want to test. The base URL will be http://localhost:8080/admin/todos/ (assuming the application is running on port 8080 on your local machine).
- Select the HTTP method for the request (GET, POST, PUT, or DELETE) depending on the operation you want to perform.
- If you're creating a new Todo (POST request) or updating an existing one (PUT request), you'll need to provide the Todo details in the request body. 
Click on the "Body" tab, select "raw", and choose "JSON" from the dropdown menu. Then enter the Todo details in JSON format, for example:
{
"name": "New Todo",
"description": "This is a new todo",
"isDone": false
}
- Click "Send" to send the request. The response will be displayed in the section below.

4. View the data in MySQL Workbench:  
- In MySQL Workbench, open the schema you created for the application.
- Click on the "Tables" section to expand it.
- Right-click on the todos table and select "Select Rows - Limit 1000" to view the data in the table.


## Credits

I received help and inspiration from the following sources:

* [Marcus Henriksson](https://github.com/MarcusRestoryAi)
* [GitHub Copilot Chat](https://docs.github.com/en/copilot/github-copilot-chat/using-github-copilot-chat-in-your-ide)

## Dependencies

* [spring-boot-starter-data-jpa](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa)
* [spring-boot-starter-web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)
* [spring-boot-devtools](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools)
* [mysql-connector-j](https://mvnrepository.com/artifact/com.mysql/mysql-connector-j/8.2.0)
* [spring-boot-starter-test](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test)
* [spring-boot-starter-security](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security)
* [spring-boot-starter-oauth2-resource-server](https://mvnrepository.com/artifact/org.springframework.security.oauth/spring-security-oauth2)




## License

[MIT License](https://choosealicense.com/licenses/mit/)

---
