# Принципы создания Базы данных
Перед тем, как делать базу данных надо выделить следующе:

 Понятие | Описание 
 ------  | ----- 
Цель приложения | Всегда надо помнить - что и зачем мы делаем. Если мы описываем пользователя, то должны расписать его параметры (паспорт, дату рождения, связи с родственниками и т.д.). Если пользователь нам нужен только для авторизации, то достаточно определить login и password...
Основные сущности | (Entity) составляют предметную область.
Связи между сущностями | Один ко многим, Многие ко многим, один к одному и т.д.
Ограничения | (Constraint) Ограничения - это то, что делает набор таблиц базой данных. Кроме самих констрейнтов к ограничения относятся первичный и вторичный ключ, ограничения полей и т.д. 

## Таблица
Таблицы делятся на следующие виды: 

 Понятие | Описание 
 ------  | ----- 
 основная сущность | (Entity) составляют предметную область.
 таблица связей | В основном, такие таблицы применяют для создания Связи <**Многие ко многим**> 
 справочные данные | Здесь хранятся возможные значения типов данных. По этим данным создаются выпадающие списки...

### Префикс таблицы и полей таблицы
Имя таблицы длжно определять ее суть (*Как вы судно назовете, так оно и поплывет*). Кроме этого, название не должно быть длинным.

В люксе принято добавлять в начало таблицы префикс, который определяет предметную облясть.

Напимер:
```postgresql
CREATE TABLE UM_USER (
  UMUSR_USER_ID SERIAL,
  UMUSR_LOGIN TEXT, 
  UMUSR_NAME TEXT, 
  UMUSR_PASSWORD TEXT 
);
```
Здесь UM_ - префикс, определяющий область "User Management".

Префикс  UMUSR_  определяет принадлежность поля к таблице UM_USER.
 Если другая таблица ссылается на UM_USER, то поля связи должны полностью совпадать.   

Напимер:
```postgresql
CREATE TABLE UM_USER_ROLE (
  UMURL_USER_ROLE_ID SERIAL,
  UMUSR_USER_ID INTEGER UNIQUE,
  UMRLE_ROLE_ID INTEGER UNIQUE
);
```
Это таблица связи, которая связывает каждого пользователя (UM_USER) со многими его ролями (UM_ROLE).     

### Первичный ключь ID
Каждая таблица должна иметь primary key (PK, ID).

### Время жизни записи
2. 
