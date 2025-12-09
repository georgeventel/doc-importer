package com.rampup.docImporter.mapper;

import com.rampup.docImporter.dto.ImportedDocumentDTO;
import com.rampup.docImporter.entity.ImportedDocument;

public final class DocumentDtoToDocumentEntity {
    public static ImportedDocument map(ImportedDocumentDTO dto) {
        ImportedDocument entity = new ImportedDocument();
        entity.setSharepointId(dto.getSharepointId());
        entity.setDocumentName(dto.getDocumentName());
        entity.setDocType(dto.getDocType());
        entity.setSharedLink(dto.getSharedLink());
        return entity;
    }
}
