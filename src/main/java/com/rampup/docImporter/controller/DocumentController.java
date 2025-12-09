package com.rampup.docImporter.controller;

import com.rampup.docImporter.dto.ImportedDocumentDto;
import com.rampup.docImporter.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/start")
    public ResponseEntity<List<ImportedDocumentDto>> startImport(){
        return ResponseEntity.ok(documentService.importDocuments());
    }
}
