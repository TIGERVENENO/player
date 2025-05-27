package ru.tigran.player.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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
    public ResponseEntity<List<VideoDto>> getVideosByAnimal(@RequestParam(value = "animal", required = false) String animalName) {
        if (animalName != null) {
            return ResponseEntity.ok(videoService.getVideosByAnimal(animalName));
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
            String hlsStreamPath = videoService.generateHLSStream(videoId);

            // Формируем полный URL
            String fullUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(hlsStreamPath)
                    .toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "text/plain"); // Указываем, что возвращаем текст

            return new ResponseEntity<>(fullUrl, headers, HttpStatus.OK); // Возвращаем URL
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating HLS stream");
        }
    }

}
