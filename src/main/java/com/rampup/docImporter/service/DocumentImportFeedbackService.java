package com.rampup.docImporter.service;

import com.rampup.docImporter.dto.DocumentImportFeedbackDTO;
import com.rampup.docImporter.dto.util.PaginatedResponse;
import com.rampup.docImporter.dto.util.SortableFields;
import com.rampup.docImporter.entity.DocumentImportFeedback;
import com.rampup.docImporter.mapper.ImportFeedbackEntityToImportFeedbackDto;
import com.rampup.docImporter.repository.DocumentFeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@RequestMapping("/api/feedback")
@Slf4j
public class DocumentImportFeedbackService {
    private static final String REDIS_KEY_PREFIX = "doc: feedback:";
    private static final String REDIS_ALL_FEEDBACKS_KEY = "doc:feedbacks:all";
    private final DocumentFeedbackRepository documentFeedbackRepository;
    private final Optional<RedisTemplate<String, Object>> redisTemplate;
    @Value("${redis.enabled:false}")
    private boolean redisEnabled;
    @Value("${sql.enabled:true}")
    private boolean sqlEnabled;

    public void saveImportFeedback(String importResult, Integer size, Long duration, DocumentImportFeedback documentImportFeedback) {
        documentImportFeedback.setStatus(importResult);
        documentImportFeedback.setImportedCount(size);
        documentImportFeedback.setExecutedAt(LocalDateTime.now());
        documentImportFeedback.setImportDurationMs(duration);
        if (sqlEnabled)
            documentFeedbackRepository.save(documentImportFeedback);
        if (redisEnabled && redisTemplate.isPresent()) {
            RedisTemplate<String, Object> redisTemplate = this.redisTemplate.get();
            String redisKey = REDIS_KEY_PREFIX + documentImportFeedback.getId();
            redisTemplate.opsForValue().set(redisKey, documentImportFeedback);
            redisTemplate.opsForZSet().add(
                    REDIS_ALL_FEEDBACKS_KEY,
                    documentImportFeedback,
                    documentImportFeedback.getExecutedAt().toEpochSecond(ZoneOffset.UTC)
            );
        }

    }

    public PaginatedResponse<DocumentImportFeedbackDTO> getFeedbackFromSql(int page, int size, SortableFields sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy.getFieldName()));
        Page<DocumentImportFeedback> feedbackPage = documentFeedbackRepository.findAll(pageable);

        PaginatedResponse<DocumentImportFeedbackDTO> response = new PaginatedResponse<>();
        List<DocumentImportFeedbackDTO> feedbackDTOs = new ArrayList<>();
        feedbackPage.getContent().forEach(feedback ->
                feedbackDTOs.add(ImportFeedbackEntityToImportFeedbackDto.map(feedback)));

        response.setFeedback(feedbackDTOs);
        response.setCurrentPage(feedbackPage.getNumber());
        response.setTotalPages(feedbackPage.getTotalPages());
        response.setTotalElements(feedbackPage.getTotalElements());
        response.setPageSize(feedbackPage.getSize());

        return response;
    }

    public PaginatedResponse<DocumentImportFeedbackDTO> getFeedbackPaginated(int page, int size, SortableFields sortBy) {
        String cacheKey = String.format("feedback: page:%d: size:%d: sort:%s", page, size, sortBy);

        if (redisEnabled && redisTemplate.isPresent()) {
            RedisTemplate<String, Object> redisTemplate = this.redisTemplate.get();
            // Check cache first
            try {
                PaginatedResponse<DocumentImportFeedbackDTO> cached =
                        (PaginatedResponse<DocumentImportFeedbackDTO>) redisTemplate.opsForValue().get(cacheKey);

                if (cached != null) {
                    log.info("Retrieved from cache!");
                    return cached;
                }
            } catch (Exception e) {
                log.error("Error retrieving from cache: ", e);
            }
        }
        PaginatedResponse<DocumentImportFeedbackDTO> response = getFeedbackFromSql(page, size, sortBy);

        // Cache the result
        if (redisEnabled && redisTemplate.isPresent()) {
            RedisTemplate<String, Object> redisTemplate = this.redisTemplate.get();
            redisTemplate.opsForValue().set(cacheKey, response, Duration.ofMinutes(5));
        }

        return response;
    }
}
