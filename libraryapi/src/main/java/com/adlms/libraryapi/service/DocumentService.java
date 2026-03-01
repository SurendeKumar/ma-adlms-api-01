package com.adlms.libraryapi.service;

import com.adlms.libraryapi.dto.DocumentDTOs;
import com.adlms.libraryapi.entity.Document;
import com.adlms.libraryapi.exception.NotFoundException;
import com.adlms.libraryapi.mapper.DocumentMapper;
import com.adlms.libraryapi.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import com.adlms.libraryapi.dto.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Service
public class DocumentService {

    private final DocumentRepository repository;

    public DocumentService(DocumentRepository repository) {
        this.repository = repository;
    }

    
    // method: create document
    public DocumentDTOs.DocumentResponse create(DocumentDTOs.CreateDocumentRequest request) {
        Document document = new Document(
                request.title(),
                request.author(),
                request.documentType(),
                request.publishedDate()
        );

        return DocumentMapper.toResponse(repository.save(document));
    }

    
    // getAllI() version 1
//    public List<DocumentDTOs.DocumentResponse> getAll() {
//        return repository.findAll()
//                .stream()
//                .map(DocumentMapper::toResponse)
//                .collect(Collectors.toList());
//    }
    
    // method: get all documents
    public PageResponse<DocumentDTOs.DocumentResponse> getAll(Pageable pageable) {
        Page<DocumentDTOs.DocumentResponse> page = repository.findAll(pageable)
                .map(DocumentMapper::toResponse);

        return PageResponse.from(page);
    }

    
    // method: get document by id
    public DocumentDTOs.DocumentResponse getById(Long id) {
        Document document = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Document not found: " + id));

        return DocumentMapper.toResponse(document);
    }

    
    // method: delete
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Document not found: " + id);
        }
        repository.deleteById(id);
    }
    
    
    // method: update
    public DocumentDTOs.DocumentResponse update(Long id, DocumentDTOs.UpdateDocumentRequest request) {
        Document document = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Document not found: " + id));

        document.setTitle(request.title());
        document.setAuthor(request.author());
        document.setDocumentType(request.documentType());
        document.setPublishedDate(request.publishedDate());

        return DocumentMapper.toResponse(repository.save(document));
    }
    
    
}