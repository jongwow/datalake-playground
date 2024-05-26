FROM flink:1.18.1-java8

RUN #mkdir -p /opt/flink/plugins/s3-fs-presto
RUN mkdir -p /opt/flink/plugins/s3-fs-hadoop
RUN #mv /opt/flink/opt/flink-s3-fs-presto-1.18.1.jar /opt/flink/plugins/s3-fs-presto/
RUN mv /opt/flink/opt/flink-s3-fs-hadoop-1.18.1.jar /opt/flink/plugins/s3-fs-hadoop/
# unlike the officlal docs, flink-clients file did not exist. so need to download manually.
RUN wget -P /opt/flink/lib https://repo1.maven.org/maven2/org/apache/flink/flink-clients/1.18.1/flink-clients-1.18.1.jar
RUN wget -P /opt/flink/lib https://repo1.maven.org/maven2/com/amazonaws/aws-java-sdk-s3/1.12.691/aws-java-sdk-s3-1.12.691.jar
RUN wget -P /opt/flink/lib https://repo1.maven.org/maven2/org/apache/flink/flink-sql-connector-hive-3.1.2_2.12/1.15.0/flink-sql-connector-hive-3.1.2_2.12-1.15.0.jar
RUN wget -P /opt/flink/lib https://repo1.maven.org/maven2/org/apache/iceberg/iceberg-hive-runtime/1.5.0/iceberg-hive-runtime-1.5.0.jar
RUN wget -P /opt/flink/lib https://repo1.maven.org/maven2/org/apache/flink/flink-table-planner-loader/1.18.1/flink-table-planner-loader-1.18.1.jar
RUN wget -P /opt/flink/lib https://repo1.maven.org/maven2/org/apache/flink/flink-table-runtime/1.18.1/flink-table-runtime-1.18.1.jar
RUN wget -P /opt/flink/lib https://repo1.maven.org/maven2/org/apache/iceberg/iceberg-flink-runtime-1.18/1.5.0/iceberg-flink-runtime-1.18-1.5.0.jar

RUN mkdir -p /opt/flink/usrlib
COPY ./build/libs/myapp-0.1-SNAPSHOT-all.jar /opt/flink/usrlib
