package com.es.core.model.phone;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository("JdbcProductDao")
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Optional<Phone> get(final Long key) {
        try {
            statementForGettingPhoneById.setLong(1, key);
            ResultSet resultSet = statementForGettingPhoneById.executeQuery();
            resultSet.next();
            Optional<Phone> phone = Optional.ofNullable(phoneBeanPropertyRowMapper.mapRow(resultSet, 0));
            if (phone.isPresent()) {
                setColorsForPhone(phone.get());
            }
            return phone;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    public void save(final Phone phone) {
        if (phone.getId() == null) {
            try {
                ResultSet resultSet = statementForGettingLastPhoneId.executeQuery();
                resultSet.next();
                phone.setId(resultSet.getLong("lastId") + 1);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            delete(phone.getId());
        }
        String sqlInsertion = "insert into phones values (" + Stream.of(phone.getId(), phone.getBrand(), phone.getModel(), phone.getPrice(), phone.getDisplaySizeInches(), phone.getWeightGr(), phone.getLengthMm(), phone.getWidthMm(), phone.getHeightMm(),
                phone.getAnnounced(), phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(), phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(), phone.getFrontCameraMegapixels(), phone.getRamGb(),
                phone.getInternalStorageGb(), phone.getBatteryCapacityMah(), phone.getTalkTimeHours(), phone.getStandByTimeHours(), phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl())
                .map((s) -> s != null ? "String Date Long".contains(s.getClass().getSimpleName()) ? "'" + s + "', " : s + ", " : s + ", ")
                .collect(Collectors.joining())
                .concat(phone.getDescription() + ");");
        jdbcTemplate.execute(sqlInsertion);
        if (!phone.getColors().equals(Collections.EMPTY_SET)) {
            try {
                for (Color color : phone.getColors()) {
                    statementForBindingPhoneAndColor.setLong(1, phone.getId());
                    statementForBindingPhoneAndColor.setLong(2, color.getId());
                    statementForBindingPhoneAndColor.executeUpdate();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        for (Phone phone : phones) {
            setColorsForPhone(phone);
        }
        return phones;
    }

    public void delete(final Long key) {
        try {
            statementForDeletingPhoneById.setLong(1, key);
            statementForDeletingPhoneById.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void setValuesForStatements() throws SQLException{
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        statementForGettingAllColors = connection.prepareStatement("select * from colors");
        statementForGettingPhoneById = connection.prepareStatement("select * from phones where phones.id=?");
        statementForGettingLastPhoneId = connection.prepareStatement("select MAX(id) as lastId from phones");
        statementForBindingPhoneAndColor = connection.prepareStatement("insert into phone2color values (?,?)");
        statementForDeletingPhoneById = connection.prepareStatement("delete from phones where id = ?");
        statementForGettingColorsByPhoneId = connection.prepareStatement("select * from phone2color where phone2color.phoneId = ?");
        statementForGettingPhonesByOffsetAndLimit = connection.prepareStatement("select * from phones offset ? limit ?");
        //statementForAddingPhone = connection.prepareStatement("insert into phones values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    }

    private void setColorsForPhone(Phone phone) {
        phone.setColors(new HashSet<>());
        try {
            statementForGettingColorsByPhoneId.setLong(1, phone.getId());
            ResultSet resultSet = statementForGettingColorsByPhoneId.executeQuery();
            while (resultSet.next()) {
                phone.getColors().add(colors.get(resultSet.getLong("colorId")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
