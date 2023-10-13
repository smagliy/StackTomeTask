FROM hseeberger/scala-sbt:11.0.14.1-oraclelinux8_1.6.2_2.13.8

WORKDIR /opt/my-scala-app

COPY src src
COPY project project
COPY build.sbt build.sbt

RUN sbt compile

CMD ["sbt", "~run"]






