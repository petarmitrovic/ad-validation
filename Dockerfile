FROM eclipse-temurin:21-jammy

ARG app_name=ad-validation

COPY build/libs/ad-validation-*-SNAPSHOT.jar ad-validation.jar

ENTRYPOINT ["java","-jar","/ad-validation.jar"]