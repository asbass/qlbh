# Bước 1: Build project bằng Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# Chạy lệnh clean package để tạo file .war (hoặc .jar)
RUN mvn clean package -DskipTests

# Bước 2: Tạo image chạy ứng dụng
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# Copy file đã build từ bước 1 vào (Hãy thay 'ltw-0.0.1-SNAPSHOT.war' bằng tên file trong thư mục target của bạn)
COPY --from=build /app/target/*.war app.war

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.war"]