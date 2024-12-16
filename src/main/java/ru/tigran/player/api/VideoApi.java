package ru.tigran.player.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.tigran.player.service.dto.VideoDto;

import java.io.IOException;

@RequestMapping("/api/videos")
public interface VideoApi {

    @GetMapping
    ResponseEntity<Page<VideoDto>> getAllVideos(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "title") @NotBlank String sortBy);

    @GetMapping("/category/{category}")
    ResponseEntity<Page<VideoDto>> getVideosByCategory(
            @PathVariable @NotBlank String category,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "title") @NotBlank String sortBy);

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteVideo(@PathVariable Long id);

    @GetMapping("/{id}")
    ResponseEntity<VideoDto> getVideoById(@PathVariable Long id);

    @GetMapping("/hls/{videoId}")
    ResponseEntity<?> getVideoHLS(@PathVariable @NotBlank String videoId) throws IOException;
}
