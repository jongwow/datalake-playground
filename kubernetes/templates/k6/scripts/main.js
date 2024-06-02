/// <reference path="./index.d.ts" />

import { uuidv4 } from "https://jslib.k6.io/k6-utils/1.4.0/index.js";
import { randomString } from "https://jslib.k6.io/k6-utils/1.2.0/index.js";
import {sleep} from 'k6';
import {
    Writer,
    SASL_PLAIN,
    SchemaRegistry,
    SCHEMA_TYPE_JSON
} from "k6/x/kafka";


const bootstrap = "kafka:9092";

const saslConfig = {
    algorithm: SASL_PLAIN,
    username: "user1",
    password: "bYYnBX7ITw"
}

const topicName = "test-topic";

const producer = new Writer({
    brokers: [bootstrap],
    topic: topicName,
    sasl: saslConfig,
    tls: {
        enableTls: false
    }
});
const options = {
    thresholds: {
        kafka_writer_error_count: ["count == 0"],
        kafka_reader_error_count: ["count == 0"]
    }
}

function getRandomValue(arr){
    return arr[Math.floor(Math.random() * arr.length)];
}

const schemaRegistry = new SchemaRegistry();

export default function(){
    for(let i =0; i < 10; i +=1){
        let messages = []
        sleep(1);
        for(let j = 0; j < 10; j+=1){
            messages.push({
                value: schemaRegistry.serialize({
                    data: {
                        "user_id": getRandomValue([1,2,3,4,5,6,7,8,9,10]),
                        "item_id": getRandomValue([100,200,300,400,500,600]),
                        "behavior": getRandomValue(["PUSH", "CLICK", "SHOW", "VISIT"])
                    },
                    schemaType: SCHEMA_TYPE_JSON
                })
            })
        }
        producer.produce({messages: messages})
    }
}

export function teardown(){
    producer.close();
}