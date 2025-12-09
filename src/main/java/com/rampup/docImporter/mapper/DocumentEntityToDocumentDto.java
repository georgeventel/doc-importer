package com.rampup.docImporter.mapper;

import com.rampup.docImporter.dto.ImportedDocumentDTO;
import com.rampup.docImporter.entity.ImportedDocument;

public final class DocumentEntityToDocumentDto {
    public static ImportedDocumentDTO map(ImportedDocument entity) {
        ImportedDocumentDTO dto = new ImportedDocumentDTO();
        dto.setSharepointId(entity.getSharepointId());
        dto.setDocumentName(entity.getDocumentName());
        dto.setDocType(entity.getDocType());
        dto.setSharedLink(entity.getSharedLink());
        return dto;
    }
}
