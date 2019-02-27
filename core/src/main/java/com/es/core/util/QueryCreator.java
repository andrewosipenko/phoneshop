package com.es.core.util;

public final class QueryCreator {
    private final static String REPLACE_REGEX = "var";

    private QueryCreator(){

    }

    public static String addSortParametersToQuery(String query, String sort, String order) {
        String dbQuery = query.replaceFirst(REPLACE_REGEX, sort);
        return dbQuery.replaceFirst(REPLACE_REGEX, order);
    }
}
