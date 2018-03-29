package com.es.core.model.phone.dao.util;

public class PhoneDaoSelector {
    private Integer offset;
    private Integer limit;
    private SortBy sortBy;
    private String search;

    public PhoneDaoSelector offset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public PhoneDaoSelector limit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public PhoneDaoSelector sortedBy(SortBy sortBy) {
        this.sortBy = sortBy;
        return this;
    }

    public PhoneDaoSelector searching(String search) {
        this.search = (search == null) ? null : search.toLowerCase();
        return this;
    }

    public String getSelectQuery() {
        return "SELECT * FROM phones LEFT JOIN stocks ON id = phoneId WHERE stock > 0 AND price IS NOT NULL"
                + searchSuffix() + sortSuffix() + offsetSuffix() + limitSuffix();
    }

    public String getCountQuery() {
        return "SELECT COUNT(*) FROM phones LEFT JOIN stocks ON id = phoneId WHERE stock > 0 AND price IS NOT NULL" + searchSuffix();
    }

    private String searchSuffix() {
        if (search == null)
            return "";
        return " AND (LOWER(model) LIKE '%" + search + "%' OR LOWER(brand) LIKE '%" + search + "%')";
    }

    private String offsetSuffix() {
        if (offset == null)
            return "";
        return " OFFSET " + offset;
    }

    private String limitSuffix() {
        if (limit == null)
            return "";
        return " LIMIT " + limit;
    }

    private String sortSuffix() {
        if (sortBy == null)
            return "";
        return " ORDER BY " + sortBy.getSql();
    }
}
