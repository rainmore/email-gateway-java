# Email Gateway Example (Java)

Email Gateway Example (Java) is a web base project to provide JOSN REST API to send email via MailGun and SendGrid. The project is based on [Spring Boot](https://projects.spring.io/spring-boot/).

## APIs

Sending mail via SendGrid
```
/api/v1/mail/send/send-grid
```

Sending mail via MailGun
```
/api/v1/mail/send/mail-gun
```
Sending Email via a random email resources (either MailGun or SendGrid)
```
/api/v1/mail/send
```
POST Request Body Structure

```
{
  "from": {email},                             
  "replyTo": {email},     // Optional                             
  "to": {email*},
  "cc": {email*},         // Optional
  "bcc": {email*},        // Optional
  "subject": "test",
  "content": "tesdsfdsa fdsa fda fdasft"
}
```
*Validate Email formats are `felix@rainmore.com.au` or `Felix <felix@rainmore.com.au>`

**Note**
Plain text is only supported for the APIs. HTML or Attachment are not supported yet.

## Developer Guide

### Prelimitary

The project is developed on top JVM with combine Java 8 code and Scala code. It requires following tools on the developer workstation to develop/debug/test application.

* GIT
* JDK 8
* Gradle 4+

### Initialize Project
 
Clone the project code from github repository
```
git clone https://github.com/rainmore/email-gateway-java.git
```

Go to the project folder
```
cd email-gateway-java
```

Setup `gradle wrapper`
```
gradle wrapper --gradle-version 4.4.1 --distribution-type bin
```

### Run Project

The project can be accessed to from http://localhost:8080/api/v1/mail/send

Run project in development mode
```
./gradlew bootRun
```

Run project as jar
```
./gradlew build

java -jar build/lib/application.jar
```

Publish Docker image
```
./gradlew upload
```

Run Test
```
./gradlew test
```

## Design

The project contains 2 layers

* Services
* Controllers

### Services
Services layer contains the business logic. The logic to send email via MailGun and SendGrid is implemented in the layer.

### Controllers
Controllers provide REST JSON endpoints. It contains endpoints, validation and data conversion. To provide an extensible API, versioning has been introduced.  

Current version is `v1`. There could be 2 solutions when more versions are required. The first one is to introduce `v2` package, duplicate all existing end point from `v1`, and, then, modify logic in `v2`. 

The second solution is to split persistent and service layer from controllers as independent micro services. Service discovery should be introduced to automatically connect the service layer with each controller versions. 

Also, DTOs and data conversion have been introduced to avoid direct accessing domain entities. The domain entity modification won't have side effect to API, vice versa.

**Endpoints**

It is built with Spring MVC framework to handle HTTP request and response. Versioning has been applied to each JSON API endpoint through `RequestMapping`.

**Validation**

Strict validation has been applied to the API access. 

* `@PathVariable` instead of `@RequestParam` is used to specific required API request parameters.
* Spring Web MVC build in type check for the request avoid extra parsing logic 
* `org.springframework.validation.Validator` has been bound to the controller to evaluate each POST request data.

**Data Conversion**

Data Conversion has been introduced to convert between domain entity and DTO. An independent converter can be introduced to hold advanced logic. At the moment, the conversion logic is simplified in each DTO.

### Others

#### Test

Testing infrastructure has been introduced in the project. However, during the limited time, no many tests are implemented 

*_TODO_* To implement unit tests and integration tests

#### I18N 

*TODO* to implement i18N

