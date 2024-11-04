package ru.tigran.player.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tigran.player.service.GoogleDriveService;
import ru.tigran.player.service.VideoService;
import ru.tigran.player.service.dto.VideoDto;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final GoogleDriveService googleDriveService;

    // Получение всех видео с метаданными, пагинацией и сортировкой
    @GetMapping
    public ResponseEntity<Page<VideoDto>> getAllVideos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        return ResponseEntity.ok(videoService.getAllVideos(page, size, sortBy));
    }

    // Получение информации о конкретном видео, включая ссылку на HLS
    @GetMapping("/{id}")
    public ResponseEntity<VideoDto> getVideoById(@PathVariable Long id) {
        VideoDto videoDto = videoService.getVideoById(id);
        String hlsLink = googleDriveService.getHlsLink(videoDto.getFileId());
        videoDto.setFileId(hlsLink);
        return ResponseEntity.ok(videoDto);
    }
}
