package com.adlms.libraryapi.mapper;
import com.adlms.libraryapi.dto.DocumentDTOs;
import com.adlms.libraryapi.entity.Document;

public class DocumentMapper {
	
	// method - return document response after mapper
	public static DocumentDTOs.DocumentResponse toResponse(Document document) {
		return new DocumentDTOs.DocumentResponse(
                document.getId(),
                document.getTitle(),
                document.getAuthor(),
                document.getDocumentType(),
                document.getPublishedDate()
        );
	}

}
