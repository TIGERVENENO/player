package ru.tigran.player.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.tigran.player.service.VideoService;
import ru.tigran.player.service.dto.VideoDto;

import java.io.IOException;

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

    @GetMapping("/category/{category}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Page<VideoDto>> getVideosByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        return ResponseEntity.ok(videoService.getVideosByCategory(category, page, size, sortBy));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteVideo(@PathVariable Long id) {
        try {
            videoService.deleteVideo(id);
            return ResponseEntity.ok("Video deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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

    // Эндпоинт для получения HLS потока
    @GetMapping("/hls/{videoId}")
    public ResponseEntity<?> getVideoHLS(@PathVariable String videoId) {
        try {
            // Генерация HLS потока для видео
            String hlsStreamUrl = videoService.generateHLSStream(videoId);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/vnd.apple.mpegurl");
            return new ResponseEntity<>(hlsStreamUrl, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating HLS stream");
        }
    }
}
