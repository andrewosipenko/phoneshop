package com.es.core.model.phone;

import com.es.core.model.AbstractPhoneResultSetExctractor;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PhoneListResultSetExtractor extends AbstractPhoneResultSetExctractor implements ResultSetExtractor<List<Phone>> {

    private static PhoneListResultSetExtractor instanse = new PhoneListResultSetExtractor();

    public static PhoneListResultSetExtractor getInstanse() {
        return instanse;
    }

    private PhoneListResultSetExtractor() {
    }

    @Override
    public List<Phone> extractData(ResultSet rs) throws SQLException {

        Map<Long, Phone> phoneMap = new HashMap<>();
        List<Phone> phoneList = new ArrayList<>();

        while (rs.next()) {
            Phone changePhone;
            Long phoneId = rs.getLong("phoneId");
            if (!phoneMap.containsKey(phoneId)) {
                changePhone = readPropertiesToPhone(rs);
                phoneMap.put(phoneId, changePhone);
                phoneList.add(changePhone);
            } else {
                changePhone = phoneMap.get(phoneId);
            }
            addColor(changePhone, rs);
        }

        return phoneList;
    }
}
