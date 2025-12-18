package com.rampup.docImporter.controller;

import com.rampup.docImporter.dto.ImportedDocumentDTO;
import com.rampup.docImporter.entity.ImportedDocument;
import com.rampup.docImporter.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/document")
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping
    public ResponseEntity<ImportedDocumentDTO> createDocument(
            @RequestBody ImportedDocumentDTO document) {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.createDocument(document));
    }

    @GetMapping
    public ResponseEntity<List<ImportedDocument>> listDocuments() {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImportedDocumentDTO> updateDocument(
            @PathVariable Long id,
            @RequestBody ImportedDocumentDTO document) {
        return ResponseEntity.ok(documentService.updateDocument(id, document));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.deleteDocument(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImportedDocumentDTO> getDocument(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.getDocumentById(id));
    }


}
