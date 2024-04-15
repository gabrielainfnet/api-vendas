FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /usr/src/app
COPY . /usr/src/app

RUN mvn -Dmaven.test.skip=true package

RUN ls -la /usr/src/app/target/

FROM tomcat:10.0.16-jdk17-openjdk

WORKDIR /usr/local/tomcat/webapps/
COPY --from=builder /usr/src/app/target/*.war /usr/local/tomcat/webapps/api-vendas.war

RUN ls -la /usr/local/tomcat/webapps/

EXPOSE 8080
ENTRYPOINT ["catalina.sh", "run"]