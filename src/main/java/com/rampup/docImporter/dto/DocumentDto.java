package com.rampup.docImporter.dto;

import lombok.Data;

@Data
public class DocumentDto {
    private String sharepointId;
    private String documentName;
    private String docType;
    private String sharedLink;

}
