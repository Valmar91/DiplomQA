# Дипломный проект по профессии «Тестировщик»

Дипломный проект — автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

Приложение - веб-сервис, предоставляющий возможность, купить тур по дебетовой карте или посредством кредита по данным карты.

### В процессе работы над проектом созданы следующие документы

[План автоматизации тестирования](https://github.com/Valmar91/DiplomQA/blob/main/Plan.md)

[Отчёт опроведённом тестировании](https://github.com/Valmar91/DiplomQA/blob/main/Report.md)

[Отчёт опроведённой автоматизации тестирования](https://github.com/Valmar91/DiplomQA/blob/main/Summary.md)

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
4) Запустить aqa-shop.jar командой `java -D:spring.profiles.active=mysql -jar aqa-shop.jar`, для работы с базой данных MySQL, либо `java -D:spring.profiles.active=postgresql -jar aqa-shop.jar`, для рботы с базой данных PostgreSQL
5) Запустить тесты командой `./gradlew clean test -Ddburl=jdbc:mysql://localhost:3306/app `, если работа идйт с БД MySQL, либо `./gradlew clean test -Ddburl=jdbc:postgresql://localhost:5432/db`, если работа идет с БД PostgreSQL
6) Для просмотра отчёта Allure ввести в терминале команду `./gradlew allureServe`
