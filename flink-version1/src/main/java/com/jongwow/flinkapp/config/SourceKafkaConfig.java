package com.jongwow.flinkapp.config;

import java.util.ArrayList;
import java.util.List;

public class SourceKafkaConfig {
    private String connector = "kafka";

    // SASL_PLAINTEXT

    private String securityProtocol; // Must
    // PLAIN, SASL_SCRAM_256, SASL_SCRAM_512
    private String saslMechanism; // Must

    // SASL_JAAS_CONFIG:  'org.apache.kafka.common.security.plain.PlainLoginModule required username="<USERNAME>" password="<PASSWORD>";'
    private String saslJaasConfig; // Must
    private String bootStrapServers; // Must
    private String topicName; // Must

    private String groupId; // Must


    // group-offsets, latest-offset, earliest-offset
    private String scanStartupMode = "earliest-offset";

    private String valueFormat = "json";
    private String valueJsonIgnoreParseError = "true";
    private List<SourceColumn> columns = new ArrayList<>();

    public void setConnector(String connector) {
        this.connector = connector;
    }


    public String getConnector() {
        return connector;
    }


    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public void setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
    }

    public String getSaslMechanism() {
        return saslMechanism;
    }

    public void setSaslMechanism(String saslMechanism) {
        this.saslMechanism = saslMechanism;
    }

    public String getSaslJaasConfig() {
        return saslJaasConfig;
    }

    public void setSaslJaasConfig(String saslJaasConfig) {
        this.saslJaasConfig = saslJaasConfig;
    }

    public String getBootStrapServers() {
        return bootStrapServers;
    }

    public void setBootStrapServers(String bootStrapServers) {
        this.bootStrapServers = bootStrapServers;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getScanStartupMode() {
        return scanStartupMode;
    }

    public void setScanStartupMode(String scanStartupMode) {
        this.scanStartupMode = scanStartupMode;
    }

    public String getValueFormat() {
        return valueFormat;
    }

    public void setValueFormat(String valueFormat) {
        this.valueFormat = valueFormat;
    }

    public String getValueJsonIgnoreParseError() {
        return valueJsonIgnoreParseError;
    }

    public void setValueJsonIgnoreParseError(String valueJsonIgnoreParseError) {
        this.valueJsonIgnoreParseError = valueJsonIgnoreParseError;
    }

    public List<SourceColumn> getColumns() {
        return columns;
    }

    public void addColumn(String name, String type, String expression){
        columns.add(new SourceColumn(name, type, expression));
    }

}
