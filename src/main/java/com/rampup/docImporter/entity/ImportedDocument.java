package com.rampup.docImporter.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ImportedDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String sharepointId;
    String documentName;
    String docType;
    @Column(length=10_000)
    String sharedLink;

}
