# Дипломный проект по профессии «Тестировщик»

Дипломный проект — автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

Приложение - веб-сервис, предоставляющий возможность, купить тур по дебетовой карте или посредством кредита по данным карты.

### В процессе работы над проектом созданы следующие документы

[План автоматизации тестирования](https://github.com/Valmar91/DiplomQA/blob/main/Plan.md)

[Отчёт опроведённом тестировании](https://github.com/Valmar91/DiplomQA/blob/main/Report.md)

[Отчёт опроведённой автоматизации тестирования]()

## Подготовка и запуск тестов

### Предворительные требования

Должны быть установлены и работать следующие программы:
- Git Bush
- Docker
- Intellij IDEA

### Запуск

1) Клонировать проект командой `git clone https://github.com/Valmar91/DiplomQA.git`
2) Открыть клонированный проект программой Intellij IDEA
3) Запустить контейнеры командой `docker-compose up --build -d`
4) Сделать `application.properties` из 
`application.mysql.properties` или `application.postgresql.properties` в зависимости от базы данных, предполагаемой для использования
5) В `build.gradle` снять сокрытие с `systemProperty 'db.url',  System.getProperty('db.url', "jdbc:mysql://localhost:3306/app")` или `systemProperty 'db.url',  System.getProperty('db.url', "jdbc:postgresql://localhost:5432/db")` в зависимости от базы данных, предполагаемой для использования, вторую оставив сокрытой
6) Запустить aqa-shop.jar командой `java -jar aqa-shop.jar`
7) Запустить тесты командой `./gradlew clean test`, либо непосредствено нажав `Run Test` в классе `PurchaseTest`
8) Для просмотра отчёта Allure ввести в терминале команду `./gradlew allureServe`
