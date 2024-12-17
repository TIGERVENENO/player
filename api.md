# Документация API

## Общая информация

- **Базовый URL**: `</api>`
- **Формат данных**: JSON
- **Аутентификация**: Для всех запросов, кроме регистрации и авторизации, необходимо использовать заголовок `Authorization` с токеном вида:

```
Authorization: Basic Y 
```

---

## Эндпоинты

### 1. Регистрация пользователя

- **URL**: `/register`
- **Метод**: `POST`
- **Описание**: Регистрирует нового пользователя и возвращает данные с токеном для авторизации.
- **Пример запроса**:

```json
{
  "username": "admin",
  "password": "admin",
  "role": "ROLE_ADMIN"
}
```

- **Ответ (успех)**:

Код: `200 OK`

```json
{
  "username": "admin",
  "token": "Basic YWRtaW46YWRtaW4=",
  "role": "ROLE_ADMIN"
}
```

### 2. Авторизация пользователя

- **URL**: `/login`
- **Метод**: `POST`
- **Описание**: Аутентифицирует пользователя и возвращает токен.
- **Пример запроса**:

```json
{
  "username": "admin",
  "password": "admin"
}
```

- **Ответ (успех)**:

Код: `200 OK`

```json
{
  "username": "admin",
  "token": "Basic YWRtaW46YWRtaW4=",
  "role": "ROLE_ADMIN"
}
```



---

### 3. Получение всех категорий

- **URL**: `/category`
- **Метод**: `GET`
- **Описание**: Возвращает список всех категорий.
- **Требуется аутентификация**: Да
- **Ответ (успех)**:

Код: `200 OK`

```json
[
  {
    "id": 1,
    "name": "Strength",
    "imageUrl": "https://example.com/strength.jpg"
  },
  {
    "id": 2,
    "name": "Agility",
    "imageUrl": "https://example.com/agility.jpg"
  },
  {
    "id": 3,
    "name": "Intelligence",
    "imageUrl": "https://example.com/intelligence.jpg"
  }
]
```

### 4. Получение героя по id

- **URL**: `/hero/{id}`
- **Метод**: `GET`
- **Описание**: Возвращает данные по герою, на основании его id.
- **Требуется аутентификация**: Да
- **Параметры запроса**:

| Параметр | Тип     | Обязательный | Описание |
|----------|---------| ------------ |----------|
| `id`     | integer | Да           | id героя |

- **Пример запроса**:

```
GET api/hero/3
```

- **Ответ (успех)**:

Код: `200 OK`

```json
{
  "id": 3,
  "name": "Pudge",
  "imageUrl": "https://example.com/pudge.jpg",
  "categoryId": 1
}
```


---

### 5. Получение героев по категории

- **URL**: `/hero`
- **Метод**: `GET`
- **Описание**: Возвращает список героев, относящихся к указанной категории.
- **Требуется аутентификация**: Да
- **Параметры запроса**:

| Параметр   | Тип     | Обязательный | Описание     |
| ---------- |---------| ------------ |--------------|
| `category` | integer | Да           | id категории |

- **Пример запроса**:

```
GET /hero?category=1
```

- **Ответ (успех)**:

Код: `200 OK`

```json
[
  {
    "id": 1,
    "name": "Axe",
    "imageUrl": "https://example.com/axe.jpg",
    "categoryId": 1
  },
  {
    "id": 2,
    "name": "Earthshaker",
    "imageUrl": "https://example.com/earthshaker.jpg",
    "categoryId": 1
  },
  {
    "id": 3,
    "name": "Pudge",
    "imageUrl": "https://example.com/pudge.jpg",
    "categoryId": 1
  }
]
```

---

### 6. Получение всех видео

- **URL**: `/video`

- **Метод**: `GET`

- **Описание**: Возвращает список всех видео.

- **Требуется аутентификация**: Да

- **Ответ (успех)**:

Код: `200 OK`

