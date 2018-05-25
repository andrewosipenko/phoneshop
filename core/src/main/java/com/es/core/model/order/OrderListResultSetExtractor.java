package com.es.core.model.order;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class OrderListResultSetExtractor implements ResultSetExtractor<List<Order>> {

    @Override
    public List<Order> extractData(ResultSet rs) throws SQLException {
        List<Order> orders = new ArrayList<>();
        Map<String, Order> ordersMap = new HashMap<>();
        Map<Long, OrderItem> orderItemsMap = new HashMap<>();
        while (rs.next()) {
            String orderId = rs.getString("orderId");
            Order order = ordersMap.get(orderId);
            if (order == null) {
                order = setOrderData(rs);
                orders.add(order);
                ordersMap.put(orderId, order);
            }
            addOrderItem(order, orderItemsMap, rs);
        }
        return orders;
    }

    private Order setOrderData(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getString("orderId"));
        order.setOrderItems(new ArrayList<>());
        order.setSubtotal(rs.getBigDecimal("subtotal"));
        order.setDeliveryPrice(rs.getBigDecimal("deliveryPrice"));
        order.setTotalPrice(rs.getBigDecimal("totalPrice"));
        order.setFirstName(rs.getString("firstName"));
        order.setLastName(rs.getString("lastName"));
        order.setDeliveryAddress(rs.getString("deliveryAddress"));
        order.setContactPhoneNo(rs.getString("contactPhoneNo"));
        order.setStatus(OrderStatus.valueOf(rs.getString("status")));
        order.setAdditionalInfo(rs.getString("additionalInfo"));
        return order;
    }

    private void addOrderItem(Order order, Map<Long, OrderItem> orderItemMap, ResultSet rs) throws SQLException {
        Long orderItemId = rs.getLong("orderItemId");
        OrderItem orderItem = orderItemMap.get(orderItemId);
        if (orderItem == null) {
            orderItem = setOrderItemData(order, rs);
            order.getOrderItems().add(orderItem);
            orderItemMap.put(orderItemId, orderItem);
        }
        addColor(orderItem.getPhone(), rs);
    }

    private OrderItem setOrderItemData(Order order, ResultSet rs) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(rs.getLong("orderItemId"));
        orderItem.setOrder(order);
        Phone phone = setPhoneData(rs);
        orderItem.setPhone(phone);
        orderItem.setQuantity(rs.getLong("quantity"));
        return orderItem;
    }

    private Phone setPhoneData(ResultSet rs) throws SQLException {
        Phone phone = new Phone();
        phone.setId(rs.getLong("phoneId"));
        phone.setBrand(rs.getString("brand"));
        phone.setModel(rs.getString("model"));
        phone.setPrice(rs.getBigDecimal("price"));
        phone.setDisplaySizeInches(rs.getBigDecimal("displaySizeInches"));
        phone.setWeightGr(rs.getInt("weightGr"));
        phone.setLengthMm(rs.getBigDecimal("lengthMm"));
        phone.setWidthMm(rs.getBigDecimal("widthMm"));
        phone.setHeightMm(rs.getBigDecimal("heightMm"));
        phone.setAnnounced(rs.getDate("announced"));
        phone.setDeviceType(rs.getString("deviceType"));
        phone.setOs(rs.getString("os"));
        phone.setDisplayResolution(rs.getString("displayResolution"));
        phone.setPixelDensity(rs.getInt("pixelDensity"));
        phone.setDisplayTechnology(rs.getString("displayTechnology"));
        phone.setBackCameraMegapixels(rs.getBigDecimal("backCameraMegapixels"));
        phone.setFrontCameraMegapixels(rs.getBigDecimal("frontCameraMegapixels"));
        phone.setRamGb(rs.getBigDecimal("ramGb"));
        phone.setInternalStorageGb(rs.getBigDecimal("internalStorageGb"));
        phone.setBatteryCapacityMah(rs.getInt("batteryCapacityMah"));
        phone.setTalkTimeHours(rs.getBigDecimal("talkTimeHours"));
        phone.setStandByTimeHours(rs.getBigDecimal("standByTimeHours"));
        phone.setBluetooth(rs.getString("bluetooth"));
        phone.setImageUrl(rs.getString("imageUrl"));
        phone.setDescription(rs.getString("description"));
        phone.setColors(new HashSet<>());
        return phone;
    }

    private void addColor(Phone phone, ResultSet rs) throws SQLException {
        Long colorId = rs.getLong("colorId");
            Color newColor = new Color();
            newColor.setCode(rs.getString("colorCode"));
            newColor.setId(colorId);
            phone.getColors().add(newColor);
    }
}
