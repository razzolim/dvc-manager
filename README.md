# Remote Monitoring and Management (RMM)

### Specifications
- JDK 17

### Cloning the project
```git clone https://github.com/razzolim/dvc-manager.git```

### To build and test the project
```./mvn clean install```

### To run the project
``./mvn spring-boot:run``

### Running the project with Docker
```docker run --name rmm-manager -p 8080:8080 razzolim/dvc-manager```

### Authentication
Authentication is mandatory for all endpoints (swagger included). Use Basic Authentication as:

| username | password |
| ---------| -------- |
| admin | admin


### Documentation
After the application is running, please go to:

```http://localhost:8080/swagger-ui.html``` 

and there you will find all endpoints specifications.

### Mocks
Some values are already mocked into the application according to the given example. You can check them at ```App.java```.
