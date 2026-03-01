package com.adlms.libraryapi.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "borrow_records")
public class BorrowRecord {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Child -> Parent
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable=false)
    private LibraryUser user;

    // Child -> Document
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="document_id", nullable=false)
    private Document document;

    @Column(nullable=false)
    private LocalDate borrowDate;

    @Column(nullable=false)
    private LocalDate dueDate;

    @Column
    private LocalDate returnedDate;

    protected BorrowRecord() { }
    
    
    // method BorrowRecord 
    public BorrowRecord(
    		LibraryUser user, 
    		Document document, 
    		LocalDate borrowDate, 
    		LocalDate dueDate, 
    		LocalDate returnedDate) {
        this.user = user;
        this.document = document;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
    }
    
    
    
    // methods: getter
    public Long getId() { 
    	return id; 
    	}
    
    public LibraryUser getUser() { 
    	return user; 
    	}
    
    public Document getDocument() { 
    	return document; 
    	}
    
    public LocalDate getBorrowDate() { 
    	return borrowDate; 
    	}
    
    public LocalDate getDueDate() { 
    	return dueDate; 
    	}
    
    public LocalDate getReturnedDate() { 
    	return returnedDate; 
    	}

    
    // methods setters
    public void setDocument(Document document) { 
    	this.document = document; 
    	}
    
    public void setBorrowDate(LocalDate borrowDate) { 
    	this.borrowDate = borrowDate; 
    	}
    
    public void setDueDate(LocalDate dueDate) { 
    	this.dueDate = dueDate; 
    	}
    
    public void setReturnedDate(LocalDate returnedDate) { 
    	this.returnedDate = returnedDate; 
    	}
}
