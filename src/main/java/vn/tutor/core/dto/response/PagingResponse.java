package vn.tutor.core.dto.response;

import java.util.List;

public record PagingResponse<T>(
    List<T> items,
    int currentPage,
    int totalPages,
    long totalItems) {

}
