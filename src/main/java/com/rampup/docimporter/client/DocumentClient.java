package com.rampup.docimporter.client;

import com.rampup.docimporter.dto.Document;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "DocumentProvider", url = "${document.source.url}")
public interface DocumentClient {
    @GetMapping("/processed")
    List<Document> getDocuments();
}
