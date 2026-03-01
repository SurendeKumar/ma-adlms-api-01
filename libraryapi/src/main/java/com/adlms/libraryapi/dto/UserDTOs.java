package com.adlms.libraryapi.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class UserDTOs {

	
	// 1. user response
    public record UserResponse(
            Long id,
            String name,
            String email,
            @JsonFormat(pattern = "yyyy-MM-dd") LocalDate memberSince,
            long totalBorrowRecords
    ) { }
    
    // 2. create user request
    public record CreateUserRequest(
            @NotBlank(message = "name is required") String name,
            @NotBlank(message = "email is required") @Email(message = "email must be valid") String email,
            @NotNull(message = "memberSince is required")
            @JsonFormat(pattern = "yyyy-MM-dd") LocalDate memberSince
    ) { }

    
    // 3. update user request
    public record UpdateUserRequest(
            @NotBlank(message = "name is required") String name,
            @NotBlank(message = "email is required") @Email(message = "email must be valid") String email,
            @NotNull(message = "memberSince is required")
            @JsonFormat(pattern = "yyyy-MM-dd") LocalDate memberSince
    ) { }
}