FROM mysql:8.0

ADD ./infrastructure/db/init/01.sql  /docker-entrypoint-initdb.d/01.sql


ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=sakila
ENV MYSQL_USER=learn-lacinia
ENV MYSQL_PASSWORD=password
ENV BIND-ADDRESS=0.0.0.0

