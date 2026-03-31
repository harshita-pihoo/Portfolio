FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY . .

# ✅ FIX: give permission BEFORE running
RUN chmod +x mvnw

# ✅ use bash to be safe
RUN bash mvnw clean install -DskipTests

CMD ["java", "-jar", "target/backend-0.0.1-SNAPSHOT.jar"]