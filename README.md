## Technologies used 
- Spring Cloud
- Docker
- Zipkin
- Spring Security
- Eureka Discovery Service
- Spring Gateway
- Lombok
- MongoDB
- JUnit
- Mockito
- Test Containers
- Google Guava
- Spring Actuator
- Kafka
- Google Jib 

## Features:
- [x] Login/Logout
- [x] View All properties for a user
- [x] Add or Remove propeties
- [ ] Add a tenant
## In Progess:
- [x] fix auth bug for creating property
- [x] fix get all property for user bug
- [x] ensure messaging system works in dockers container ETA 8/13/2023
- [x] change configs for kafka and tenant-service to prevent duplicate keys - ETA 8/14/2023
- [X] Unit Tests for services - ETA 8/15/2023
- [x] Integration Test for services - ETA 8/17/2023
- [ ] Add Application performance metrics - ETA 8/22/2023
- [ ] create front end with angular
- [ ] create jenkins pipeline which runs tests, sonarqube, build, and pushes images

## Future Features 
- [ ] create metrics engine 
- [ ] use kafka to collect payment data
- [ ] create a dash board to display information
- [ ] use redis for caching 
- [ ] put it on kubernetes
