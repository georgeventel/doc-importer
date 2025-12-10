package com.rampup.docImporter.mapper;

import com.rampup.docImporter.dto.DocumentImportFeedbackDTO;
import com.rampup.docImporter.entity.DocumentImportFeedback;

public final class ImportFeedbackEntityToImportFeedbackDto {
    public static DocumentImportFeedbackDTO map(DocumentImportFeedback entity) {
        return DocumentImportFeedbackDTO.builder()
                .status(entity.getStatus())
                .importDurationMs(entity.getImportDurationMs())
                .importedCount(entity.getImportedCount())
                .importedAt(entity.getExecutedAt())
                .build();
    }
}
