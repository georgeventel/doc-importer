package com.rampup.docImporter.client;

import com.rampup.docImporter.dto.wrapper.DocumentSourceWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "DocumentProvider", url = "${document.source.url}")
public interface DocumentClient {
    @GetMapping("/processed")
    DocumentSourceWrapper getDocuments();
}
