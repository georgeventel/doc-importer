package com.rampup.docImporter.dto.wrapper;

import com.rampup.docImporter.dto.DocumentDto;

import java.util.List;

public class DocumentSourceWrapper {
    private List<DocumentDto> items;
    DocumentSourceWrapper(List<DocumentDto> items) {
        this.items = items;
    }
    public List<DocumentDto> getItems() {
        return items;
    }
}
