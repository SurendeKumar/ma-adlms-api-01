package com.adlms.libraryapi.service;

import com.adlms.libraryapi.dto.DocumentDTOs;
import com.adlms.libraryapi.dto.PageResponse;
import com.adlms.libraryapi.entity.Document;
import com.adlms.libraryapi.entity.DocumentType;
import com.adlms.libraryapi.exception.NotFoundException;
import com.adlms.libraryapi.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    private DocumentRepository repository;

    @InjectMocks
    private DocumentService service;

    @Test
    void create_shouldSaveDocumentAndReturnResponse() throws Exception {
        DocumentDTOs.CreateDocumentRequest request =
                new DocumentDTOs.CreateDocumentRequest(
                        "Clean Code",
                        "Robert C. Martin",
                        DocumentType.Book,
                        LocalDate.of(2008, 8, 1)
                );

        Document savedDocument = new Document(
                "Clean Code",
                "Robert C. Martin",
                DocumentType.Book,
                LocalDate.of(2008, 8, 1)
        );
        setDocumentId(savedDocument, 1L);

        when(repository.save(any(Document.class))).thenReturn(savedDocument);

        DocumentDTOs.DocumentResponse response = service.create(request);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Clean Code", response.title());
        assertEquals("Robert C. Martin", response.author());
        assertEquals(DocumentType.Book, response.documentType());
        assertEquals(LocalDate.of(2008, 8, 1), response.publishedDate());

        verify(repository, times(1)).save(any(Document.class));
    }

    @Test
    void getAll_shouldReturnPagedResponse() throws Exception {
        Pageable pageable = PageRequest.of(0, 2);

        Document document1 = new Document(
                "Book One",
                "Author One",
                DocumentType.Book,
                LocalDate.of(2020, 1, 1)
        );
        setDocumentId(document1, 1L);

        Document document2 = new Document(
                "Journal One",
                "Author Two",
                DocumentType.Journal,
                LocalDate.of(2021, 1, 1)
        );
        setDocumentId(document2, 2L);

        Page<Document> documentPage = new PageImpl<>(List.of(document1, document2), pageable, 2);

        when(repository.findAll(pageable)).thenReturn(documentPage);

        PageResponse<DocumentDTOs.DocumentResponse> response = service.getAll(pageable);

//        assertNotNull(response);
//        assertEquals(2, response.content().size());
//        assertEquals(0, response.page());
//        assertEquals(2, response.size());
//        assertEquals(2, response.totalElements());
//        assertEquals(1, response.totalPages());
        
        
        // new version for new page response class
        assertEquals(2, response.getContent().size());
        assertEquals(0, response.getPage());
        assertEquals(2, response.getSize());
        assertEquals(2, response.getTotalElements());
        assertEquals(1, response.getTotalPages());

        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    void getById_shouldReturnDocumentWhenFound() throws Exception {
        Long id = 1L;

        Document document = new Document(
                "Domain-Driven Design",
                "Eric Evans",
                DocumentType.Book,
                LocalDate.of(2003, 8, 30)
        );
        setDocumentId(document, id);

        when(repository.findById(id)).thenReturn(Optional.of(document));

        DocumentDTOs.DocumentResponse response = service.getById(id);

        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals("Domain-Driven Design", response.title());
        assertEquals("Eric Evans", response.author());
        assertEquals(DocumentType.Book, response.documentType());

        verify(repository, times(1)).findById(id);
    }

    @Test
    void getById_shouldThrowNotFoundExceptionWhenDocumentDoesNotExist() {
        Long id = 99L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.getById(id));

        assertEquals("Document not found: 99", exception.getMessage());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void update_shouldUpdateDocumentAndReturnResponse() throws Exception {
        Long id = 1L;

        Document existingDocument = new Document(
                "Old Title",
                "Old Author",
                DocumentType.Book,
                LocalDate.of(2010, 1, 1)
        );
        setDocumentId(existingDocument, id);

        DocumentDTOs.UpdateDocumentRequest request =
                new DocumentDTOs.UpdateDocumentRequest(
                        "New Title",
                        "New Author",
                        DocumentType.Journal,
                        LocalDate.of(2024, 1, 1)
                );

        when(repository.findById(id)).thenReturn(Optional.of(existingDocument));
        when(repository.save(existingDocument)).thenReturn(existingDocument);

        DocumentDTOs.DocumentResponse response = service.update(id, request);

        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals("New Title", response.title());
        assertEquals("New Author", response.author());
        assertEquals(DocumentType.Journal, response.documentType());
        assertEquals(LocalDate.of(2024, 1, 1), response.publishedDate());

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(existingDocument);
    }

    @Test
    void update_shouldThrowNotFoundExceptionWhenDocumentDoesNotExist() {
        Long id = 50L;

        DocumentDTOs.UpdateDocumentRequest request =
                new DocumentDTOs.UpdateDocumentRequest(
                        "New Title",
                        "New Author",
                        DocumentType.Book,
                        LocalDate.of(2024, 1, 1)
                );

        when(repository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.update(id, request));

        assertEquals("Document not found: 50", exception.getMessage());
        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any(Document.class));
    }

    @Test
    void delete_shouldDeleteDocumentWhenItExists() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void delete_shouldThrowNotFoundExceptionWhenDocumentDoesNotExist() {
        Long id = 10L;

        when(repository.existsById(id)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.delete(id));

        assertEquals("Document not found: 10", exception.getMessage());
        verify(repository, times(1)).existsById(id);
        verify(repository, never()).deleteById(anyLong());
    }

    private void setDocumentId(Document document, Long id) throws Exception {
        Field field = Document.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(document, id);
    }
}