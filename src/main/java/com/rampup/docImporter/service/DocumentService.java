package com.rampup.docImporter.service;

import com.rampup.docImporter.client.DocumentClient;
import com.rampup.docImporter.dto.DocumentDto;
import com.rampup.docImporter.dto.ImportedResultDto;
import com.rampup.docImporter.entity.DocumentImportFeedback;
import com.rampup.docImporter.mapper.DocumentDtoToDocumentEntity;
import com.rampup.docImporter.mapper.ImportFeedbackEntityToImportFeedbackDto;
import com.rampup.docImporter.repository.DocumentFeedbackRepository;
import com.rampup.docImporter.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentClient documentClient;
    private final DocumentFeedbackRepository documentFeedbackRepository;

    public ImportedResultDto importDocuments() {

        Instant startTime = Instant.now();// start timer
        DocumentImportFeedback documentImportFeedback = new DocumentImportFeedback();
        try {
            List<DocumentDto> importedDocumentDtos = documentClient.getDocuments().getItems();
            List<String> alreadyImportedSpIds = documentRepository.findAllSharepointIds();

            importedDocumentDtos = importedDocumentDtos.stream()
                    .filter(documentDto -> !alreadyImportedSpIds.contains(documentDto.getSharepointId()))
                    .toList();

            importedDocumentDtos.forEach(documentDto ->
                    documentRepository.save(DocumentDtoToDocumentEntity.map(documentDto)));

            Instant endTime = Instant.now();// stop timer
            Long duration = Duration.between(startTime, endTime).toMillis();

            documentImportFeedback.setStatus("SUCCESS");
            documentImportFeedback.setImportedCount(importedDocumentDtos.size());
            documentImportFeedback.setExecutedAt(LocalDateTime.now());
            documentImportFeedback.setImportDurationMs(duration);

            documentFeedbackRepository.save(documentImportFeedback);

        } catch (Exception _) {
            Instant endTime = Instant.now();// stop timer
            Long duration = Duration.between(startTime, endTime).toMillis();

            documentImportFeedback.setStatus("FAILED");
            documentImportFeedback.setImportedCount(0);
            documentImportFeedback.setExecutedAt(LocalDateTime.now());
            documentImportFeedback.setImportDurationMs(duration);

            documentFeedbackRepository.save(documentImportFeedback);


        }


        return ImportFeedbackEntityToImportFeedbackDto.map(documentImportFeedback);
    }
}
