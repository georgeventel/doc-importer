package com.rampup.docImporter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentImportFeedbackDTO {
    private String status;
    private Long importDurationMs;
    private Integer importedCount;
    private LocalDateTime importedAt;
}
