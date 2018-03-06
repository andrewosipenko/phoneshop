package com.es.core.model.order;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class JdbcOrderDao implements OrderDao {

    private final static String SELECT_PHONE_QUERY = "select orders.id AS orderId, subtotal, deliveryPrice," +
            "totalPrice, firstName, lastName, deliveryAddress, contactPhoneNo," +
            "orderItems.id AS orderItemId, quantity," +
            "phones.id AS phoneId, brand, model, " +
            "price, displaySizeInches, weightGr, lengthMm, widthMm, " +
            "heightMm, announced, deviceType, os, displayResolution, " +
            "pixelDensity, displayTechnology, backCameraMegapixels, " +
            "frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, " +
            "talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, " +
            "description, colors.id AS colorId, colors.code AS colorCode from orders " +
            "left join orderItems on orders.id = orderItems.orderId " +
            "left join phones on phones.id = orderId.phoneId " +
            "left join phone2color on phones.id = phone2color.phoneId " +
            "left join colors on colors.id = phone2color.colorId " +
            "where phones.id = ?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Order> getOrderById(Long id) {
        //TODO
        return null;
    }

    @Override
    public List<Order> findAll(int offset, int limit) {
        //TODO
        return null;
    }

    @Override
    public int orderCount() {
        //TODO
        return 0;
    }

    @Override
    public void save(Order order) {
        //TODO
    }
}
