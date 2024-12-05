//package ru.tigran.player.controller;
//
//import org.springframework.data.domain.Page;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import ru.tigran.player.service.GoogleDriveService;
//import ru.tigran.player.service.VideoService;
//import ru.tigran.player.service.dto.VideoDto;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/drive/videos")
//public class GoogleDriveController {
//
//    private final VideoService videoService;
//    private final GoogleDriveService googleDriveService;
//
//    public GoogleDriveController(VideoService videoService, GoogleDriveService googleDriveService) {
//        this.videoService = videoService;
//        this.googleDriveService = googleDriveService;
//    }
//
//    // Получение всех видео с пагинацией и сортировкой
//    @GetMapping
//    @PreAuthorize("hasRole('USER')") // Доступно только для пользователей и администраторов
//    public ResponseEntity<Page<VideoDto>> getAllVideos(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "title") String sortBy) {
//        return ResponseEntity.ok(videoService.getAllVideos(page, size, sortBy));
//    }
//
//    // Получение видео по ID с HLS-ссылкой
//    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('USER')") // Доступно только для пользователей и администраторов
//    public ResponseEntity<VideoDto> getVideoById(@PathVariable Integer id) {
//        VideoDto videoDto = videoService.getVideoById(id);
//        // Получаем HLS-ссылку и добавляем её в DTO
//        String hlsLink = googleDriveService.getHlsLinkByVideoId(id);
//        videoDto.setHlsLink(hlsLink); // Предполагается, что в VideoDto есть поле hlsLink
//        return ResponseEntity.ok(videoDto);
//    }
//}
