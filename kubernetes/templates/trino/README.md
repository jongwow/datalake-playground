
```sql
```

```
./trino localhost:8080
```


```sql
SELECT current_user;
```

```sql
SELECT current_catalog; -- NULL
```

```sql
USE iceberg.raw_data;
```

```sql
SELECT current_catalog;
```

```sql
CREATE TABLE iceberg.raw_data.user_activity1 (
        user_id bigint,
        item_id bigint,
        behavior VARCHAR,
        reg_ts timestamp(6)
     )
     WITH (
        format = 'PARQUET',
        format_version = 2,
        location = 's3a://datalake/raw/data/user_activity1',
        partitioning = ARRAY['day(reg_ts)']
     );
```


