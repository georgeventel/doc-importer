package com.rampup.docimporter.client;

import com.rampup.docimporter.dto.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "gcp", url = "${gcp.service.url}")
public interface DocumentClient {
    @GetMapping("/processed")
    List<Document> getDocuments();
}
