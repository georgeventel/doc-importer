package com.rampup.docImporter.service;

import com.rampup.docImporter.entity.DocumentImportFeedback;
import com.rampup.docImporter.repository.DocumentFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
