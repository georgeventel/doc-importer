package com.rampup.docImporter.dto.util;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse<T> {
    private List<T> feedback;
    private int currentPage;
    private int totalPages;
    private int totalElements;
    private int pageSize;
}
