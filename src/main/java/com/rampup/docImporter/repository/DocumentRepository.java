package com.rampup.docImporter.repository;

import com.rampup.docImporter.entity.ImportedDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<ImportedDocument, Long> {
}
