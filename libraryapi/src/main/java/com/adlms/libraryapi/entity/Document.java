package com.adlms.libraryapi.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="documents")
public class Document {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType documentType;

    @Column(nullable = false)
    private LocalDate publishedDate;
    
    
    // required by JPA
    protected Document() {}
    
    public Document(
    		String title, 
    		String author, 
    		DocumentType documentType, 
    		LocalDate publishedDate) {
    	this.title=title;
    	this.author=author;
    	this.documentType=documentType;
    	this.publishedDate=publishedDate;
    }
    
    
    // getter
    public Long getId() { 
    	return id; 
    	}

    public String getTitle() { 
    	return title; 
    	}

    public String getAuthor() { 
    	return author; 
    	}

    public DocumentType getDocumentType() { 
    	return documentType; 
    	}

    public LocalDate getPublishedDate() { 
    	return publishedDate; 
    	}
    
    
    // setter
    // 1. title
    public void setTitle(String title) {
    	this.title=title;
    }
    
    // 2. author
    public void setAuthor(String author) { 
    	this.author = author; 
    	}
    
    // 3. documentType
    public void setDocumentType(DocumentType documentType) { 
    	this.documentType = documentType; 
    	}

    // 4. publish date
    public void setPublishedDate(LocalDate publishedDate) { 
    	this.publishedDate = publishedDate; 
    	}
}
    

