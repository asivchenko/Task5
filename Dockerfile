FROM tomcat:latest
COPY Task5.jar  /usr/local/tomcat/webapps/task5.jar
EXPOSE 8080
CMD ["catalina.sh","run"]