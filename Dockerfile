FROM amazoncorretto:17
LABEL maintainer="komorowskidev@gmail.com"
EXPOSE 8080
ADD target/tuicodechallenge-0.0.1-SNAPSHOT.jar tuicodechallenge.jar
ENTRYPOINT ["java", "-jar", "/tuicodechallenge.jar"]
