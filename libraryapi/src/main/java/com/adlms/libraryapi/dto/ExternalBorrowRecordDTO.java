//package com.adlms.libraryapi.dto;
//
//import java.time.LocalDate;
//
//public record ExternalBorrowRecordDTO(
//        Long id,
//        Long userId,
//        Long documentId,
//        LocalDate borrowDate,
//        LocalDate dueDate,
//        LocalDate returnedDate
//) {
//}



// New version for CI/CD Assignment 2
package com.adlms.libraryapi.dto;

import java.time.LocalDate;

public class ExternalBorrowRecordDTO {

    private Long id;
    private Long userId;
    private Long documentId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;

    public ExternalBorrowRecordDTO(Long id, Long userId, Long documentId,
                                   LocalDate borrowDate, LocalDate dueDate, LocalDate returnedDate) {
        this.id = id;
        this.userId = userId;
        this.documentId = documentId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }
}