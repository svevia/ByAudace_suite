from debian 
run apt-get update && \
    apt-get install -y maven openjdk-7-jdk && \
    apt-get clean 
add webserver/pom.xml /srv/ByAudace/webserver/
workdir /srv/ByAudace/webserver/
run mvn install
add src /srv/ByAudace/webserver/src/
expose 8080
cmd mvn jetty:run
