# Bazzar Application
Application for creating some products and package it so it can be sold as one item.

# Prerequisites
  
  - [Java 8](https://adoptopenjdk.net/)
  - [Maven](https://maven.apache.org/)
  - [Lombok](https://www.journaldev.com/18124/java-project-lombok)
  - Active internet connection

# How to use
  
  - Clone this repository or get the zip file
  - Go to the location of this project via terminal/command line
  - Run ```mvn clean install``` command to build this project, make sure internet connection is available
  - Import ```bazzar_db.sql file to bazzar_db database```
  - Run ```java -jar target/[jar name, the one without original text]``` command in terminal to run the application
  - Open http://localhost:8082/swagger-ui.html/ to see the documentation
  
# Application flow

    save new user -> save new product -> save product requirement -> save product package -> save new transaction