FROM openjdk:17-alpine
RUN mkdir /opt/app
COPY ./build/libs/*.jar /opt/app/
CMD ["java", "-jar", "/opt/app/isoToName-0.0.1-SNAPSHOT.jar"]
