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
import java.util.List;

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
        List<ImportedDocumentDTO> importedImportedDocumentDTOS = documentClient.getDocuments().getItems();
        List<String> alreadyImportedSpIds = documentRepository.findAllSharepointIds();

        importedImportedDocumentDTOS = importedImportedDocumentDTOS.stream()
                .filter(importedDocumentDTO ->
                        !alreadyImportedSpIds.contains(importedDocumentDTO.getSharepointId())).toList();

        importedImportedDocumentDTOS.forEach(importedDocumentDTO ->
                documentRepository.save(DocumentDtoToDocumentEntity.map(importedDocumentDTO)));

        return importedImportedDocumentDTOS;
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

        return documentRepository.findById(id)
                .map(existingDocument -> {
                    existingDocument.setDocumentName(doc.getDocumentName());
                    existingDocument.setSharepointId(doc.getSharepointId());
                    existingDocument.setSharedLink(doc.getSharedLink());
                    existingDocument.setDocType(doc.getDocType());
                    documentRepository.save(existingDocument);
                    return DocumentEntityToDocumentDto.map(existingDocument);
                })
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));
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
}
