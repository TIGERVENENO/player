package ru.tigran.player.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.tigran.player.service.VideoPlayerService;

@RestController
@RequestMapping("/api/player")
public class VideoPlayerController {

    private final VideoPlayerService videoPlayerService;

    public VideoPlayerController(VideoPlayerService videoPlayerService) {
        this.videoPlayerService = videoPlayerService;
    }

    // Стриминг видео с поддержкой Range-заголовков
    @GetMapping("/{id}/stream")
    @PreAuthorize("hasRole('USER')") // Доступно для авторизованных пользователей и администраторов
    public ResponseEntity<Resource> streamVideo(@PathVariable Integer id, @RequestHeader HttpHeaders headers) {
        return videoPlayerService.streamVideo(id, headers);
    }
}
