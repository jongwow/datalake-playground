package com.jongwow.flinkapp.config;

import java.util.Properties;

public class SinkIcebergConfig {
    private String catalogName;
    private String catalogDatabase;
    private String catalogTable;
    private String warehouse;
    private String schema;
    private String catalogType;
    private String uri;

    public String getSchema() {
        return schema;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public String getCatalogDatabase() {
        return catalogDatabase;
    }

    public String getCatalogTable() {
        return catalogTable;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public void setCatalogDatabase(String catalogDatabase) {
        this.catalogDatabase = catalogDatabase;
    }

    public void setCatalogTable(String catalogTable) {
        this.catalogTable = catalogTable;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }


    public void setSchema(String schema) {
        this.schema = schema;
    }

    public void setCatalogType(String catalogType) {
        this.catalogType = catalogType;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCatalogType() {
        return this.catalogType;
    }

    public String getUri() {
        return this.uri;
    }
}
