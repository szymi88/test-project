# Teletronics interview application

This application is the REST API returning the number of repositories owned by Github user.

The application was build using `Java 12` and `Spring Boot 2.1.6`

## CONFIGURATION

The data source for this application is Github's REST API.
API URL to get users repositories is defined in application.properties:

```properties
git.repos.api.pattern=https://api.github.com/users/%s/repos
```
## BUILD & RUN

The following tools are required to build the app: 'JDK 12', 'Maven', 'Docker'

Installation steps:
```bash
 mvn package
 docker build -t test-app . 
 docker run -p 80:8080 test-app 
```


## REST API
The REST API to the example app is described below.

Get number of Github projects

Request
```url
GET /projects/{user}
```
Response
```bash
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sat, 22 Jun 2019 09:01:24 GMT
```

```json
{
    "user":"john",
    "projects":30
}
```

## CI
The continues deployment pipeline is configured in using [ Codefresh](https://codefresh.io/).
Every commit to the master branch will trigger the deployment process.

The pipeline is defined in `codefresh.yml` file.

The deployment scripts and docker files location:
`ci-tools`
