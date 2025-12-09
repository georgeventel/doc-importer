package com.rampup.docImporter.dto;

import java.time.LocalDateTime;

public record ImportedDocumentDto(
        String documentName,
        String sharepointId,
        LocalDateTime importedAt
){}
