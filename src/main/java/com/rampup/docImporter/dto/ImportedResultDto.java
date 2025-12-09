package com.rampup.docImporter.dto;

import java.time.LocalDateTime;

public record ImportedResultDto(
        String status,
        Long importDurationMs,
        Integer importedCount,
        LocalDateTime importedAt
){}
