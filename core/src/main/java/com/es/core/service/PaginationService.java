package com.es.core.service;

import java.util.List;

public interface PaginationService {
    List<Integer> getPaginationList(int pageNumber);
    Integer getOffset(int pageNumber);
}
