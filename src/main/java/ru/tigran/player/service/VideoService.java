package ru.tigran.player.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.tigran.player.mapper.VideoMapper;
import ru.tigran.player.model.VideoEntity;
import ru.tigran.player.repository.VideoRepository;
import ru.tigran.player.service.dto.VideoDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    private static final String VIDEO_DIRECTORY = "/usr/share/nginx/html/videos/";

    public VideoService(VideoRepository videoRepository, VideoMapper videoMapper) {
        this.videoRepository = videoRepository;
        this.videoMapper = videoMapper;
    }

    public String generateHLSStream(String videoId) throws IOException {
        String videoPath = VIDEO_DIRECTORY + videoId + ".mp4";
        String outputDirPath = VIDEO_DIRECTORY + videoId;
        String outputPath = outputDirPath + "/output.m3u8";

        // Расширенная диагностика
        System.out.println("Video Processing Details:");
        System.out.println("Video ID: " + videoId);
        System.out.println("Video Path: " + videoPath);
        System.out.println("Output Directory: " + outputDirPath);
        System.out.println("Output M3U8 Path: " + outputPath);

        // Проверка существования директории
        File videoDir = new File(VIDEO_DIRECTORY);
        if (!videoDir.exists()) {
            throw new IOException("Video storage directory does not exist: " + VIDEO_DIRECTORY);
        }

        // Листинг всех файлов в директории
        File[] files = videoDir.listFiles();
        if (files != null) {
            System.out.println("Files in video directory:");
            for (File file : files) {
                System.out.println(" - " + file.getName());
            }
        }

        // Проверяем, существует ли исходный файл
        File videoFile = new File(videoPath);
        if (!videoFile.exists()) {
            throw new IOException("Video file not found: " + videoPath + ". Please ensure the file exists with the exact name " + videoId + ".mp4");
        }

        // Создаем выходную папку, если она не существует
        File outputDir = new File(outputDirPath);
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new IOException("Failed to create output directory: " + outputDirPath);
        }

        // Команда FFmpeg
        String ffmpegCommand = String.format("ffmpeg -i %s -c:v libx264 -preset fast -crf 22 -c:a aac -strict -2 -f hls -hls_time 10 -hls_list_size 0 %s", videoPath, outputPath);

        // Выполняем команду
        Process process = Runtime.getRuntime().exec(ffmpegCommand);

        // Переменные для вывода
        StringBuilder output = new StringBuilder();
        StringBuilder error = new StringBuilder();

        // Обрабатываем вывод процесса
        try {
            handleProcessStream(process, output, error);
            int exitCode = process.waitFor();

            // Расширенный вывод ошибок
            if (exitCode != 0) {
                System.err.println("FFmpeg Error Output: " + error.toString());
                System.err.println("FFmpeg Standard Output: " + output.toString());
                throw new IOException("FFmpeg failed with exit code " + exitCode + ": " + error.toString());
            }

            // Проверяем, был ли создан файл HLS
            File hlsFile = new File(outputPath);
            if (!hlsFile.exists()) {
                throw new IOException("HLS output file not created: " + outputPath);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("FFmpeg process was interrupted", e);
        }

        return "/videos/" + videoId + "/output.m3u8";
    }

    // Метод handleProcessStream остается без изменений

    private void handleProcessStream(Process process, StringBuilder output, StringBuilder error) throws IOException {
        Thread outputThread = new Thread(() -> {
            try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread errorThread = new Thread(() -> {
            try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    error.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        outputThread.start();
        errorThread.start();

        try {
            outputThread.join();
            errorThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Остальные методы без изменений
    public Page<VideoDto> getAllVideos(int page, int size, String sortBy) {
        Page<VideoEntity> videoEntities = videoRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
        return videoEntities.map(videoMapper::toDto);
    }

    public VideoDto getVideoById(Long id) {
        return videoRepository.findById(id).map(videoMapper::toDto).orElse(null);
    }
}