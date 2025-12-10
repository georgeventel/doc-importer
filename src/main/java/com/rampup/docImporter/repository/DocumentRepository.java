package com.rampup.docImporter.repository;

import com.rampup.docImporter.entity.ImportedDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<ImportedDocument, Long> {
    @Query("SELECT d.sharepointId FROM ImportedDocument d")
    List<String> findAllSharepointIds();
}
