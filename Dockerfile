#FROM gcr.io/distroless/java:11
FROM gradle:7.3.0-jdk11 AS build-env

WORKDIR /app
COPY /build/libs /app

CMD mkdir /tmp/certs

COPY import_rds_certs.sh /tmp/certs/import_rds_certs.sh
ENV TRUST_STORE_PASSWORD=">scW-X!'5Q=5vQ4Q"
RUN chmod +x /tmp/certs/import_rds_certs.sh && \
    /tmp/certs/import_rds_certs.sh

CMD java -Djavax.net.ssl.trustStore="/tmp/certs/rds-truststore.jks" -Djavax.net.ssl.trustStorePassword="$TRUST_STORE_PASSWORD" -jar conta-postgres-1-all.jar