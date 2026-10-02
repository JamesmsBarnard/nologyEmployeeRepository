package io.nology.employee.common.dtos;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

public class PageResponse<R> {
    
    public static <T, R> PageResponse<R> assemble(Page<T> data, Function<T,R> mapper)
    {
        int currentPage = data.getNumber() + 1;
        Integer nextPage = currentPage < data.getTotalPages() ? currentPage + 1 : null;
        Integer previousPage = currentPage > 1 ? currentPage - 1 : null;

        return new PageResponse<>(
                currentPage,
                data.getTotalPages(),
                data.getTotalElements(),
                data.getSize(),
                nextPage,
                previousPage,
                data.map(mapper).getContent()
        );
    }

    private int currentPage;
    private int totalPages;
    private long totalResults;
    private int resultsPerPage;
    private Integer nextPage;
    private Integer previousPage;
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public Integer getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(Integer previousPage) {
        this.previousPage = previousPage;
    }

    public List<R> getData() {
        return data;
    }

    public void setData(List<R> data) {
        this.data = data;
    }

    private List<R> data;

    public PageResponse()
    {

    }

    public PageResponse(int currentPage, int totalPages, long totalResults, int resultsPerPage, Integer nextPage, Integer previousPage, List<R> data)
    {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
        this.resultsPerPage = resultsPerPage;
        this.nextPage = nextPage;
        this.previousPage = previousPage;
        this.data = data;
    }
}
