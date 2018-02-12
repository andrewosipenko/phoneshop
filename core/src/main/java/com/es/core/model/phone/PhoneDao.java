package com.es.core.model.phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    List<Phone> findAll(int offset, int limit);
    List<Phone> findAllInOrder(OrderBy orderBy, int offset, int limit);
    int phonesCount();
    List<Phone> getPhonesByQuery(String query, OrderBy orderBy, int offset, int limit);
    int phonesCountByQuery(String query);

    enum OrderBy {
        BRAND("brand"), BRAND_DESC("brand DESC"),
        MODEL("model"), MODEL_DESC("model DESC"),
        DISPLAY_SIZE("displaySizeInches"), DISPLAY_SIZE_DESC ("displaySizeInches DESC"),
        PRICE("price"), PRICE_DESC("price DESC");

        private String sqlCommand;

        OrderBy(String sqlCommand){
            this.sqlCommand = sqlCommand;
        }

        public String getSqlCommand() {
            return sqlCommand;
        }
    }
}
