package com.jongwow.flinkquick.deserializer;

import com.jongwow.flinkquick.data.kafka.KafkaStringRecord;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;

public class KafkaDeserializationSchema implements KafkaRecordDeserializationSchema<KafkaStringRecord> {
    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> consumerRecord, Collector<KafkaStringRecord> collector) {
        String value = new String(consumerRecord.value());
        long logAppendTime = consumerRecord.timestamp();
        collector.collect(new KafkaStringRecord(logAppendTime, value));
    }

    @Override
    public TypeInformation<KafkaStringRecord> getProducedType() {
        return TypeInformation.of(KafkaStringRecord.class);
    }
}
