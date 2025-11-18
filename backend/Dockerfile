# Image avec JDK + Maven déjà installés
FROM maven:3.9-eclipse-temurin-25-alpine AS dev

# Dossier de travail dans le conteneur
WORKDIR /app

# Copie du pom.xml en premier (pratique pour le cache Docker)
COPY pom.xml .

RUN mvn -B -q dependency:go-offline

# Copie du code source
COPY src ./src

# Port exposé par Spring Boot
EXPOSE 8080

# Lancement en mode dev
# -DskipTests : on ne lance pas les tests à chaque démarrage
CMD ["mvn", "spring-boot:run", "-DskipTests"]
