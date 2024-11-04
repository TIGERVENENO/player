package ru.tigran.player.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.tigran.player.service.dto.VideoDto;
import ru.tigran.player.model.VideoEntity;
import ru.tigran.player.repository.VideoRepository;
import ru.tigran.player.mapper.VideoMapper;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    public VideoService(VideoRepository videoRepository, VideoMapper videoMapper) {
        this.videoRepository = videoRepository;
        this.videoMapper = videoMapper;
    }

    // Получение всех видео с пагинацией и сортировкой
    public Page<VideoDto> getAllVideos(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        return videoRepository.findAll(pageRequest)
                .map(videoMapper::toDto);
    }


    // Получение конкретного видео по ID
    public VideoDto getVideoById(Long id) {
        VideoEntity video = videoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Видео не найдено!"));
        return videoMapper.toDto(video);
    }
}
