Порядок запуска тестов

1) Запустить контейнеры командой `docker-compose up --build -d`
2) Сделать `application.properties` из 
`application.mysql.properties` или `application.postgresql.properties` в зависимости от базы данных, предполагаемой для использования
3) В `build.gradle` снять сокрытие с `systemProperty 'db.url',  System.getProperty('db.url', "jdbc:mysql://localhost:3306/app")` или `systemProperty 'db.url',  System.getProperty('db.url', "jdbc:postgresql://localhost:5432/db")` в зависимости от базы данных, предполагаемой для использования, вторую оставив сокрытой
4) Запустить aqa-shop.jar командой `java -jar aqa-shop.jar`
5) Запустить тесты командой `./gradlew clean test`, либо непосредствено нажав `Run Test` в классе `PurchaseTest`
```powershell
docker-compose up --build -d

```