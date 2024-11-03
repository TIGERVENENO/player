package ru.tigran.player.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class VideoPlayerService {

    private final ResourceLoader resourceLoader;
    private final String videoStoragePath = "C:/videos"; // Путь к папке с видео

    public VideoPlayerService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    // Стриминг видео с поддержкой Range-запросов
    public ResponseEntity<Resource> streamVideo(Long id, HttpHeaders headers) {
        // Получаем путь к видеофайлу (здесь предполагаем, что у видео есть имя в базе данных, соответствующее имени файла)
        String videoFileName = getVideoFileNameById(id);
        Path videoPath = Paths.get(videoStoragePath).resolve(videoFileName);

        // Загружаем видео как ресурс
        Resource videoResource = resourceLoader.getResource("file:" + videoPath.toString());

        // Обрабатываем запросы с заголовком Range (если пользователь делает перемотку)
        if (headers.containsKey(HttpHeaders.RANGE)) {
            HttpRange range = headers.getRange().get(0);
            // Вы можете использовать библиотеку, которая поддерживает частичную загрузку
            // или самим вычислять байтовые диапазоны для прогрессивного стриминга
            return ResponseEntity
                    .status(206)
                    .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                    .header(HttpHeaders.CONTENT_RANGE, range.toString())
                    .body(videoResource);
        }

        // Возвращаем полный контент, если Range не указан
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                .body(videoResource);
    }

    // Метод для получения имени файла по ID видео
    private String getVideoFileNameById(Long id) {
        // Для упрощения, здесь используется фиксированное имя видеофайла
        // В реальном приложении имя файла можно хранить в базе данных
        return "example_video.mp4"; // Подставить реальное имя видеофайла
    }
}
