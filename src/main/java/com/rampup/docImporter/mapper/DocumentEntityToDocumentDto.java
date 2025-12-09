package com.rampup.docImporter.mapper;

import com.rampup.docImporter.dto.DocumentDto;
import com.rampup.docImporter.entity.ImportedDocument;

public final class DocumentEntityToDocumentDto {
    public static DocumentDto map(ImportedDocument entity){
        DocumentDto dto = new DocumentDto();
        dto.setSharepointId(entity.getSharepointId());
        dto.setDocumentName(entity.getDocumentName());
        dto.setDocType(entity.getDocType());
        dto.setSharedLink(entity.getSharedLink());
        return dto;
    }
}
