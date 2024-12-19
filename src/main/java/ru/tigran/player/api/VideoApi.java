package ru.tigran.player.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.tigran.player.service.dto.VideoDto;

import java.io.IOException;
import java.util.List;

    @RequestMapping("/api/video")
public interface VideoApi {

    @GetMapping
    ResponseEntity<List<VideoDto>> getVideosByHero(@RequestParam(value = "hero", required = false) String heroName);

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteVideo(@PathVariable Integer id);

    @GetMapping("/{id}")
    ResponseEntity<VideoDto> getVideoById(@PathVariable Integer id);

    @GetMapping("/stream/{videoId}")
    ResponseEntity<?> getVideoHLS(@PathVariable  Integer videoId) throws IOException;
}
