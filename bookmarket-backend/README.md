# bookmarket-backend

This is the web API server used by the bookmarket website.
It listens to requests on certain REST endpoints and responds accordingly, as well as doing data processing, database stuff, etc.
It is made in Java using the Spring Boot library/framework to develop the APIs, as well as SQLite for the database.

## Setup instructions

### Java

This project uses Java, specifically version 21 aka JDK 21.
It can be downloaded [here](https://www.oracle.com/java/technologies/downloads/?er=221886#java21) or another way if you prefer.

### Maven

It also uses a Java build system called Maven.
It standardizes the publication and usage of Java libraries in addition to a clear build/compilation process.
It can be downloaded [here](https://maven.apache.org/download.cgi) or another way if you prefer.
You probably don't actually need to download Maven, since it provides a way for people to run it without it installed, called "wrappers," but it would be good to install it still.
The "wrappers" are the `mvnw.cmd` and `mvnw` scripts in this folder.
When you want to run Maven, you can just run those instead, depending on if you are on Windows or Unix, respectively.

### SQLite

And for the database, it uses SQLite, which can be downloaded [here](https://www.sqlite.org/download.html).
It's basically just an SQL database that runs on your local machine, using a single file, instead of a whole server.

### IDE

I highly recommend IntelliJ IDEA for writing Java.
It can be downloaded [here](https://www.jetbrains.com/idea/download).
Just scroll down to the "Community edition" and download that one, it's free.

You can also use Eclipse if you prefer (don't recommend tho 💀).
It can be downloaded [here](https://eclipseide.org/).

## How to run

For the future instructions, if you are on Windows, run the `mvnw.cmd` script, and if you are on Unix, run the `mvnw` script.
I'll be writing just `mvnw` because it's shorter.

Maven is the tool that helps us build, compile, package, and run the application.
To run the server as-is, run `./mvnw spring-boot:run`.
This runs a Maven plugin provided by Spring Boot that will start the Spring Boot API server.
To compile the program, run `./mvnw package` or `./mvnw compile`.
There are a lot more commands you can google if you want as well.
