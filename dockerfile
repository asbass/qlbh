# Bước 1: Build ứng dụng bằng Maven với Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Bước 2: Tạo image chạy ứng dụng (chỉ cần JRE 21)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copy file war từ bước build sang
COPY --from=build /app/target/*.war app.war
ENTRYPOINT ["java", "-jar", "app.war"]
