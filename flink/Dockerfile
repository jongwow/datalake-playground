FROM flink:1.18.1-java8

RUN mkdir -p /opt/flink/plugins/s3-fs-presto
RUN mv /opt/flink/opt/flink-s3-fs-presto-1.18.1.jar /opt/flink/plugins/s3-fs-presto/
# unlike the officlal docs, flink-clients file did not exist. so need to download manually.
RUN wget -P /opt/flink/lib https://repo1.maven.org/maven2/org/apache/flink/flink-clients/1.18.1/flink-clients-1.18.1.jar

RUN mkdir -p /opt/flink/usrlib
COPY ./build/libs/myapp-0.1-SNAPSHOT-all.jar /opt/flink/usrlib
