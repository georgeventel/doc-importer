package com.rampup.docImporter.service;

import com.rampup.docImporter.dto.ImportedResultDto;
import com.rampup.docImporter.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    public @Nullable List<ImportedResultDto> importDocuments() {
        return null;
    }
}
