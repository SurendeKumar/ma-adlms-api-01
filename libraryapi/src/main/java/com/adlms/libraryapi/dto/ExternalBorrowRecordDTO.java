package com.adlms.libraryapi.dto;

import java.time.LocalDate;

public record ExternalBorrowRecordDTO(
        Long id,
        Long userId,
        Long documentId,
        LocalDate borrowDate,
        LocalDate dueDate,
        LocalDate returnedDate
) {
}