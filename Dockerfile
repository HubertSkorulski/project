FROM openjdk:11.0.2-jdk
ADD build/libs/final-project-0.0.1-SNAPSHOT.jar .
EXPOSE 8000
CMD java -jar final-project-0.0.1-SNAPSHOT.jar
