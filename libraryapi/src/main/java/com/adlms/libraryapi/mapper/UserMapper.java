package com.adlms.libraryapi.mapper;

import com.adlms.libraryapi.dto.UserDTOs;
import com.adlms.libraryapi.entity.LibraryUser;

public class UserMapper {

    public static UserDTOs.UserResponse toResponse(LibraryUser user) {
        long totalBorrowRecords = user.getBorrowRecords() == null
                ? 0
                : user.getBorrowRecords().size();

        return new UserDTOs.UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getMemberSince(),
                totalBorrowRecords
        );
    }
}