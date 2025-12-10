package com.rampup.docImporter.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DocumentImportFeedbackDTO {
    private String status;
    private Long importDurationMs;
    private Integer importedCount;
    private LocalDateTime importedAt;
}
