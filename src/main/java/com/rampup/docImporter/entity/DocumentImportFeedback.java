package com.rampup.docImporter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class DocumentImportFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status; // e.g., "SUCCESS", "FAILED"
    private LocalDateTime executedAt;
    private Long importDurationMs;
    private Integer importedCount;

}
