FROM alpine:3.21.3


RUN apk add openjdk21 bash maven git curl

RUN mkdir /opt/tomcat
WORKDIR /opt/tomcat
RUN wget https://dlcdn.apache.org/tomcat/tomcat-11/v11.0.6/bin/apache-tomcat-11.0.6.tar.gz
RUN tar xvzf apache-tomcat-11.0.6.tar.gz --strip-components 1 --directory /opt/tomcat

WORKDIR /opt/tomcat

RUN mkdir /build
WORKDIR /build
ADD . .
RUN mvn clean compile package
RUN rm -rf /opt/tomcat/webapps/*
RUN cp -rf target/RedSocialPobreza-1.0-SNAPSHOT.war /opt/tomcat/webapps/ROOT.war



RUN apk del -f maven git
RUN rm -rf /build
RUN rm -f /var/cache/apk/*
WORKDIR /opt/tomcat
CMD ["/opt/tomcat/bin/catalina.sh", "run"]