# Scoreboard Application

This project implements a Scoreboard system using Java and Spring Boot. The system tracks the scores of players and provides functionality to add/update players, retrieve the top 10 players, and more.

---

## Features
1. Add or update a single player's score.
2. Add or update multiple players' scores.
3. Retrieve the top 10 players sorted by score in descending order.
4. Thread-safe operations for score management.

---

## **Technologies Used**
- **Java Version**: `17`
- **Spring Boot Version**: `3.4.0`

---

## **How to Run the Project**

### **Prerequisites**
1. Ensure Java 17 or higher is installed.
   - Verify using:
     ```bash
     java -version
     ```
2. Install Maven (optional, if not using an IDE).
   - Verify using:
     ```bash
     mvn -version
     ```
3. Install Git to clone the repository.

---

### **Steps to Run**
1. Clone the repository:
   ```bash
   git clone https://github.com/hirparamilan/scoreboard-app.git
   cd scoreboard-app
2. Build the project:
   - Using Maven:
     ```bash
     mvn clean install
   - Alternatively, use your IDE (e.g., IntelliJ IDEA, Eclipse) to build the project.
3. Run the application:
   - Using Maven:
      ```bash
      mvn spring-boot:run
   - Alternatively, use your IDE's "Run" feature to start the application.
4. Access the API:
   - API Base URL: http://localhost:8080/api/scoreboard

---

### **API Endpoints**
#### **Approch #1 - Using Map & PriorityQueue**

1. Add/Update Single Player
   - Time Complexity : O(log k)
   - POST /add_player
   - Parameters: name (String), score (int)
   - Example Request:
      ```bash
     curl -X POST "http://localhost:8080/api/scoreboard/add_player?name=Alice&score=150"

2. Add/Update Multiple Players
   - POST /add_players
   - Request Body (JSON):
      ```bash
     [
        { "name": "Alice", "score": 150 },
        { "name": "Bob", "score": 100 }
     ]
   - Example Request:
      ```bash
     curl -X POST -H "Content-Type: application/json" -d '[{"name": "Alice", "score": 150}, {"name": "Bob", "score": 100}]' "http://localhost:8080/api/scoreboard/add_players"

3. Get Top 10 Players
    - Time Complexity : O(k log k)
    - GET /get_top10_player
    - Example Request:
      ```bash
      curl -X GET "http://localhost:8080/api/scoreboard/get_top10_player"
    
---

#### **Approch #2 - Using Map & Stream API**

1. Add/Update Single Player
    - Time Complexity : O(1)
    - POST /add_player2
    - Parameters: name (String), score (int)
    - Example Request:
       ```bash
      curl -X POST "http://localhost:8080/api/scoreboard/add_player2?name=Alice&score=150"

2. Add/Update Multiple Players
    - POST /add_players2
    - Request Body (JSON):
       ```bash
      [
         { "name": "Alice", "score": 150 },
         { "name": "Bob", "score": 100 }
      ]
    - Example Request:
       ```bash
      curl -X POST -H "Content-Type: application/json" -d '[{"name": "Alice", "score": 150}, {"name": "Bob", "score": 100}]' "http://localhost:8080/api/scoreboard/add_players2"

3. Get Top 10 Players
    - Time Complexity : O(n log n)
    - GET /get_top10_player
    - Example Request:
      ```bash
      curl -X GET "http://localhost:8080/api/scoreboard/get_top10_player2"

---

### **Containerization with Docker**

1. Build the Docker image:
   ```bash
   docker build -t scoreboard-app .
2. Run the container:
   ```bash
   docker run -p 8080:8080 --name scoreboard-container scoreboard-app
3. Verify the container is running:
   ```bash
   docker ps

---

### **Scaling with Docker Compose & Load Balancing**
* Note : If you are using this, do not use "Containerization with Docker" commands to build and run docker image as compose will do it.
* Note : Comment out or remove scoreboard.yaml file used for Scaling with Kubernetes.
* Here we have created 3 replicas of the scoreboard-app and used nginx to balance traffic across our replicas.
* It will use docker-compose.yml, my_conf.conf and Dockerfile to create image and run containers.
* It will allow us to access our end-point through different containers with the same Base-URL.
1. Build and start the containers:
   ```bash
   docker-compose up --build
---

### **Kubernetes for Scaling (Advanced)**
1. Build the project to generate .jar file:
   ```bash
   mvn clean package
2. Build the Docker image:
   ```bash
   docker build -t scoreboard-app .
3. Deploy the app:
   ```bash
   kubectl apply -f scoreboard.yaml
4. Verify the pods and service:
   ```bash
   kubectl get pods
   kubectl get svc
   kubectl get nodes
   ///// OR ///////
   kubectl get all

   kubectl describe deployment scoreboard-deployment
   kubectl describe svc scoreboard-service
5. Stream Logs for all Pods
   ```bash
   kubectl logs -f -l app=scoreboard-app
6. Verify it is using different pods
- In Postman, Add new header "Connection: close"
   ```bash
   curl -X GET 'http://localhost:8080/api/scoreboard/test' \
  -H 'Connection: close'

---
### **Contact**
For questions or support, please contact:
- Email: hirparamilan@gmail.com
- GitHub: [hirparamilan](https://github.com/hirparamilan)

