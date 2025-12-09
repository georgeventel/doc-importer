package com.rampup.docImporter.dto.wrapper;

import com.rampup.docImporter.dto.ImportedDocumentDTO;

import java.util.List;

public class DocumentSourceWrapper {
    private List<ImportedDocumentDTO> items;

    DocumentSourceWrapper(List<ImportedDocumentDTO> items) {
        this.items = items;
    }

    public List<ImportedDocumentDTO> getItems() {
        return items;
    }
}
