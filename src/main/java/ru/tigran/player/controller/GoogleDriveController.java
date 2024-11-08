package ru.tigran.player.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tigran.player.service.VideoService;
import ru.tigran.player.service.dto.VideoDto;

@RestController
@RequestMapping("/api/videos")
public class GoogleDriveController {

    private final VideoService videoService;

    public GoogleDriveController(VideoService videoService) {
        this.videoService = videoService;
    }

    // Получение всех видео с пагинацией и сортировкой
    @GetMapping
    public ResponseEntity<Page<VideoDto>> getAllVideos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        return ResponseEntity.ok(videoService.getAllVideos(page, size, sortBy));
    }

    // Получение видео по ID
    @GetMapping("/{id}")
    public ResponseEntity<VideoDto> getVideoById(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.getVideoById(id));
    }

    // Добавление нового видео
    @PostMapping
    public ResponseEntity<VideoDto> addVideo(@RequestBody VideoDto videoDto) {
        return ResponseEntity.ok(videoService.addVideo(videoDto));
    }

    // Обновление видео
    @PutMapping("/{id}")
    public ResponseEntity<VideoDto> updateVideo(@PathVariable Long id, @RequestBody VideoDto videoDto) {
        return ResponseEntity.ok(videoService.updateVideo(id, videoDto));
    }

    // Удаление видео
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }
}
