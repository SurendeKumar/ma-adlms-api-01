package com.adlms.libraryapi.service;

import com.adlms.libraryapi.dto.UserDTOs;
import com.adlms.libraryapi.entity.LibraryUser;
import com.adlms.libraryapi.exception.NotFoundException;
import com.adlms.libraryapi.mapper.UserMapper;
import com.adlms.libraryapi.repository.LibraryUserRepository;
import org.springframework.stereotype.Service;
import com.adlms.libraryapi.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class UserService {

    private final LibraryUserRepository repository;

    public UserService(LibraryUserRepository repository) {
        this.repository = repository;
    }

    public UserDTOs.UserResponse create(UserDTOs.CreateUserRequest request) {
        LibraryUser user = new LibraryUser(
                request.name(),
                request.email(),
                request.memberSince()
        );

        return UserMapper.toResponse(repository.save(user));
    }

    
    // getAll() version 1
//    public List<UserDTOs.UserResponse> getAll() {
//        return repository.findAll()
//                .stream()
//                .map(UserMapper::toResponse)
//                .collect(Collectors.toList());
//    }
    
    // Pagination version 
    public PageResponse<UserDTOs.UserResponse> getAll(Pageable pageable) {
        Page<UserDTOs.UserResponse> page = repository.findAll(pageable)
                .map(UserMapper::toResponse);

        return PageResponse.from(page);
    }
    

    public UserDTOs.UserResponse getById(Long id) {
        LibraryUser user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));

        return UserMapper.toResponse(user);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("User not found: " + id);
        }
        repository.deleteById(id);
    }
}