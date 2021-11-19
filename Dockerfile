FROM openjdk:11
COPY target/company-server-1.jar main
ENTRYPOINT ["java","-jar","main"]
