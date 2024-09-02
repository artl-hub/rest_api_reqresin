# Проект по автоматизации тестирования API для сайта <a href="https://reqres.in/">reqres.in</a>

<p align="center">
<a href="https://reqres.in/"><img title="Логотип сайта Reqres" src="media/logos/reqres.jpg" align="center"></a>
</p>

## :scroll: Содержание

- [Используемый стек](#computer-используемый-стек)
- [Покрытый функционал](#male_detective-покрытый-функционал)
- [Локальный запуск тестов](#arrow_forward-локальный-запуск-тестов)
- [Сборка в Jenkins](#-сборка-в-jenkins)
- [Пример Allure-отчёта](#-пример-allure-отчёта)
- [Интеграция с Allure TestOps](#-интеграция-с-allure-testOps)
- [Интеграция с Jira](#-интеграция-с-jira)
- [Уведомления в Telegram с использованием бота](#-уведомления-в-telegram-с-использованием-бота)

## :computer: Используемый стек

<p align="center">
<a href="https://www.jetbrains.com/idea/"><img width="6%" title="IntelliJ IDEA" src="media/logos/IntelijIDEA.svg"></a> 
<a href="https://www.java.com/"><img width="6%" title="Java" src="media/logos/Java.svg"></a> 
<a href="https://gradle.org/"><img width="6%" title="Gradle" src="media/logos/Gradle.svg"></a>
<a href="https://rest-assured.io/"><img width="6%" title="REST Assured" src="media/logos/RestAssured.svg"></a> 
<a href="https://github.com/allure-framework/allure2"><img width="6%" title="Allure Report" src="media/logos/AllureReport.svg"></a> 
<a href="https://junit.org/junit5/"><img width="6%" title="JUnit5" src="media/logos/JUnit5.svg"></a> 
<a href="https://assertj.github.io/doc/"><img width="6%" title="AssertJ" src="media/logos/AssertJ.png"></a> 
<a href="https://github.com/"><img width="6%" title="GitHub" src="media/logos/GitHub.svg"></a> 
<a href="https://www.jenkins.io/"><img width="6%" title="Jenkins" src="media/logos/Jenkins.svg"></a> 
<a href="https://telegram.org/"><img width="6%" title="Telegram" src="media/logos/Telegram.svg"></a> 
<a href="https://qameta.io/"><img width="6%" title="Allure TestOps" src="media/logos/AllureTestOps.svg"></a> 
<a href="https://www.atlassian.com/ru/software/jira/"><img width="6%" title="Jira" src="media/logos/Jira.svg"></a> 
</p>

Тесты в проекте написаны на языке <code>Java</code> с использованием [REST Assured](https://rest-assured.io/) и сборщика <code>Gradle</code>. <code>JUnit 5</code> задействован
в качестве фреймворка модульного тестирования.
Для удаленного запуска реализована сборка в <code>Jenkins</code> с формированием Allure-отчёта и отправкой результатов
в <code>Telegram</code> при помощи бота. Также реализована интеграция с <code>Allure TestOps</code> и <code>Jira</code>.

## :male_detective: Покрытый функционал

Reqres – это сайт, предоставляющий публичный доступ к тестовому API, который можно использовать в процессе разработки собственных приложений или как тренажёр по работе с REST API.
<br>Тесты в проекте покрывают следующие фукнции тестового API:
* Получение списка существующих пользователей и данных о них
* Получение списка занесённых в API цветов с описанием их кодов в системе Pantone и шестнадцатитеричном формате
* Получение ответа со статусом 404 при запросе несуществующего пользователя/цвета
* Создание пользователя
* Регистрация пользователя
* Редактирование данных пользователя
* Удаление пользователя (так как тренажёр не изменяет при этом список пользователей, проверяется лишь возвращаемый код 204)

## :arrow_forward: Локальный запуск тестов

```
gradle clean test
```

## <img width="4%" style="vertical-align:middle" title="Jenkins" src="media/logos/Jenkins.svg"> [Сборка](https://jenkins.autotests.cloud/view/C24/job/C24-MolokoVelosiped-reqres-api-tests/) в Jenkins

Для запуска сборки необходимо нажать кнопку <code>Build Now</code>.
<p align="center">
<img title="Сборка в Jenkins" src="media/screencaps/Jenkins.png">
</p>
После выполнения сборки, в блоке <code>Build History</code> напротив номера сборки появятся значки <code>Allure Report</code> и <code>Allure TestOps</code>, при клике на которые откроется страница с сформированным html-отчетом и тестовой документацией соответственно.

## <img width="4%" style="vertical-align:middle" title="Allure Report" src="media/logos/AllureReport.svg"> [Пример](https://jenkins.autotests.cloud/view/C24/job/C24-MolokoVelosiped-reqres-api-tests/3/allure/) Allure-отчёта

<p align="center">
<img title="Общий вид отчёта Allure" src="media/screencaps/AllureOverview.png">
</p>

### Содержание Allure-отчёта

* Тест-кейсы, разделённые на папки по сьютам или фичерам
* Описание шагов тестов
* Запросы, отправленные на API
* Полученные ответы на запросы

### Раздел Suites

<p align="center">
<img title="Раздел Suites отчёта Allure" src="media/screencaps/AllureSuites.png">
</p>

### Раздел Behaviors

<p align="center">
<img title="Раздел Behaviors отчёта Allure" src="media/screencaps/AllureBehaviors.png">
</p>

## <img width="4%" style="vertical-align:middle" title="Allure TestOps" src="media/logos/AllureTestOps.svg"> [Интеграция](https://allure.autotests.cloud/project/4171/dashboards) с Allure TestOps

На *Dashboard* в <code>Allure TestOps</code> видна статистика по тестам, которые приходят по интеграции при каждом запуске сборки вместе с результатами их прохождения.

<p align="center">
<img title="Дэшборд Allure TestOps" src="media/screencaps/TestOpsDashboard.png">
</p>

### Тест-кейс, сгенерированный из автотеста

<p align="center">
<img title="Результаты теста в Allure TestOps №1" src="media/screencaps/TestOpsResults1.png">
</p>

### История выполнения автотеста

<p align="center">
<img title="Результаты теста в Allure TestOps №2" src="media/screencaps/TestOpsResults2.png">
</p>

## <img width="4%" style="vertical-align:middle" title="Jira" src="media/logos/Jira.svg"> [Интеграция](https://jira.autotests.cloud/browse/HOMEWORK-1187) с Jira

Реализована интеграция <code>Allure TestOps</code> с <code>Jira</code>, в тикете отображаются привязанные тест-кейсы и результаты их запусков.

<p align="center">
<img title="Тикет в Jira" src="media/screencaps/Jira.png">
</p>

## <img width="4%" style="vertical-align:middle" title="Telegram" src="media/logos/Telegram.svg"> Уведомления в Telegram с использованием бота

После завершения сборки специальный бот, созданный в <code>Telegram</code>, получает из Jenkins и отправляет сообщение с
отчётом о прогоне тестов.

<p align="center">
<img width="50%" title="Уведомление в Telegram" src="media/screencaps/Bot.png">
</p>
