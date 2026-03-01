package com.adlms.libraryapi.repository;

import com.adlms.libraryapi.entity.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryUserRepository extends JpaRepository<LibraryUser, Long> {
}