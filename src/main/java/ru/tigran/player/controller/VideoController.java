package ru.tigran.player.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.tigran.player.service.VideoService;
import ru.tigran.player.service.dto.VideoDto;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    // Получение списка всех видео с пагинацией и сортировкой
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Page<VideoDto>> getAllVideos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        return ResponseEntity.ok(videoService.getAllVideos(page, size, sortBy));
    }

    // Получение информации о конкретном видео
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<VideoDto> getVideoById(@PathVariable Long id) {
        VideoDto videoDto = videoService.getVideoById(id);
        if (videoDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(videoDto);
    }
}
