package com.jongwow.flinkapp.sinkconnector;


import com.jongwow.flinkapp.config.SinkIcebergConfig;
import org.apache.flink.api.java.utils.ParameterTool;

import java.util.Objects;
import java.util.Properties;

public class SinkTableQueryBuilder {
    //    private static final Logger logger = LoggerFactory.getLogger(SinkTableQueryBuilder.class);
    private static final String DEFAULT_ICEBERG_GLUE_CATALOG_IMPL = "org.apache.iceberg.aws.glue.GlueCatalog";
    //                formatString("catalog-impl", DEFAULT_ICEBERG_GLUE_CATALOG_IMPL) +
    private static final String DEFAULT_ICEBERG_TO_IMPL = "org.apache.iceberg.aws.s3.S3FileIO";

    public static String getCreateSinkTable(SinkIcebergConfig config) {
        return "CREATE TABLE SinkTable ( " +
                config.getSchema() +
                " ) WITH ( " +
                formatString("connector", "iceberg") +
                formatString("hive-conf-dir", "/etc/hive") +
                formatString("catalog-name", config.getCatalogName()) +
                formatString("catalog-database", config.getCatalogDatabase()) +
                formatString("catalog-table", config.getCatalogTable()) +
                formatString("catalog-type", config.getCatalogType()) +
                formatString("uri", config.getUri()) +
                formatString("io-impl", DEFAULT_ICEBERG_TO_IMPL) +
                formatString("warehouse", config.getWarehouse(), false)
                + " )";

    }

    public static SinkIcebergConfig getSinkConfig(ParameterTool params) {
        SinkIcebergConfig sinkIcebergConfig = new SinkIcebergConfig();
        String SINK = "sink.";
        Properties properties = params.getProperties();

        String connector = properties.getProperty(SINK + "connector");
        if (!Objects.equals(connector, "iceberg")) {
            return null;
        }
        String catalogName = properties.getProperty(SINK + "catalog-name");
        String catalogDatabase = properties.getProperty(SINK + "catalog-database");
        String catalogTable = properties.getProperty(SINK + "catalog-table");
        String warehouse = properties.getProperty(SINK + "warehouse");
        String catalogType = properties.getProperty(SINK + "catalog-type");
        String uri = properties.getProperty(SINK + "uri");
        String schema = properties.getProperty(SINK + "schema");
        sinkIcebergConfig.setCatalogDatabase(catalogDatabase);
        sinkIcebergConfig.setCatalogTable(catalogTable);
        sinkIcebergConfig.setCatalogName(catalogName);
        sinkIcebergConfig.setWarehouse(warehouse);
        sinkIcebergConfig.setSchema(schema);
        sinkIcebergConfig.setUri(uri);
        sinkIcebergConfig.setCatalogType(catalogType);

        return sinkIcebergConfig;
    }

    public static String formatString(String key, String value, boolean delimiter) {
        if (delimiter) {
            return String.format(" '%s' = '%s' ,", key, value);
        } else {
            return String.format(" '%s' = '%s' ", key, value);
        }
    }

    public static String formatString(String key, String value) {
        return formatString(key, value, true);
    }
}
