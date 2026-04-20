package com.adlms.libraryapi.controller;

import com.adlms.libraryapi.dto.DocumentDTOs;
import com.adlms.libraryapi.dto.PageResponse;
import com.adlms.libraryapi.entity.DocumentType;
import com.adlms.libraryapi.exception.GlobalExceptionHandler;
import com.adlms.libraryapi.exception.NotFoundException;
import com.adlms.libraryapi.service.DocumentService;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DocumentControllerTest {

    private final DocumentService service = mock(DocumentService.class);

    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new DocumentController(service))
            .setControllerAdvice(new GlobalExceptionHandler())
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .build();

    @Test
    void getAll_shouldReturnPagedDocuments() throws Exception {
        PageResponse<DocumentDTOs.DocumentResponse> response = new PageResponse<>(
                List.of(
                        new DocumentDTOs.DocumentResponse(
                                1L,
                                "Clean Code",
                                "Robert C. Martin",
                                DocumentType.Book,
                                LocalDate.of(2008, 8, 1),
                                false
                        ),
                        new DocumentDTOs.DocumentResponse(
                                2L,
                                "Effective Java",
                                "Joshua Bloch",
                                DocumentType.Book,
                                LocalDate.of(2018, 1, 6), 
                                false
                        )
                ),
                0,
                20,
                2,
                1
        );

        when(service.getAll(any(Pageable.class))).thenReturn(response);

        mockMvc.perform(get("/documents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Clean Code"))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1));

        verify(service, times(1)).getAll(any(Pageable.class));
    }

    @Test
    void getById_shouldReturnDocumentWhenFound() throws Exception {
        DocumentDTOs.DocumentResponse response = new DocumentDTOs.DocumentResponse(
                1L,
                "Domain-Driven Design",
                "Eric Evans",
                DocumentType.Book,
                LocalDate.of(2003, 8, 30), 
                false
        );

        when(service.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/documents/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Domain-Driven Design"))
                .andExpect(jsonPath("$.author").value("Eric Evans"))
                .andExpect(jsonPath("$.documentType").value("Book"))
                .andExpect(jsonPath("$.publishedDate").value("2003-08-30"));

        verify(service, times(1)).getById(1L);
    }

    @Test
    void getById_shouldReturn404WhenDocumentNotFound() throws Exception {
        when(service.getById(99L)).thenThrow(new NotFoundException("Document not found: 99"));

        mockMvc.perform(get("/documents/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Document not found: 99"))
                .andExpect(jsonPath("$.path").value("/documents/99"));

        verify(service, times(1)).getById(99L);
    }

    @Test
    void create_shouldReturn201AndLocationHeader() throws Exception {
//        String requestJson = """
//                {
//                  "title": "Clean Architecture",
//                  "author": "Robert C. Martin",
//                  "documentType": "Book",
//                  "publishedDate": "2017-09-20"
//                }
//                """;
//        
        String requestJson = "{\"title\":\"Clean Architecture\",\"author\":\"Robert C. Martin\",\"documentType\":\"Book\",\"publishedDate\":\"2017-09-20\"}";

        DocumentDTOs.DocumentResponse response = new DocumentDTOs.DocumentResponse(
                10L,
                "Clean Architecture",
                "Robert C. Martin",
                DocumentType.Book,
                LocalDate.of(2017, 9, 20),
                false
        );

        when(service.create(any(DocumentDTOs.CreateDocumentRequest.class))).thenReturn(response);

        mockMvc.perform(post("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/documents/10"))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.title").value("Clean Architecture"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"))
                .andExpect(jsonPath("$.documentType").value("Book"))
                .andExpect(jsonPath("$.publishedDate").value("2017-09-20"));

        verify(service, times(1)).create(any(DocumentDTOs.CreateDocumentRequest.class));
    }

    @Test
    void create_shouldReturn400WhenRequestIsInvalid() throws Exception {
//        String invalidJson = """
//                {
//                  "title": "",
//                  "author": "",
//                  "documentType": null,
//                  "publishedDate": null
//                }
//                """;
        
        String invalidJson = "{\"title\":\"\",\"author\":\"\",\"documentType\":null,\"publishedDate\":null}";

        mockMvc.perform(post("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.path").value("/documents"));

        verify(service, never()).create(any(DocumentDTOs.CreateDocumentRequest.class));
    }

    @Test
    void update_shouldReturnUpdatedDocument() throws Exception {
//        String requestJson = """
//                {
//                  "title": "Refactoring",
//                  "author": "Martin Fowler",
//                  "documentType": "Book",
//                  "publishedDate": "2018-11-19"
//                }
//                """;
        
        String requestJson = "{\"title\":\"Refactoring\",\"author\":\"Martin Fowler\",\"documentType\":\"Book\",\"publishedDate\":\"2018-11-19\"}";

        DocumentDTOs.DocumentResponse response = new DocumentDTOs.DocumentResponse(
                5L,
                "Refactoring",
                "Martin Fowler",
                DocumentType.Book,
                LocalDate.of(2018, 11, 19),
                false
        );

        when(service.update(eq(5L), any(DocumentDTOs.UpdateDocumentRequest.class))).thenReturn(response);

        mockMvc.perform(put("/documents/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.title").value("Refactoring"))
                .andExpect(jsonPath("$.author").value("Martin Fowler"))
                .andExpect(jsonPath("$.documentType").value("Book"))
                .andExpect(jsonPath("$.publishedDate").value("2018-11-19"));

        verify(service, times(1)).update(eq(5L), any(DocumentDTOs.UpdateDocumentRequest.class));
    }

    @Test
    void delete_shouldReturn204WhenDocumentExists() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/documents/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(1L);
    }

    @Test
    void delete_shouldReturn404WhenDocumentDoesNotExist() throws Exception {
        doThrow(new NotFoundException("Document not found: 77")).when(service).delete(77L);

        mockMvc.perform(delete("/documents/77"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Document not found: 77"))
                .andExpect(jsonPath("$.path").value("/documents/77"));

        verify(service, times(1)).delete(77L);
    }
}