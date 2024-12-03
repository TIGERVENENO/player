package ru.tigran.player.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.tigran.player.service.VideoService;
import ru.tigran.player.service.dto.VideoDto;

@RestController
@RequestMapping("/api/drive/videos")
public class GoogleDriveController {

    private final VideoService videoService;

    public GoogleDriveController(VideoService videoService) {
        this.videoService = videoService;
    }

    // Получение всех видео с пагинацией и сортировкой
    @GetMapping
    @PreAuthorize("hasRole('USER')") // Доступно только для пользователей и администраторов
    public ResponseEntity<Page<VideoDto>> getAllVideos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        return ResponseEntity.ok(videoService.getAllVideos(page, size, sortBy));
    }

    // Получение видео по ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')") // Доступно только для пользователей и администраторов
    public ResponseEntity<VideoDto> getVideoById(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.getVideoById(id));
    }
}
