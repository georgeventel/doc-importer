package com.rampup.docImporter.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class ImportedDocument {
    String sharepointId;
    String documentName;
    String docType;
    @Column(length = 10_000)
    String sharedLink;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdateTime;

}
