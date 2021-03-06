docker exec --workdir /var/lib/mysql/entry deadline_mysql_1 bash recreate_schema.sh
java -jar app-deadline.jar -P:jdbc.url=jdbc:mysql://localhost:3306/app -P:jdbc.user=app -P:jdbc.password=pass