```json
[
  {
    "id": 1,
    "name": "Axe Gameplay Guide",
    "imageUrl": "https://example.com/axe_video.jpg",
    "heroId": 1
  },
  {
    "id": 2,
    "name": "Earthshaker Pro Moves",
    "imageUrl": "https://example.com/earthshaker_video.jpg",
    "heroId": 2
  },
  {
    "id": 3,
    "name": "Pudge Hook Highlights",
    "imageUrl": "https://example.com/pudge_video.jpg",
    "heroId": 3
  },
  {
    "id": 4,
    "name": "Phantom Assassin Crit Masterclass",
    "imageUrl": "https://example.com/phantom_assassin_video.jpg",
    "heroId": 4
  },
  {
    "id": 5,
    "name": "Juggernaut Blade Fury Tips",
    "imageUrl": "https://example.com/juggernaut_video.jpg",
    "heroId": 5
  },
  {
    "id": 6,
    "name": "Sniper Positioning Strategies",
    "imageUrl": "https://example.com/sniper_video.jpg",
    "heroId": 6
  },
  {
    "id": 7,
    "name": "Crystal Maiden Ultimate Plays",
    "imageUrl": "https://example.com/crystal_maiden_video.jpg",
    "heroId": 7
  },
  {
    "id": 8,
    "name": "Invoker 4-Spell Combos",
    "imageUrl": "https://example.com/invoker_video.jpg",
    "heroId": 8
  },
  {
    "id": 9,
    "name": "Zeus Damage Build",
    "imageUrl": "https://example.com/zeus_video.jpg",
    "heroId": 9
  }
]
```

---

### 7. Получение видео по герою

- **URL**: `/video`
- **Метод**: `GET`
- **Описание**: Возвращает список видео, относящихся к указанному герою.
- **Требуется аутентификация**: Да
- **Параметры запроса**:

| Параметр | Тип    | Обязательный | Описание  |
| -------- | ------ | ------------ | --------- |
| `hero`   | string | Да           | Имя героя |

- **Пример запроса**:

```
GET /video?hero=Pudge
```

- **Ответ (успех)**:

Код: `200 OK`

```json
[
  {
    "id": 3,
    "name": "Pudge Hook Highlights",
    "imageUrl": "https://example.com/pudge_video.jpg",
    "heroId": 3
  }
]
```

---

### 8. Удаление видео (только админ)

- **URL**: `/video/{id}`

- **Метод**: `DELETE`

- **Описание**: Удаляет видео по указанному идентификатору.

- **Требуется аутентификация**: Да

- **Права доступа**: Только администратор

- **Ответ (успех)**:

Код: `200 OK`

```
"Video deleted successfully."
```

---

### 9. Получение видео по ID

- **URL**: `/video/{id}`

- **Метод**: `GET`

- **Описание**: Возвращает информацию о видео по его идентификатору.

- **Требуется аутентификация**: Да

- **Ответ (успех)**:

Код: `200 OK`

```json
{
  "id": 1,
  "name": "Axe Gameplay Guide",
  "imageUrl": "https://example.com/axe_video.jpg",
  "heroId": 1
}
```



---

### 10. Получение HLS потока видео

- **URL**: `/video/stream/{videoId}`

- **Метод**: `GET`

- **Описание**: Возвращает ссылку на HLS-поток видео.

- **Требуется аутентификация**: Да

- **Ответ (успех)**:

Код: `200 OK`

```
/api/stream/2/output.m3u8
```

---

### 11. Получение файлов видео

- **URL**: `/video/{videoId}/{filename}`

- **Метод**: `GET`

- **Описание**: Возвращает hls-поток).

- **Ответ (успех)**:

Код: `200 OK`

- **Пример заголовка ответа**:

```
#EXTM3U
#EXT-X-VERSION:3
#EXT-X-TARGETDURATION:12
#EXT-X-MEDIA-SEQUENCE:0
#EXTINF:11.933333,
output0.ts
#EXTINF:7.800000,
output1.ts
#EXT-X-ENDLIST
```



---

## Примечания

- Все поля в JSON строго типизированы. Ошибки в формате приведут к коду `400 Bad Request`.



