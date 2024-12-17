CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    password varchar(255) not null,
    role     varchar(255) not null,
    username varchar(255) not null unique
);

-- Создание таблицы category
CREATE TABLE IF NOT EXISTS category
(
    id        SERIAL PRIMARY KEY,   -- Уникальный идентификатор категории
    name      VARCHAR(50) NOT NULL, -- Название категории
    image_url TEXT                  -- URL изображения категории
);

-- Создание таблицы heroes
CREATE TABLE IF NOT EXISTS heroes
(
    id          SERIAL PRIMARY KEY,   -- Уникальный идентификатор героя
    name        VARCHAR(50) NOT NULL, -- Имя героя
    image_url   TEXT,                 -- URL изображения героя
    category_id INTEGER     NOT NULL, -- Идентификатор категории
    FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE
);

-- Создание таблицы videos
CREATE TABLE IF NOT EXISTS videos
(
    id        SERIAL PRIMARY KEY,   -- Уникальный идентификатор видео
    name      VARCHAR(50) NOT NULL, -- Название видео
    image_url TEXT,                 -- URL изображения видео
    hero_id   INTEGER     NOT NULL, -- Идентификатор героя
    FOREIGN KEY (hero_id) REFERENCES heroes (id) ON DELETE CASCADE
);
