package ru.tigran.player.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.tigran.player.api.VideoApi;
import ru.tigran.player.service.VideoService;
import ru.tigran.player.service.dto.VideoDto;

import java.io.IOException;

@RestController
public class VideoController implements VideoApi {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @Override
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Page<VideoDto>> getAllVideos(int page, int size, String sortBy) {
        return ResponseEntity.ok(videoService.getAllVideos(page, size, sortBy));
    }

    @Override
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Page<VideoDto>> getVideosByCategory(String category, int page, int size, String sortBy) {
        return ResponseEntity.ok(videoService.getVideosByCategory(category, page, size, sortBy));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteVideo(Long id) {
        try {
            videoService.deleteVideo(id);
            return ResponseEntity.ok("Video deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Override
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<VideoDto> getVideoById(Long id) {
        VideoDto videoDto = videoService.getVideoById(id);
        if (videoDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(videoDto);
    }

    @Override
    public ResponseEntity<?> getVideoHLS(String videoId) throws IOException {
        try {
            String hlsStreamUrl = videoService.generateHLSStream(videoId);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/vnd.apple.mpegurl");
            return new ResponseEntity<>(hlsStreamUrl, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating HLS stream");
        }
    }
}
