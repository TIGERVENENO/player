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

    public Page<VideoDto> getAllVideos(int page, int size, String sortBy) {
        Page<VideoEntity> videoEntities = videoRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
        return videoEntities.map(videoMapper::toDto);
    }

    public VideoDto getVideoById(Integer id) {
        VideoEntity videoEntity = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));
        return videoMapper.toDto(videoEntity);
    }
}
