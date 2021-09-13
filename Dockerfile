# get gradle image and build application
FROM gradle:7.1.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

# add application inside docker & run
FROM adoptopenjdk/openjdk11:ubi
EXPOSE 8088
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/pasteit-services.jar
ENTRYPOINT ["java","-jar","/app/pasteit-services.jar"]