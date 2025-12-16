package com.rampup.docImporter.dto.util;

public enum SortableFields {
    EXECUTED_AT("executedAt"),
    STATUS("status"),
    IMPORTED_COUNT("importedCount"),
    IMPORT_DURATION_MS("importDurationMs");

    private final String fieldName;

    SortableFields(String fieldName) {
        this.fieldName = fieldName;
    }

    public static SortableFields from(String sortByLiteral) {
        for (SortableFields field : SortableFields.values()) {
            if (field.getFieldName().equalsIgnoreCase(sortByLiteral)) {
                return field;
            }
        }
        return EXECUTED_AT; // Default sorting field
    }

    public String getFieldName() {
        return fieldName;
    }
}
