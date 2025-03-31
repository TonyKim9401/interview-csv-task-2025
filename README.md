## Project Overview
This project includes implementations and test codes related to the first and second interview rounds.

## Folder Structure
![image](https://github.com/user-attachments/assets/66cb1e36-ffdc-4cce-8ab5-d7a305909aa4)

### 1. `demo`
This folder contains a simplified implementation of the design system from the first interview round:
* Handling GET/POST requests for Instagram posts
* Logging using ControllerAdvisor and Spring AOP
* Integration with Elasticsearch, Logstash, and Kibana via Spring Logback and Docker Compose

### 2. `fmpapi`
* Fetching stock information based on company codes using the FMP API
* Utilizing generics to simplify and abstract data structures
* Applying caching to the scheduler for efficient and continuous data updates

### 3. `mar28`
* Contains code implementations from the second interview round on March 28th
* Data processing and statistical analysis
* Converting API JSON responses into Java objects and saving them as CSV files



## How to Run

### 1. `demo`
1. **Prerequisites**
   * Java 17+
   * Docker
   * Gradle
   * Must change `logstach:volumes:/{your-path}/demo/logs:logs`, or logstatsh can not have application log information
![image](https://github.com/user-attachments/assets/6b6804a8-a2f9-462f-8ad3-04a03970ee84)

   
2. **Build & Run**
   ```sh
   ./gradlew build
   docker-compose up
   ```

3. **API Testing**
   ```sh
   curl -X GET http://localhost:8080/instagram/posting/all
   ```

### 2. `fmpapi`
1. Run Main class in fmpapi folder
![image](https://github.com/user-attachments/assets/f1303edd-40cc-4910-9a37-1740e7a2f1f7)
2. Result example
![image](https://github.com/user-attachments/assets/4fde22a2-bb63-4828-902d-f6fef2b896d6)

### 3. `mar28`
1. Run Main class in mar28 folder
![image](https://github.com/user-attachments/assets/52c194ad-460d-44ac-bedb-6ba989294a71)
2. Result example,  `populationData.csv` + `printed result`
![image](https://github.com/user-attachments/assets/4df8e85d-96cd-41ba-8542-8c33d1d4219a)

### The test code should work without any issues
![image](https://github.com/user-attachments/assets/285ef67c-335f-4251-9f5a-a767bffb119a)



## Tech Stack
* **Java 17 –** Programming language for building the application.
* **Spring Boot 3.4.3 –** Framework for building the application, providing various starter modules like web, JPA, and validation.
* **Spring Data JPA –** For working with relational databases using JPA.
* **Spring Web –** For creating RESTful APIs.
* **Spring Validation –** For input validation.
* **JUnit 5 –** Framework for unit and integration testing.
* **Mockito –** Mocking framework for unit testing.
* **Guava –** Google’s core libraries for Java.
* **OkHttp –** HTTP client for making network requests.
* **MockWebServer –** For simulating HTTP responses in tests.
* **Lombok –** Java library to reduce boilerplate code (e.g., getters, setters, constructors).
* **Logback –** Logging framework used for logging.
* **H2 Database –** In-memory database for development and testing.
* **Gradle –** Build automation tool.
* **Docker, Docker Compose -** Containerization
* **Elasticsearch –** A distributed search and analytics engine that stores and indexes large volumes of data, allowing for fast searches and aggregation.
* **Logstash –** A data processing pipeline that ingests, processes, and forwards logs and events to Elasticsearch.
* **Kibana –** A data visualization and exploration tool for Elasticsearch, used to visualize logs and metrics and create custom dashboards for real-time monitoring and analysis.
