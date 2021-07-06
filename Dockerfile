FROM openjdk:16-alpine3.13
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY target/employee-0.0.1-SNAPSHOT.jar employee.jar
EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -jar employee.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar employee.jar
