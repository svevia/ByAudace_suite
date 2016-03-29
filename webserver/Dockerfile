from debian 
run apt-get update && \
    apt-get install -y maven openjdk-7-jdk && \
    apt-get clean 
add webserver/pom.xml /srv/ByAudace/
workdir /srv/ByAudace/
run mvn install
add src /srv/ByAudace/src/
expose 8080
cmd mvn jetty:run
