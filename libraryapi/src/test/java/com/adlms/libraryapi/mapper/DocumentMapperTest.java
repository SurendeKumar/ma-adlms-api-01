package com.adlms.libraryapi.mapper;

import com.adlms.libraryapi.dto.DocumentDTOs;
import com.adlms.libraryapi.entity.Document;
import com.adlms.libraryapi.entity.DocumentType;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DocumentMapperTest {

    @Test
    void toResponse_shouldMapDocumentToDocumentResponse() throws Exception {
        Document document = new Document(
                "Clean Code",
                "Robert C. Martin",
                DocumentType.Book,
                LocalDate.of(2008, 8, 1)
        );

        setDocumentId(document, 1L);

        DocumentDTOs.DocumentResponse response = DocumentMapper.toResponse(document);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Clean Code", response.title());
        assertEquals("Robert C. Martin", response.author());
        assertEquals(DocumentType.Book, response.documentType());
        assertEquals(LocalDate.of(2008, 8, 1), response.publishedDate());
    }

    @Test
    void toResponse_shouldPreserveJournalTypeAndDate() throws Exception {
        Document document = new Document(
                "Nature Review",
                "Editorial Team",
                DocumentType.Journal,
                LocalDate.of(2024, 5, 10)
        );

        setDocumentId(document, 25L);

        DocumentDTOs.DocumentResponse response = DocumentMapper.toResponse(document);

        assertNotNull(response);
        assertEquals(25L, response.id());
        assertEquals("Nature Review", response.title());
        assertEquals("Editorial Team", response.author());
        assertEquals(DocumentType.Journal, response.documentType());
        assertEquals(LocalDate.of(2024, 5, 10), response.publishedDate());
    }

    private void setDocumentId(Document document, Long id) throws Exception {
        Field field = Document.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(document, id);
    }
}