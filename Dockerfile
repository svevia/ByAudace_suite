from debian 
#env http_proxy http://cache.univ-lille1.fr:3128
#env https_proxy http://cache.univ-lille1.fr:3128
#add settings.xml /root/.m2/settings.xml
run apt-get update && \
    apt-get install -y maven openjdk-7-jdk sqlite3 && \
    apt-get clean 
add webserver/pom.xml /srv/ByAudace/webserver/
workdir /srv/ByAudace/webserver/
run mvn install
add webserver/src /srv/ByAudace/webserver/src/
add script_BDD.sql  /srv/ByAudace/
run sqlite3 /tmp/data.db < /srv/ByAudace/script_BDD.sql
expose 8080
cmd mvn jetty:run
