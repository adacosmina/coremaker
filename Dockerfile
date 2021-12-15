FROM openjdk:15
VOLUME /tmp
ADD target/coremaker-1.0.0.jar coremaker-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","coremaker-1.0.0.jar"]