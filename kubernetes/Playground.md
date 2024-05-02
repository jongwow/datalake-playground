설정에서 파일 쓰기 크기 줄이기

```sql
SHOW SESSION LIKE '%iceberg%'; 
...
 iceberg.target_max_file_size                 | 1GB   | 1GB      | varchar | Target maximum size of written files; the actual size may be larger
...

SET SESSION iceberg.target_max_file_size = '0.5MB';

```