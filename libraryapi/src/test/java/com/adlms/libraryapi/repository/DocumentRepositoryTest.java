package com.adlms.libraryapi.repository;

import com.adlms.libraryapi.entity.Document;
import com.adlms.libraryapi.entity.DocumentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository repository;

    @Test
    @DisplayName("should save and retrieve document by id")
    void shouldSaveAndFindDocumentById() {
        Document document = new Document(
                "Clean Code",
                "Robert C. Martin",
                DocumentType.Book,
                LocalDate.of(2008, 8, 1)
        );

        Document saved = repository.save(document);

        assertNotNull(saved.getId());

        Optional<Document> found = repository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Clean Code", found.get().getTitle());
        assertEquals("Robert C. Martin", found.get().getAuthor());
        assertEquals(DocumentType.Book, found.get().getDocumentType());
        assertEquals(LocalDate.of(2008, 8, 1), found.get().getPublishedDate());
    }

    @Test
    @DisplayName("should return empty when document id does not exist")
    void shouldReturnEmptyWhenDocumentNotFound() {
        Optional<Document> found = repository.findById(999L);

        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("should delete document successfully")
    void shouldDeleteDocumentSuccessfully() {
        Document document = new Document(
                "Effective Java",
                "Joshua Bloch",
                DocumentType.Book,
                LocalDate.of(2018, 1, 6)
        );

        Document saved = repository.save(document);
        Long id = saved.getId();

        assertTrue(repository.findById(id).isPresent());

        repository.deleteById(id);

        assertFalse(repository.findById(id).isPresent());
    }

    @Test
    @DisplayName("should return paged documents")
    void shouldReturnPagedDocuments() {
        repository.save(new Document(
                "Book One",
                "Author One",
                DocumentType.Book,
                LocalDate.of(2020, 1, 1)
        ));

        repository.save(new Document(
                "Journal One",
                "Author Two",
                DocumentType.Journal,
                LocalDate.of(2021, 2, 1)
        ));

        repository.save(new Document(
                "Book Two",
                "Author Three",
                DocumentType.Book,
                LocalDate.of(2022, 3, 1)
        ));

        var page = repository.findAll(org.springframework.data.domain.PageRequest.of(0, 2));

        assertEquals(2, page.getContent().size());
        assertEquals(3, page.getTotalElements());
        assertEquals(2, page.getSize());
        assertEquals(0, page.getNumber());
    }
}