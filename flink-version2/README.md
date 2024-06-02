# Getting Started
https://nightlies.apache.org/flink/flink-docs-release-1.17/docs/dev/configuration/overview/#getting-started
used maven

```shell
Define value for property 'groupId': com.jongwow.flinkquick
Define value for property 'artifactId': kafka-reader
Define value for property 'version' 1.0-SNAPSHOT: :
Define value for property 'package' com.jongwow.flinkquick: :
Confirm properties configuration:
groupId: com.jongwow.flinkquick
artifactId: kafka-reader
version: 1.0-SNAPSHOT
package: com.jongwow.flinkquick
 Y: : Y
[INFO] ----------------------------------------------------------------------------
[INFO] Using following parameters for creating project from Archetype: flink-quickstart-java:1.17.1
[INFO] ----------------------------------------------------------------------------
[INFO] Parameter: groupId, Value: com.jongwow.flinkquick
[INFO] Parameter: artifactId, Value: kafka-reader
[INFO] Parameter: version, Value: 1.0-SNAPSHOT
[INFO] Parameter: package, Value: com.jongwow.flinkquick
[INFO] Parameter: packageInPathFormat, Value: com/jongwow/flinkquick
[INFO] Parameter: package, Value: com.jongwow.flinkquick
[INFO] Parameter: groupId, Value: com.jongwow.flinkquick
[INFO] Parameter: artifactId, Value: kafka-reader
[INFO] Parameter: version, Value: 1.0-SNAPSHOT
[WARNING] CP Don't override file /Users/jongwow/playground/flink-application-from-scratch/kafka-reader/src/main/resources
[INFO] Project created from Archetype in dir: /Users/jongwow/playground/flink-application-from-scratch/kafka-reader
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  01:10 min
[INFO] Finished at: 2024-06-02T03:17:09+09:00
[INFO] ------------------------------------------------------------------------
```

## Goal
- [ ] DMS 형태의 메세지 파싱
- [ ] Table 이름별로 분류
- [ ] iceberg connector 작성
- [ ] DDL 처리


## Libs

https://nightlies.apache.org/flink/flink-docs-release-1.17/docs/connectors/datastream/formats/json/
https://github.com/apache/flink-cdc