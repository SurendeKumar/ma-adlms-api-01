package com.adlms.libraryapi.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="library_users")
public class LibraryUser {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private LocalDate memberSince;
    
    // parent -> child: required one-to-many
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowRecord> borrowRecords = new ArrayList<>();
    
    
    protected LibraryUser() { }
    
    public LibraryUser(String name, String email, LocalDate memberSince) {
        this.name = name;
        this.email = email;
        this.memberSince = memberSince;
    }
    
    // method getters
    // 1. get id
    public Long getId() { 
    	return id; 
    	}
    
    // 2. get name 
    public String getName() { 
    	return name; 
    	}
    
    // 3. get email
    public String getEmail() { 
    	return email; 
    	}
    
    // 4. get membership details
    public LocalDate getMemberSince() { 
    	return memberSince; 
    	}
    
    // private records for borrowing
    public List<BorrowRecord> getBorrowRecords() { 
    	return borrowRecords; 
    	}

    // methods setters - name, email, membership details
    public void setName(String name) { 
    	this.name = name; 
    	}
    
    public void setEmail(String email) { 
    	this.email = email; 
    	}
    
    public void setMemberSince(LocalDate memberSince) { 
    	this.memberSince = memberSince; 
    	}
}
