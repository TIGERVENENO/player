CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,            -- Уникальный идентификатор пользователя
    password varchar(255) not null,         -- Пароль пользователя в зашифрованном виде
    role     varchar(255) not null,         -- Роль пользователя (ROLE_USER или ROLE_ADMIN)
    username varchar(255) not null unique   -- Имя пользователя
);

-- Создание таблицы category
CREATE TABLE IF NOT EXISTS category
(
    id        SERIAL PRIMARY KEY,   -- Уникальный идентификатор категории
    name      VARCHAR(50) NOT NULL, -- Название категории
    image_url TEXT                  -- URL изображения категории
);

-- Создание таблицы animal
CREATE TABLE IF NOT EXISTS animal
(
    id          SERIAL PRIMARY KEY,   -- Уникальный идентификатор животного
    name        VARCHAR(50) NOT NULL, -- Имя животного
    image_url   TEXT,                 -- URL изображения животного
    category_id INTEGER     NOT NULL, -- Идентификатор категории
    FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE
);

-- Создание таблицы video
CREATE TABLE IF NOT EXISTS video
(
    id        SERIAL PRIMARY KEY,   -- Уникальный идентификатор видео
    name      VARCHAR(50) NOT NULL, -- Название видео
    image_url TEXT,                 -- URL изображения видео
    animal_id   INTEGER     NOT NULL, -- Идентификатор животного
    FOREIGN KEY (animal_id) REFERENCES animal (id) ON DELETE CASCADE
);


CREATE TABLE SPRING_SESSION (
    PRIMARY_ID CHAR(36) NOT NULL,
    SESSION_ID CHAR(36) NOT NULL,
    CREATION_TIME BIGINT NOT NULL,
    LAST_ACCESS_TIME BIGINT NOT NULL,
    MAX_INACTIVE_INTERVAL INT NOT NULL,
    EXPIRY_TIME BIGINT NOT NULL,
    PRINCIPAL_NAME VARCHAR(100),
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);
