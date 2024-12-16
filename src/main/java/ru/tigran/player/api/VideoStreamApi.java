package ru.tigran.player.api;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * API для стриминга видео.
 */
@RequestMapping("/api/stream")
public interface VideoStreamApi {

    /**
     * Эндпоинт для получения файлов потока.
     *
     * @param videoId   ID видео.
     * @param filename  Имя файла.
     * @return Ресурс (файл) или ошибка.
     */
    @GetMapping("/{videoId}/{filename}")
    ResponseEntity<Resource> serveFile(
            @PathVariable String videoId,
            @PathVariable String filename);
}
