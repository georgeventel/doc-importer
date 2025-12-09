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
    private String status; // e.g., "SUCCESS", "FAILED"
    private LocalDateTime executedAt;
    private Long importDurationMs;
    private Integer importedCount;

}
