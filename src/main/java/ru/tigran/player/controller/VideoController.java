package ru.tigran.player.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
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
    @PreAuthorize(value = "hasRole('USER')")
    public ResponseEntity<Page<VideoDto>> getAllVideos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        return ResponseEntity.ok(videoService.getAllVideos(page, size, sortBy));
    }

    // Получение информации о конкретном видео, включая ссылку на HLS
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<VideoDto> getVideoById(@PathVariable Integer id) {
        VideoDto videoDto = videoService.getVideoById(id);
        String hlsLink = googleDriveService.getHlsLinkByVideoId(id);
        videoDto.setHlsLink(hlsLink);
        return ResponseEntity.ok(videoDto);
    }
}
