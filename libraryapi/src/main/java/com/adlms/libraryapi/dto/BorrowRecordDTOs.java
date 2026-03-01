package com.adlms.libraryapi.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public class BorrowRecordDTOs {

	// 1. borrow records response
    public record BorrowRecordResponse(
            Long id,
            Long userId,
            Long documentId,
            String documentTitle,
            @JsonFormat(pattern = "yyyy-MM-dd") LocalDate borrowDate,
            @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dueDate,
            @JsonFormat(pattern = "yyyy-MM-dd") LocalDate returnedDate
    ) { }

    // 2. create borrow records request
    public record CreateBorrowRecordRequest(
            @NotNull(message = "documentId is required") Long documentId,
            @NotNull(message = "borrowDate is required")
            @JsonFormat(pattern = "yyyy-MM-dd") LocalDate borrowDate,
            @NotNull(message = "dueDate is required")
            @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dueDate,
            @JsonFormat(pattern = "yyyy-MM-dd") LocalDate returnedDate
    ) { }

    // 3. update borrow record request
    public record UpdateBorrowRecordRequest(
            @NotNull(message = "documentId is required") Long documentId,
            @NotNull(message = "borrowDate is required")
            @JsonFormat(pattern = "yyyy-MM-dd") LocalDate borrowDate,
            @NotNull(message = "dueDate is required")
            @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dueDate,
            @JsonFormat(pattern = "yyyy-MM-dd") LocalDate returnedDate
    ) { }
}