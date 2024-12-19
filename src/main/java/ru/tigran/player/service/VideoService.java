package ru.tigran.player.service;

import org.springframework.stereotype.Service;
import ru.tigran.player.mapper.VideoMapper;
import ru.tigran.player.model.HeroEntity;
import ru.tigran.player.model.VideoEntity;
import ru.tigran.player.repository.CategoryRepository;
import ru.tigran.player.repository.HeroRepository;
import ru.tigran.player.repository.VideoRepository;
import ru.tigran.player.service.dto.VideoDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;
    private final HeroRepository heroRepository;

    // Используем относительный путь к локальной папке с видео
    private static final String VIDEO_DIRECTORY = "videos/";

    public VideoService(VideoRepository videoRepository, VideoMapper videoMapper, HeroRepository heroRepository) {
        this.videoRepository = videoRepository;
        this.videoMapper = videoMapper;
        this.heroRepository = heroRepository;
    }

    public String generateHLSStream(Integer videoId) throws IOException {
        // Получаем абсолютный путь к папке проекта
        Path projectPath = Paths.get("").toAbsolutePath();
        String videoPath = projectPath.resolve(VIDEO_DIRECTORY + videoId + ".mp4").toString();
        String outputDirPath = projectPath.resolve(VIDEO_DIRECTORY + videoId).toString();
        String outputPath = outputDirPath + "/output.m3u8";

        // Расширенная диагностика
        System.out.println("Project Base Path: " + projectPath);
        System.out.println("Video Processing Details:");
        System.out.println("Video ID: " + videoId);
        System.out.println("Video Path: " + videoPath);
        System.out.println("Output Directory: " + outputDirPath);
        System.out.println("Output M3U8 Path: " + outputPath);

        // Проверка, существует ли папка с нарезанным видео
        File outputDir = new File(outputDirPath);
        File hlsFile = new File(outputPath);

        if (outputDir.exists() && hlsFile.exists()) {
            // Папка и HLS файл уже существуют, возвращаем путь к HLS файлу
            System.out.println("HLS already exists. Skipping FFmpeg processing.");
            return "/api/stream/" + videoId + "/output.m3u8";
        }

        // Проверяем, существует ли исходный файл
        File videoFile = new File(videoPath);
        if (!videoFile.exists()) {
            throw new IOException("Video file not found: " + videoPath +
                    ". Please ensure the file exists with the exact name " + videoId + ".mp4");
        }

        // Создаем выходную папку, если она не существует
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new IOException("Failed to create output directory: " + outputDirPath);
        }

        // Команда FFmpeg
        String ffmpegCommand = String.format(
                "ffmpeg -i %s -c:v libx264 -preset fast -crf 22 -c:a aac -strict -2 -f hls -hls_time 10 -hls_list_size 0 %s",
                videoPath, outputPath
        );

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
            if (!hlsFile.exists()) {
                throw new IOException("HLS output file not created: " + outputPath);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("FFmpeg process was interrupted", e);
        }

        return "/api/stream/" + videoId + "/output.m3u8";
    }

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

    public List<VideoDto> getAllVideos() {
        List<VideoEntity> videoEntities = videoRepository.findAll();
        return videoEntities.stream()
                .map(videoMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<VideoDto> getVideosByHero(String heroName) {
        HeroEntity hero = heroRepository.findByName(heroName);
        List<VideoEntity> videoEntities = videoRepository.findByHero(hero);
        return videoEntities.stream()
                .map(videoMapper::toDto)
                .collect(Collectors.toList());
    }

    public VideoDto getVideoById(Integer id) {
        VideoEntity videoEntities = videoRepository.findById(id);
        return videoMapper.toDto(videoEntities);
    }

    public void deleteVideo(Integer id) {
        if (!videoRepository.existsById(Long.valueOf(id))) {
            throw new IllegalArgumentException("Video not found with ID: " + id);
        }
        videoRepository.deleteById(Long.valueOf(id));
    }
}