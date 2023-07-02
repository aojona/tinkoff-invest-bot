# Tinkoff invest bot

Телеграм бот для взаимодействия с торговой платформой Тинькофф Инвестиции

## Демонстрация

https://github.com/aojona/tinkoff-invest-bot/assets/112020091/80a61bc4-eae8-4c99-b66f-a088621017f0

## Стек используемых технологий

* [Spring](https://spring.io/) — основной фреймворк для разработки на Java
* [Gradle](https://gradle.org) — инструмент для автоматизации сборки проекта
* [Lombok](https://projectlombok.org/) — библиотека для генерации boilerplate кода
* [PostgreSQL](https://postgresql.org) — реляционная база данных
* [Telegram Bots](https://core.telegram.org/bots/api) — инструмент для создания бота
* [Tinkoff Invest API](https://tinkoff.github.io/investAPI/) — API Тинькофф Инвестиции
* [StreamEx](https://github.com/amaembo/streamex) — расширение к Stream API

## Начало работы
1. Настроить подключение к [PostgreSQL](https://postgresql.org) или запустить локально
```shell
docker run --name hotel-database -e POSTGRES_PASSWORD=pass -p 5432:5432 -d postgres:15.3-alpine
```
2. Зарегестрировать бота через [@BotFather](https://t.me/BotFather) и получить его токен {BOT_TOKEN}
3. Установить и запустить [ngrok](https://ngrok.com) для переадресации сообщений с защищенного адреса {WEB_HOOK_PATH}
```shell
ngrok http 8080
```
4. Сообщить телеграм боту о нашем адресе 
```shell
https://api.telegram.org/bot{BOT_TOKEN}/setWebhook?url={WEB_HOOK_PATH}
```
В случае успеха придет сообщение:
```json
{
  "ok": true,
  "result": true,
  "description": "Webhook was set"
}
```
5. Для работы с [Тинкьофф Инвестиции](https://www.tinkoff.ru/invest/) выпустить токен в личном кабинете 
6. Настроить конфигурацию приложения:
- `telegram.bot.name` — имя бота
- `telegram.bot.webHookPath` — URL адрес вебхука
- `telegram.bot.token` — токен бота 
- `tinkoff.token` — токен для работы с Tinkoff Invest API
- `tinkoff.sandbox` — режим песочницы
- `schedule.fixed-rate.update-currencies` – частота обновления цены валют
- `schedule.fixed-rate.send-prices` – частота уведомлений
7. Скомпилировать и запустить приложение
```shell
./gradlew bootRun
```
Либо скомилировать и запустить приложение с переменными окружения, чтобы не указывать их в конфигурации
```shell
BOT_NAME=? WEB_HOOK_PATH=? BOT_TOKEN=? TINKOFF_TOKEN=? SANDBOX=? ./gradlew bootRun
```
