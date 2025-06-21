package com.vasd.medical_service.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PaginatedResponse<T> {

    private List<T> items;
    private int page;
    private int size;
    private long totalItems;
    private int totalPages;

    public PaginatedResponse(Page<T> pageData) {
        this.items = pageData.getContent();
        this.page = pageData.getNumber();
        this.size = pageData.getSize();
        this.totalItems = pageData.getTotalElements();
        this.totalPages = pageData.getTotalPages();
    }
}
