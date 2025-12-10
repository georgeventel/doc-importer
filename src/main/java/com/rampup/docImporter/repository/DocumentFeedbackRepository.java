package com.rampup.docImporter.repository;

import com.rampup.docImporter.entity.DocumentImportFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentFeedbackRepository extends JpaRepository<DocumentImportFeedback, Long> {
}
