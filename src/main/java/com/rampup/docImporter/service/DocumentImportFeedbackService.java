package com.rampup.docImporter.service;

import com.rampup.docImporter.dto.DocumentImportFeedbackDTO;
import com.rampup.docImporter.dto.util.PaginatedResponse;
import com.rampup.docImporter.dto.util.SortableFields;
import com.rampup.docImporter.entity.DocumentImportFeedback;
import com.rampup.docImporter.mapper.ImportFeedbackEntityToImportFeedbackDto;
import com.rampup.docImporter.repository.DocumentFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentImportFeedbackService {
    private final DocumentFeedbackRepository documentFeedbackRepository;

    public void saveImportFeedback(String importResult, Integer size, Long duration, DocumentImportFeedback documentImportFeedback) {
        documentImportFeedback.setStatus(importResult);
        documentImportFeedback.setImportedCount(size);
        documentImportFeedback.setExecutedAt(LocalDateTime.now());
        documentImportFeedback.setImportDurationMs(duration);
        documentFeedbackRepository.save(documentImportFeedback);

    }

    public PaginatedResponse<DocumentImportFeedbackDTO> getFeedbackPaginated(int page, int size, SortableFields sortBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, sortBy.getFieldName()));
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
}
