# 1 стейдж - билдим при помощи мавена главный имеейдж
FROM maven:3.8.5-openjdk-17 AS MAVEN_BUILD

# копируем pom и src код в контейнер
COPY ./ ./

# пакуем код нашего приложения
RUN mvn clean package

# 2 стейдж - билдим при помощи openjdk17
FROM openjdk:17.0.1-jdk-slim

# копируем только необходимые артефакты из 1 стейджа
COPY --from=MAVEN_BUILD /target/labs-0.0.1-SNAPSHOT.jar /demo.jar

# устанаваливаем команды для запуска нашего джарника
CMD ["java", "-jar", "/demo.jar"]