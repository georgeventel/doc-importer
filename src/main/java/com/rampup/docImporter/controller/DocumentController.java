package com.rampup.docImporter.controller;

import com.rampup.docImporter.dto.DocumentImportFeedbackDTO;
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
@RequestMapping("/api/documents")
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/start")
    public ResponseEntity<DocumentImportFeedbackDTO> startImport() {
        return ResponseEntity.ok(documentService.importDocuments());
    }

    @PostMapping("/create")
    public ResponseEntity<ImportedDocumentDTO> createDocument(
            @RequestBody ImportedDocumentDTO document) {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.createDocument(document));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ImportedDocument>> listDocuments() {
        return ResponseEntity.ok(documentService.listDocuments());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ImportedDocumentDTO> updateDocument(
            @PathVariable Long id,
            @RequestBody ImportedDocumentDTO document) {
        return ResponseEntity.ok(documentService.updateDocument(id, document));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ImportedDocumentDTO> getDocument(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.getDocumentById(id));
    }


}
