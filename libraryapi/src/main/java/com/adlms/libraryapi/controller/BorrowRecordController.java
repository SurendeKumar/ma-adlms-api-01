package com.adlms.libraryapi.controller;

import com.adlms.libraryapi.dto.BorrowRecordDTOs;
import com.adlms.libraryapi.service.BorrowRecordService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.adlms.libraryapi.dto.PageResponse;
import org.springframework.data.domain.Pageable;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;


@RestController
public class BorrowRecordController {

    private final BorrowRecordService service;

    public BorrowRecordController(BorrowRecordService service) {
        this.service = service;
    }

    // GET /users/{id}/borrow-records version 1
//    @GetMapping("/users/{userId}/borrow-records")
//    public List<BorrowRecordDTOs.BorrowRecordResponse> getByUser(
//            @PathVariable Long userId
//    ) {
//        return service.getByUser(userId);
//    }
    
    
    @GetMapping("/users/{userId}/borrow-records")
    public PageResponse<BorrowRecordDTOs.BorrowRecordResponse> getByUser(
            @PathVariable Long userId,
            Pageable pageable
    ) {
        return service.getByUser(userId, pageable);
    }

    
    
    // POST /users/{id}/borrow-records
    @PostMapping("/users/{userId}/borrow-records")
    public BorrowRecordDTOs.BorrowRecordResponse create(
            @PathVariable Long userId,
            @Valid @RequestBody BorrowRecordDTOs.CreateBorrowRecordRequest request
    ) {
        return service.create(userId, request);
    }
    
    
    @GetMapping("/borrow-records")
    public PageResponse<BorrowRecordDTOs.BorrowRecordResponse> filterByBorrowDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            Pageable pageable
    ) {
        return service.getByBorrowDateRange(from, to, pageable);
    }
    
    
    
    @PostMapping("/users/{userId}/borrow-records")
    public ResponseEntity<BorrowRecordDTOs.BorrowRecordResponse> create(
            @PathVariable Long userId,
            @Valid @RequestBody BorrowRecordDTOs.CreateBorrowRecordRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        BorrowRecordDTOs.BorrowRecordResponse created = service.create(userId, request);

        URI location = uriBuilder
                .path("/users/{userId}/borrow-records/{recordId}")
                .buildAndExpand(userId, created.id())
                .toUri();

        return ResponseEntity.created(location).body(created); // 201
    }
    
    
    
    @PutMapping("/users/{userId}/borrow-records/{recordId}")
    public ResponseEntity<BorrowRecordDTOs.BorrowRecordResponse> update(
            @PathVariable Long userId,
            @PathVariable Long recordId,
            @Valid @RequestBody BorrowRecordDTOs.UpdateBorrowRecordRequest request
    ) {
        return ResponseEntity.ok(service.update(userId, recordId, request)); // 200
    }
    
    
    @DeleteMapping("/users/{userId}/borrow-records/{recordId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long userId,
            @PathVariable Long recordId
    ) {
        service.delete(userId, recordId);
        return ResponseEntity.noContent().build(); // 204
    }
    
    
}