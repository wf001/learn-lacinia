FROM amazoncorretto:21


RUN mkdir app

# debugging tools
RUN yum update &&\
yum install -y net-tools procps bind-utils iputils nmap

ADD ./backend/lacinia/target/sakilaapi.jar /app/sakilaapi.jar

WORKDIR /app

CMD ["java", "-jar", "sakilaapi.jar"]

