# eCommerce Application

This project is a part of the "Security and DevOps" section of the "Java Web Developer" Udacity's program. 
Some simplified object model of an e-store is taken as the basis for this project. 
The project uses the following technologies: 
- Spring Boot (as infrastructural base)
- Spring Security (with a JSON web token)
- Spring JPA( for the persistence layer) 
- Slf4j + Logback (for logging features).
Testing covers 90+% of controller layer and implemented with JUnit + Mockito.
For logs analyzing Splunk Enterprise is used. The whole CI/CD flow powered by Git + Jenkins.