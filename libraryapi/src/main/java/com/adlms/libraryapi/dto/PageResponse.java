//package com.adlms.libraryapi.dto;
//import org.springframework.data.domain.Page;
//import java.util.List;
//
//
//public record PageResponse<T>(
//        List<T> content,
//        int page,
//        int size,
//        long totalElements,
//        int totalPages
//) {
//    public static <T> PageResponse<T> from(Page<T> p) {
//        return new PageResponse<>(
//                p.getContent(),
//                p.getNumber(),
//                p.getSize(),
//                p.getTotalElements(),
//                p.getTotalPages()
//        );
//    }
//}



// New version for CI/CD pipeline assignment 2
package com.adlms.libraryapi.dto;

import org.springframework.data.domain.Page;
import java.util.List;

public class PageResponse<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public PageResponse(List<T> content, int page, int size, long totalElements, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static <T> PageResponse<T> from(Page<T> p) {
        return new PageResponse<>(
                p.getContent(),
                p.getNumber(),
                p.getSize(),
                p.getTotalElements(),
                p.getTotalPages()
        );
    }

    public List<T> getContent() {
        return content;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }
}