package com.adlms.libraryapi.repository;

import com.adlms.libraryapi.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}