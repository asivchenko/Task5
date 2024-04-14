FROM tomcat:latest
COPY "C:/Users/asivchenko/IdeaProjects/Task5/out/artifacts/Task5_jar/Task5.jar"  /usr/local/tomcat/webapps/task5.jar
EXPOSE 8081
CMD ["catalina.sh","run"]