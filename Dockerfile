FROM maven:3.8-amazoncorretto-17 as build
WORKDIR /app
COPY pom.xml .
RUN mvn verify --fail-never

COPY . .
RUN mvn package -DskipTests

FROM amazoncorretto:17-alpine

RUN apk update && apk upgrade && apk add bash
WORKDIR /app
COPY --from=build /app/target/versioner-0.0.1-SNAPSHOT.jar /app/versioner.jar
ENTRYPOINT ["java", "-jar", "/app/versioner.jar"]