package com.adlms.libraryapi.repository;

import com.adlms.libraryapi.entity.BorrowRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

//org.springframework.data.domain.Page<BorrowRecord> findByBorrowDateBetween(LocalDate from, LocalDate to, org.springframework.data.domain.Pageable pageable);


public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    Page<BorrowRecord> findByUserId(Long userId, Pageable pageable);
    
    Page<BorrowRecord> findByBorrowDateBetween(LocalDate from, LocalDate to, Pageable pageable);
}