package com.adlms.libraryapi.controller;

import com.adlms.libraryapi.dto.UserDTOs;
import com.adlms.libraryapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.adlms.libraryapi.dto.PageResponse;
import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserDTOs.UserResponse create(
            @Valid @RequestBody UserDTOs.CreateUserRequest request
    ) {
        return service.create(request);
    }

    // getAll version 1
//    @GetMapping
//    public List<UserDTOs.UserResponse> getAll() {
//        return service.getAll();
//    }
    
    @GetMapping
    public PageResponse<UserDTOs.UserResponse> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public UserDTOs.UserResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}