FROM openjdk:18
EXPOSE 8080
ADD target/data-search-0.0.1-SNAPSHOT.jar data-import-employee.jar
ENTRYPOINT ["java","-jar","/data-import-employee.jar"]