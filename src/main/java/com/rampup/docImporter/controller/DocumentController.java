package com.rampup.docImporter.controller;

import com.rampup.docImporter.dto.DocumentImportFeedbackDTO;
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
    public ResponseEntity<DocumentImportFeedbackDTO> createDocument() {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.createDocument());
    }

    @GetMapping("/list")
    public ResponseEntity<List<DocumentImportFeedbackDTO>> listDocuments() {
        return ResponseEntity.ok(documentService.listDocuments());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DocumentImportFeedbackDTO> updateDocument(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.updateDocument(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }


}
