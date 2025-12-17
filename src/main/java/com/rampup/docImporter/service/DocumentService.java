package com.rampup.docImporter.service;

import com.rampup.docImporter.client.DocumentClient;
import com.rampup.docImporter.dto.DocumentImportFeedbackDTO;
import com.rampup.docImporter.dto.ImportedDocumentDTO;
import com.rampup.docImporter.entity.DocumentImportFeedback;
import com.rampup.docImporter.entity.ImportedDocument;
import com.rampup.docImporter.mapper.DocumentDtoToDocumentEntity;
import com.rampup.docImporter.mapper.DocumentEntityToDocumentDto;
import com.rampup.docImporter.mapper.ImportFeedbackEntityToImportFeedbackDto;
import com.rampup.docImporter.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentClient documentClient;
    private final DocumentImportFeedbackService documentImportFeedbackService;

    public DocumentImportFeedbackDTO importDocuments() {
        Instant startTime = Instant.now();// start timer
        DocumentImportFeedback documentImportFeedback = new DocumentImportFeedback();
        String importFeedback = StringUtils.EMPTY;
        int size = 0;
        log.info("Start importing documents");
        try {
            List<ImportedDocumentDTO> importedImportedDocumentDTOS = importDocumentDtos();
            importFeedback = "SUCCESS";
            size = importedImportedDocumentDTOS.size();
            log.info("End importing documents. Imported {} new documents", importedImportedDocumentDTOS.size());
        } catch (Exception e) {
            importFeedback = "FAILED";
            log.error("Error occurred during document import: ", e);
        } finally {
            Instant endTime = Instant.now();// stop timer
            Long duration = Duration.between(startTime, endTime).toMillis();

            documentImportFeedbackService.saveImportFeedback(importFeedback, size, duration, documentImportFeedback);
        }

        return ImportFeedbackEntityToImportFeedbackDto.map(documentImportFeedback);
    }

    private List<ImportedDocumentDTO> importDocumentDtos() {
        List<ImportedDocumentDTO> incomingDocs =
                documentClient.getDocuments().getItems();

        Map<String, ImportedDocument> existingBySpId =
                documentRepository.findAll().stream()
                        .collect(Collectors.toMap(
                                ImportedDocument::getSharepointId,
                                Function.identity()
                        ));

        List<ImportedDocumentDTO> processed = new ArrayList<>();

        for (ImportedDocumentDTO dto : incomingDocs) {
            ImportedDocument entity = existingBySpId.get(dto.getSharepointId());

            if (entity == null) {
                // NEW document
                entity = DocumentDtoToDocumentEntity.map(dto);
                documentRepository.save(entity);
                processed.add(dto);
            } else {
                // EXISTING document, update only if changed
                if (applyChanges(entity, dto)) {
                    documentRepository.save(entity);
                    processed.add(dto);
                }
            }
        }

        return processed;
    }


    public ImportedDocumentDTO createDocument(ImportedDocumentDTO doc) {
        log.info("Created document with name: {}", doc.getDocumentName());
        documentRepository.save(DocumentDtoToDocumentEntity.map(doc));
        return doc;
    }

    public List<ImportedDocument> listDocuments() {
        log.info("Listing documents");
        return documentRepository.findAll();
    }

    public ImportedDocumentDTO updateDocument(Long id, ImportedDocumentDTO doc) {
        log.info("Updating document with id: {}", id);

        ImportedDocument entity = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));

        applyChanges(entity, doc);
        documentRepository.save(entity);

        return DocumentEntityToDocumentDto.map(entity);
    }


    public void deleteDocument(Long id) {
        log.info("Delete document with id: {}", id);
        documentRepository.deleteById(id);
    }


    public ImportedDocumentDTO getDocumentById(Long id) {
        log.info("Get document with id: {}", id);
        return DocumentEntityToDocumentDto.map(documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id)));
    }

    private boolean applyChanges(ImportedDocument entity, ImportedDocumentDTO dto) {
        boolean changed = false;

        if (!Objects.equals(entity.getDocumentName(), dto.getDocumentName())) {
            entity.setDocumentName(dto.getDocumentName());
            changed = true;
        }
        if (!Objects.equals(entity.getSharepointId(), dto.getSharepointId())) {
            entity.setSharepointId(dto.getSharepointId());
            changed = true;
        }
        if (!Objects.equals(entity.getSharedLink(), dto.getSharedLink())) {
            entity.setSharedLink(dto.getSharedLink());
            changed = true;
        }
        if (!Objects.equals(entity.getDocType(), dto.getDocType())) {
            entity.setDocType(dto.getDocType());
            changed = true;
        }

        return changed;
    }

}
