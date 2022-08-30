FROM openjdk:18
EXPOSE 8081
ADD target/data-search-0.0.1-SNAPSHOT.jar data-search.jar
ENTRYPOINT ["java","-jar","/data-search.jar"]