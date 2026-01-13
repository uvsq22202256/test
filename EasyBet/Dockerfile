# Dockerfile pour EasyBet Spring Boot Application
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Copier les fichiers Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Donner les permissions d'exécution à gradlew
RUN chmod +x ./gradlew

# Copier le code source
COPY src src

# Construire l'application
RUN ./gradlew bootJar --no-daemon

# Image de production
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copier le JAR depuis l'étape de build
COPY --from=build /app/build/libs/*.jar app.jar

# Exposer le port
EXPOSE 8080

# Variables d'environnement par défaut
ENV SPRING_PROFILES_ACTIVE=docker
ENV SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:29092

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]

