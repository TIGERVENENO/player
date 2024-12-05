//package ru.tigran.player.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//@Service
//public class VideoUploadService {
//
//    @Value("${video.storage.path}")
//    private String videoStoragePath;  // Путь для сохранения видео
//
//    public void uploadVideo(MultipartFile file) throws IOException {
//        // Проверяем, что файл не пустой
//        if (file.isEmpty()) {
//            throw new IllegalArgumentException("File is empty");
//        }
//
//        // Сохраняем файл в директорию
//        String filePath = videoStoragePath + File.separator + file.getOriginalFilename();
//        Files.copy(file.getInputStream(), Paths.get(filePath));
//
//        // Здесь можно добавить дополнительную логику, например, сохранить информацию о видео в базе данных
//    }
//}
