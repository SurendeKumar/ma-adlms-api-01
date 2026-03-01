package com.adlms.libraryapi.service;
import com.adlms.libraryapi.dto.BorrowRecordDTOs;
import com.adlms.libraryapi.entity.BorrowRecord;
import com.adlms.libraryapi.entity.Document;
import com.adlms.libraryapi.entity.LibraryUser;
import com.adlms.libraryapi.exception.NotFoundException;
import com.adlms.libraryapi.mapper.BorrowRecordMapper;
import com.adlms.libraryapi.repository.BorrowRecordRepository;
import com.adlms.libraryapi.repository.DocumentRepository;
import com.adlms.libraryapi.repository.LibraryUserRepository;
import org.springframework.stereotype.Service;
import com.adlms.libraryapi.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;


@Service
public class BorrowRecordService {

    private final BorrowRecordRepository borrowRepository;
    private final LibraryUserRepository userRepository;
    private final DocumentRepository documentRepository;

    public BorrowRecordService(
            BorrowRecordRepository borrowRepository,
            LibraryUserRepository userRepository,
            DocumentRepository documentRepository
    ) {
        this.borrowRepository = borrowRepository;
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
    }
    
    
    
    // method - create borrow records
    public BorrowRecordDTOs.BorrowRecordResponse create(
            Long userId,
            BorrowRecordDTOs.CreateBorrowRecordRequest request
    ) {

        LibraryUser user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId));

        Document document = documentRepository.findById(request.documentId())
                .orElseThrow(() -> new NotFoundException("Document not found: " + request.documentId()));

        BorrowRecord record = new BorrowRecord(
                user,
                document,
                request.borrowDate(),
                request.dueDate(),
                request.returnedDate()
        );

        return BorrowRecordMapper.toResponse(borrowRepository.save(record));
    }
    
    

    // getByUser version 1
//    public List<BorrowRecordDTOs.BorrowRecordResponse> getByUser(Long userId) {
//        return borrowRepository.findAll()
//                .stream()
//                .filter(r -> r.getUser().getId().equals(userId))
//                .map(BorrowRecordMapper::toResponse)
//                .collect(Collectors.toList());
//    }
    
    
    // method - get records by user
    public PageResponse<BorrowRecordDTOs.BorrowRecordResponse> getByUser(Long userId, Pageable pageable) {
        Page<BorrowRecordDTOs.BorrowRecordResponse> page =
                borrowRepository.findByUserId(userId, pageable)
                        .map(BorrowRecordMapper::toResponse);

        return PageResponse.from(page);
    }
    
    
    // method get borrow date range
    public PageResponse<BorrowRecordDTOs.BorrowRecordResponse> getByBorrowDateRange(
            LocalDate from,
            LocalDate to,
            Pageable pageable
    ) {
        Page<BorrowRecordDTOs.BorrowRecordResponse> page =
                borrowRepository.findByBorrowDateBetween(from, to, pageable)
                        .map(BorrowRecordMapper::toResponse);

        return PageResponse.from(page);
    }
    
    
    
    // method update borrow record 
    public BorrowRecordDTOs.BorrowRecordResponse update(
            Long userId,
            Long recordId,
            BorrowRecordDTOs.UpdateBorrowRecordRequest request
    ) {
        BorrowRecord record = borrowRepository.findById(recordId)
                .orElseThrow(() -> new NotFoundException("BorrowRecord not found: " + recordId));

        if (!record.getUser().getId().equals(userId)) {
            throw new NotFoundException("BorrowRecord " + recordId + " not found for user " + userId);
        }

        Document document = documentRepository.findById(request.documentId())
                .orElseThrow(() -> new NotFoundException("Document not found: " + request.documentId()));

        record.setDocument(document);
        record.setBorrowDate(request.borrowDate());
        record.setDueDate(request.dueDate());
        record.setReturnedDate(request.returnedDate());

        return BorrowRecordMapper.toResponse(borrowRepository.save(record));
    }
    
    
    // method - delete borrow record
    public void delete(Long userId, Long recordId) {
        BorrowRecord record = borrowRepository.findById(recordId)
                .orElseThrow(() -> new NotFoundException("BorrowRecord not found: " + recordId));

        if (!record.getUser().getId().equals(userId)) {
            throw new NotFoundException("BorrowRecord " + recordId + " not found for user " + userId);
        }

        borrowRepository.delete(record);
    }
    
    
}