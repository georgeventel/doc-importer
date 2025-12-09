package com.rampup.docImporter.mapper;

import com.rampup.docImporter.dto.ImportedResultDto;
import com.rampup.docImporter.entity.DocumentImportFeedback;

public final class ImportFeedbackEntityToImportFeedbackDto {
    public static ImportedResultDto map(DocumentImportFeedback entity){
        return new ImportedResultDto(
                entity.getStatus(),
                entity.getImportDurationMs(),
                entity.getImportedCount(),
                entity.getExecutedAt()
        );
    }
}
