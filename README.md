# Tinkoff invest bot

Телеграм бот для взаимодействия с торговой платформой Тинькофф Инвестиции

## Стек используемых технологий

* [Spring](https://spring.io/) — основной фреймворк для разработки на Java
* [Gradle](https://gradle.org) — инструмент для автоматизации сборки проекта
* [Lombok](https://projectlombok.org/) — библиотека для генерации boilerplate кода
* [Telegram Bots](https://core.telegram.org/bots/api) — инструмент для создания бота
* [Tinkoff Invest API](https://tinkoff.github.io/investAPI/) — инструментарий для работы с API Тинькофф Инвестиции
* [StreamEx](https://github.com/amaembo/streamex) — расширение к Stream API

## Начало работы

1. Зарегестрировать нового бота через [@BotFather](https://t.me/BotFather) и получить Токен, с помощью которого
поисходит управление ботом
2. Установить и запустить [ngrok](https://ngrok.com) для переадресации сообщений с защищенного адреса
```shell
ngrok http 8080
```
3. Далее необходимо сообщить телеграм боту о нашем адресе 
```shell
https://api.telegram.org/bot{BOT_TOKEN}/setWebhook?url={WEB_HOOK_PATH}
```
В случае успеха в ответ придет сообщение:
```json
{
  "ok": true,
  "result": true,
  "description": "Webhook was set"
}
```
4. Для работы с [Тинкьофф Инвестиции](https://www.tinkoff.ru/invest/) необходимо выпустить токен в личном кабинете
5. Далее необходимо настроить конфигурацию приложения:
- `telegram.bot.name` — имя бота
- `telegram.bot.webHookPath` — URL адрес вебхука
- `telegram.bot.token` — токен бота
- `tinkoff.token` — токен для работы с Tinkoff Invest API
- `tinkoff.sandbox` — режим песочницы
6. Скомпилировать и запустить приложение
```shell
./gradlew bootRun
```
Либо скомилировать и запустить приложение с переменными окружения, чтобы не прописывать их в конфигурации
```shell
BOT_NAME=? WEB_HOOK_PATH=? BOT_TOKEN=? TINKOFF_TOKEN=? SANDBOX=? ./gradlew bootRun
```