package com.rampup.docImporter.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class DocumentImportFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String documentName;
    private String status; // e.g., "SUCCESS", "FAILED"
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    @OneToOne
    private ImportedDocument importedDocument;
}
