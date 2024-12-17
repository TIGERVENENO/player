package ru.tigran.player.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.tigran.player.api.VideoApi;
import ru.tigran.player.service.VideoService;
import ru.tigran.player.service.dto.VideoDto;

import java.io.IOException;
import java.util.List;

@RestController
public class VideoController implements VideoApi {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @Override
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<VideoDto>> getVideosByHero(@RequestParam(value = "hero", required = false) String heroName) {
        if (heroName != null) {
            return ResponseEntity.ok(videoService.getVideosByHero(heroName));
        }
        return ResponseEntity.ok(videoService.getAllVideos());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteVideo(@PathVariable Integer id) {
        try {
            videoService.deleteVideo(id);
            return ResponseEntity.ok("Video deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Override
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<VideoDto> getVideoById(@PathVariable Integer id) {
        VideoDto videoDto = videoService.getVideoById(id);
        if (videoDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(videoDto);
    }

    @Override
    public ResponseEntity<?> getVideoHLS(@PathVariable Integer videoId) {
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
