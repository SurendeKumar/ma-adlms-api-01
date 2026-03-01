package com.adlms.libraryapi.dto;
import java.time.LocalDate;
import com.adlms.libraryapi.entity.DocumentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public class DocumentDTOs {
	
	// 1. response DTO (what API returns)
	public record DocumentResponse(
			Long id,
            String title,
            String author,
            DocumentType documentType,
            @JsonFormat(pattern = "yyyy-MM-dd") LocalDate publishedDate
		) {}
	
	// 2. request DTP (what API accepts for create)
	public record CreateDocumentRequest(
            @NotBlank(message = "title is required") String title,
            @NotBlank(message = "author is required") String author,
            @NotNull(message = "documentType is required") DocumentType documentType,
            @NotNull(message = "publishedDate is required")
            @JsonFormat(pattern = "yyyy-MM-dd") LocalDate publishedDate
    ) { }
	
	
	// 3. request DTP - what API accepts for update
	public record UpdateDocumentRequest(
            @NotBlank(message = "title is required") String title,
            @NotBlank(message = "author is required") String author,
            @NotNull(message = "documentType is required") DocumentType documentType,
            @NotNull(message = "publishedDate is required")
            @JsonFormat(pattern = "yyyy-MM-dd") LocalDate publishedDate
    ) { }
	

}
