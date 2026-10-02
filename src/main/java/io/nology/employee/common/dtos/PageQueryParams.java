package io.nology.employee.common.dtos;


import org.springframework.data.domain.Page;

import io.nology.employee.common.exceptions.UnprocessableContentException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class PageQueryParams {
    @Min(1)
    private Integer page = 1;

    @Min(1)
    @Max(20)
    private Integer size = 10;

    private String search;

    public PageQueryParams() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public <T> void validatePageNumber(Page<T> data) {
        if (page > 1 && data.getTotalPages() < page) {
            throw new UnprocessableContentException(
                    "Page " + page + " is too high. Total pages is " + data.getTotalPages());
        }
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
