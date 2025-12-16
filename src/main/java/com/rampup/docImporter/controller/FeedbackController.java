package com.rampup.docImporter.controller;

import com.rampup.docImporter.dto.DocumentImportFeedbackDTO;
import com.rampup.docImporter.dto.util.PaginatedResponse;
import com.rampup.docImporter.service.DocumentImportFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeedbackController {
    private final DocumentImportFeedbackService documentImportFeedbackService;

    @GetMapping("/feedback")
    public PaginatedResponse<DocumentImportFeedbackDTO> getFeedback(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return documentImportFeedbackService.getFeedbackPaginated(page, size);
    }
}
