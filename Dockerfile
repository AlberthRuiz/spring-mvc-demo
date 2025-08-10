FROM openjdk:21-jdk-slim AS build
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/list/*
WORKDIR /app
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests -B
FROM openjdk:21
RUN useradd -r -s /bin/false springuser
WORKDIR /app
COPY --from=build /app/target/spring-mvc-demo-0.0.1-SNAPSHOT.jar app.jar
RUN chown springuser:springuser app.jar
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=default
ENV JAVA_OPTS=" -Xmx512m -Xms256m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar app.jar"  ]

