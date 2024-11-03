package ru.tigran.player.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import ru.tigran.player.service.VideoPlayerService;

@RestController
@RequestMapping("/api/videos")
public class VideoPlayerController {

    private final VideoPlayerService videoPlayerService;

    public VideoPlayerController(VideoPlayerService videoPlayerService) {
        this.videoPlayerService = videoPlayerService;
    }

    // Стриминг видео с поддержкой Range-заголовков
    @GetMapping("/{id}/stream")
    public ResponseEntity<Resource> streamVideo(@PathVariable Long id, @RequestHeader HttpHeaders headers) {
        return videoPlayerService.streamVideo(id, headers);
    }
}
