package ru.tigran.player.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.tigran.player.mapper.VideoMapper;
import ru.tigran.player.model.VideoEntity;
import ru.tigran.player.repository.VideoRepository;
import ru.tigran.player.service.dto.VideoDto;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    public VideoService(VideoRepository videoRepository, VideoMapper videoMapper) {
        this.videoRepository = videoRepository;
        this.videoMapper = videoMapper;
    }

    // Получение списка видео с пагинацией и сортировкой
    public Page<VideoDto> getAllVideos(int page, int size, String sortBy) {
        Page<VideoEntity> videoEntities = videoRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
        return videoEntities.map(videoMapper::toDto);
    }

    // Получение информации о конкретном видео
    public VideoDto getVideoById(Long id) {
        return videoRepository.findById(id)
                .map(videoMapper::toDto)
                .orElse(null);
    }
}