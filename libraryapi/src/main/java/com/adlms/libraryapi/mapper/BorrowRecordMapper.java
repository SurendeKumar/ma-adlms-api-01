package com.adlms.libraryapi.mapper;
import com.adlms.libraryapi.dto.BorrowRecordDTOs;
import com.adlms.libraryapi.entity.BorrowRecord;

public class BorrowRecordMapper {

    public static BorrowRecordDTOs.BorrowRecordResponse toResponse(BorrowRecord record) {
        return new BorrowRecordDTOs.BorrowRecordResponse(
                record.getId(),
                record.getUser().getId(),
                record.getDocument().getId(),
                record.getDocument().getTitle(),
                record.getBorrowDate(),
                record.getDueDate(),
                record.getReturnedDate()
        );
    }
}