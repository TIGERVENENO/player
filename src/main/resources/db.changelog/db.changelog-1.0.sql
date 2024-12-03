-- Создание таблицы users
CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    role     VARCHAR(255)        NOT NULL
);

-- Создание таблицы categories
CREATE TABLE IF NOT EXISTS categories
(
    id            SERIAL PRIMARY KEY,
    title         VARCHAR(50) UNIQUE NOT NULL,
    thumbnail_url VARCHAR(255) NOT NULL,
    name          VARCHAR(255) NOT NULL
        CONSTRAINT ukt8o6pivur7nn124jehx7cygw5 UNIQUE
);

-- Создание таблицы heroes
CREATE TABLE IF NOT EXISTS videos
(
    id            SERIAL PRIMARY KEY,
    description   VARCHAR(255) NOT NULL,
    file_id       VARCHAR(255) NOT NULL,
    thumbnail_url VARCHAR(255),
    title         VARCHAR(255) NOT NULL,
    category_id   INTEGER NOT NULL REFERENCES categories (id)
);

-- Создание таблицы videos
CREATE TABLE IF NOT EXISTS heroes
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    category_id INTEGER REFERENCES categories (id),
    description TEXT
);

-- Создание таблицы replays
CREATE TABLE IF NOT EXISTS replays
(
    id            SERIAL PRIMARY KEY,
    hero_id       INTEGER REFERENCES heroes (id),
    file_path     VARCHAR(255) NOT NULL,
    thumbnail_url VARCHAR(255) NOT NULL,
    upload_date   DATE
);
