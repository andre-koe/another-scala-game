FROM sbtscala/scala-sbt:eclipse-temurin-17.0.4_1.7.1_3.2.0

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y libxrender1 libxtst6 libxi6 apt-utils xvfb

RUN sbt clean compile

CMD sbt run

