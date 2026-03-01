package com.adlms.libraryapi.controller;

import com.adlms.libraryapi.dto.DocumentDTOs;
import com.adlms.libraryapi.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.adlms.libraryapi.dto.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;


@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService service;

    public DocumentController(DocumentService service) {
        this.service = service;
    }

    @PostMapping
    public DocumentDTOs.DocumentResponse create(
            @Valid @RequestBody DocumentDTOs.CreateDocumentRequest request
    ) {
        return service.create(request);
    }

    
    // getAll() version 1
//    @GetMapping
//    public List<DocumentDTOs.DocumentResponse> getAll() {
//        return service.getAll();
//    }
    
    
    @GetMapping
    public PageResponse<DocumentDTOs.DocumentResponse> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }
    

    @GetMapping("/{id}")
    public DocumentDTOs.DocumentResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    
    
    @PostMapping
    public ResponseEntity<DocumentDTOs.DocumentResponse> create(
            @Valid @RequestBody DocumentDTOs.CreateDocumentRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        DocumentDTOs.DocumentResponse created = service.create(request);

        URI location = uriBuilder.path("/documents/{id}")
                .buildAndExpand(created.id())
                .toUri();

        return ResponseEntity.created(location).body(created); // 201 Created
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<DocumentDTOs.DocumentResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody DocumentDTOs.UpdateDocumentRequest request
    ) {
        DocumentDTOs.DocumentResponse updated = service.update(id, request);
        return ResponseEntity.ok(updated); // 200 OK
    }
    
    
    // old delete version 
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable Long id) {
//        service.delete(id);
//    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
    
}