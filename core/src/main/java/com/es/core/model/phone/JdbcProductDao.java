package com.es.core.model.phone;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcProductDao implements PhoneDao, InitializingBean {
    @Resource
    private JdbcTemplate jdbcTemplate;
    private Map<Long, Color> colors;
    private BeanPropertyRowMapper<Phone> phoneBeanPropertyRowMapper;
    private PreparedStatement statementForGettingAllColors;
    private PreparedStatement statementForGettingPhoneById;
    private PreparedStatement statementForGettingLastPhoneId;
    private PreparedStatement statementForAddingPhone;
    private PreparedStatement statementForBindingPhoneAndColor;
    private PreparedStatement statementForGettingPhonesByOffsetAndLimit;
    private PreparedStatement statementForDeletingPhoneById;
    private PreparedStatement statementForGettingColorsByPhoneId;

    @Override
    public void afterPropertiesSet() {
        phoneBeanPropertyRowMapper = new BeanPropertyRowMapper<>(Phone.class);
        colors = new HashMap<>();
        try {
            setValuesForStatements();
            ResultSet resultSet = statementForGettingAllColors.executeQuery();
            while (resultSet.next()) {
                Long colorId = resultSet.getLong("id");
                String colorCode = resultSet.getString("code");
                colors.put(colorId, new Color(colorId, colorCode));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Optional<Phone> get(Long key) {
        try {
            statementForGettingPhoneById.setLong(1, key);
            ResultSet resultSet = statementForGettingPhoneById.executeQuery();
            resultSet.next();
            Optional<Phone> phone = Optional.ofNullable(phoneBeanPropertyRowMapper.mapRow(resultSet, 0));
            phone.ifPresent(this::setColorsForPhone);
            return phone;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Optional.empty();
        }
    }

    public void save(Phone phone) {
        try {
            checkPhoneIdAndSetIfNeeded(phone);
            setParametersForStatementAddingPhoneAndExecute(phone);
            bindPhoneAndColor(phone);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public List<Phone> findAll(int offset, int limit) {
        List<Phone> phones = new ArrayList<>();
        try {
            statementForGettingPhonesByOffsetAndLimit.setInt(1, offset);
            statementForGettingPhonesByOffsetAndLimit.setInt(2, limit);
            ResultSet resultSet = statementForGettingPhonesByOffsetAndLimit.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                phones.add(phoneBeanPropertyRowMapper.mapRow(resultSet, i++));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        for (Phone phone : phones) {
            setColorsForPhone(phone);
        }
        return phones;
    }

    private void delete(Long key) {
        try {
            statementForDeletingPhoneById.setLong(1, key);
            statementForDeletingPhoneById.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void setValuesForStatements() throws SQLException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        statementForGettingAllColors = connection.prepareStatement("select * from colors");
        statementForGettingPhoneById = connection.prepareStatement("select * from phones where phones.id=?");
        statementForGettingLastPhoneId = connection.prepareStatement("select MAX(id) as lastId from phones");
        statementForBindingPhoneAndColor = connection.prepareStatement("insert into phone2color values (?,?)");
        statementForDeletingPhoneById = connection.prepareStatement("delete from phones where id = ?");
        statementForGettingColorsByPhoneId = connection.prepareStatement("select * from phone2color where phone2color.phoneId = ?");
        statementForGettingPhonesByOffsetAndLimit = connection.prepareStatement("select * from phones offset ? limit ?");
        statementForAddingPhone = connection.prepareStatement("insert into phones values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    }

    private void setColorsForPhone(Phone phone) {
        phone.setColors(new HashSet<>());
        try {
            statementForGettingColorsByPhoneId.setLong(1, phone.getId());
            ResultSet resultSet = statementForGettingColorsByPhoneId.executeQuery();
            while (resultSet.next()) {
                phone.getColors().add(colors.get(resultSet.getLong("colorId")));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void checkPhoneIdAndSetIfNeeded(Phone phone) throws SQLException {
        if (phone.getId() == null) {
            ResultSet resultSet = statementForGettingLastPhoneId.executeQuery();
            resultSet.next();
            phone.setId(resultSet.getLong("lastId") + 1);
        } else {
            delete(phone.getId());
        }
    }

    private void bindPhoneAndColor(Phone phone) throws SQLException {
        if (!phone.getColors().equals(Collections.EMPTY_SET)) {
            for (Color color : phone.getColors()) {
                statementForBindingPhoneAndColor.setLong(1, phone.getId());
                statementForBindingPhoneAndColor.setLong(2, color.getId());
                statementForBindingPhoneAndColor.executeUpdate();
            }
        }
    }

    private void setParametersForStatementAddingPhoneAndExecute(Phone phone) throws SQLException {
        Object[] phoneParameters = getPhoneFieldsValues(phone);
        for (int i = 0; i < phoneParameters.length; i++) {
            statementForAddingPhone.setObject(i + 1, phoneParameters[i]);
        }
        statementForAddingPhone.executeUpdate();
    }

    private Object[] getPhoneFieldsValues(Phone phone) {
        try {
            Field[] fields = phone.getClass().getDeclaredFields();
            List<Field> fieldsList = new ArrayList<>(Arrays.asList(fields));
            for (Field field : fields) {
                if (field.getName().equals("colors")) {
                    fieldsList.remove(field);
                    break;
                }
            }
            Object[] values = new Object[fieldsList.size()];
            for (int i = 0; i < fieldsList.size(); i++) {
                Field field = phone.getClass().getDeclaredField(fieldsList.get(i).getName());
                field.setAccessible(true);
                values[i] = field.get(phone);
            }
            return values;
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
