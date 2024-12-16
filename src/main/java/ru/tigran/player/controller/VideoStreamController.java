package ru.tigran.player.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.tigran.player.api.VideoStreamApi;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class VideoStreamController implements VideoStreamApi {

    @Override
    public ResponseEntity<Resource> serveFile(String videoId, String filename) {
        try {
            Path filePath = Paths.get("videos/" + videoId + "/" + filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(filename.endsWith(".m3u8")
                            ? MediaType.parseMediaType("application/x-mpegURL")
                            : MediaType.parseMediaType("video/MP2T"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}