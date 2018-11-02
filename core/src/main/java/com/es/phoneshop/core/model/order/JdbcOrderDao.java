package com.es.phoneshop.core.model.order;

import com.es.phoneshop.core.model.phone.Phone;
import com.es.phoneshop.core.model.phone.PhoneDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcOrderDao implements OrderDao {
    private final static String SQL_INSERT_ORDER_QUERY = "insert into orders values(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String SQL_INSERT_PHONE2ORDER_QUERY = "insert into phone2order values(?, ?, ?)";
    private final static String SQL_FIND_ORDERS_QUERY = "select * from orders";
    private final static String SQL_FIND_ITEMS_QUERY = "select phoneId, quantity from phone2order where orderId = ?";
    private final static String SQL_FIND_ORDER = "select * from orders where id = ?";
    private final static String SQL_CHANGE_STATUS_QUERY = "update orders set statusId = ? where id = ?";
    private final static String ID_LABEL = "id";
    private final static String SUBTOTAL_LABEL = "subtotal";
    private final static String DELIVERY_PRICE_LABEL = "deliveryPrice";
    private final static String TOTAL_PRICE_LABEL = "totalPrice";
    private final static String FIRSTNAME_LABEL = "firstName";
    private final static String LASTNAME_LABEL = "lastName";
    private final static String DELIVERY_ADDRESS_LABEL = "deliveryAddress";
    private final static String CONTACT_PHONE_NUMBER_LABEL = "contactPhoneNo";
    private final static String STATUSID_LABEL = "statusId";
    private final static String QUANTITY_LABEL = "quantity";
    private final static String PHONEID_LABEL = "phoneId";
    private final static String DATE_LABEL = "orderDate";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private PhoneDao phoneDao;

    private void insertIntoOrders(final Order order) {
        jdbcTemplate.update(SQL_INSERT_ORDER_QUERY, new Object[]{order.getSubtotal(), order.getDeliveryPrice(),
                order.getTotalPrice(), order.getFirstName(), order.getLastName(), order.getDeliveryAddress(),
                order.getContactPhoneNo(), (order.getStatus().ordinal() + 1), order.getDate()});
    }

    private void insertIntoPhone2Order(final Order order) {
        for(OrderItem item: order.getOrderItems()) {
            jdbcTemplate.update(SQL_INSERT_PHONE2ORDER_QUERY, new Object[]{order.getId(), item.getPhone().getId(), item.getQuantity()});
        }
    }

    @Transactional
    public void insertOrder(final Order order) {
        insertIntoOrders(order);
        insertIntoPhone2Order(order);
    }

    private void setOrderListPhoneData(List<Order> orderList) {
        for(Order order: orderList) {
            setOrderPhoneData(order);
        }
    }

    private void setOrderPhoneData(Order order) {
        for(OrderItem orderItem: order.getOrderItems()) {
            Optional<Phone> phone = phoneDao.get(orderItem.getPhone().getId());
            if(phone.isPresent()) {
                Phone gotPhone = phone.get();
                gotPhone.setColors(new HashSet<>(phoneDao.getPhoneColors(gotPhone.getId())));
                orderItem.setPhone(gotPhone);
            }
        }
    }

    private void setOrderListItems(List<Order> orderList) {
        for(Order order: orderList) {
            setOrderItems(order);
        }
    }

    private void setOrderItems(Order order) {
        List<OrderItem> orderItemList = jdbcTemplate.query(SQL_FIND_ITEMS_QUERY, new Object[]{order.getId()},
                (ResultSet rs, int rowNumber) -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setQuantity(rs.getLong(QUANTITY_LABEL));
                    orderItem.setPhone(new Phone());
                    orderItem.getPhone().setId(rs.getLong(PHONEID_LABEL));
                    orderItem.setOrder(order);
                    return orderItem;
                });

        order.setOrderItems(orderItemList);
    }

    private List<Order> selectOrders() {
        List<Order> orderList = jdbcTemplate.query(SQL_FIND_ORDERS_QUERY, (ResultSet rs, int rowNumber) -> {
            Order order = new Order();
            order.setId(rs.getLong(ID_LABEL));
            order.setSubtotal(rs.getBigDecimal(SUBTOTAL_LABEL));
            order.setDeliveryPrice(rs.getBigDecimal(DELIVERY_PRICE_LABEL));
            order.setTotalPrice(rs.getBigDecimal(TOTAL_PRICE_LABEL));
            order.setFirstName(rs.getString(FIRSTNAME_LABEL));
            order.setLastName(rs.getString(LASTNAME_LABEL));
            order.setDeliveryAddress(rs.getString(DELIVERY_ADDRESS_LABEL));
            order.setContactPhoneNo(rs.getString(CONTACT_PHONE_NUMBER_LABEL));
            order.setStatus(OrderStatus.values()[rs.getInt(STATUSID_LABEL)-1]);
            order.setDate(rs.getDate(DATE_LABEL));
            return order;
        });

        return orderList;
    }

    private Optional<Order> selectOrderById(Long id) {
        try {
            Order optionalOrder = jdbcTemplate.queryForObject(SQL_FIND_ORDER, new Object[]{id}, (ResultSet rs, int rowNumber) -> {
                Order order = new Order();
                order.setId(rs.getLong(ID_LABEL));
                order.setSubtotal(rs.getBigDecimal(SUBTOTAL_LABEL));
                order.setDeliveryPrice(rs.getBigDecimal(DELIVERY_PRICE_LABEL));
                order.setTotalPrice(rs.getBigDecimal(TOTAL_PRICE_LABEL));
                order.setFirstName(rs.getString(FIRSTNAME_LABEL));
                order.setLastName(rs.getString(LASTNAME_LABEL));
                order.setDeliveryAddress(rs.getString(DELIVERY_ADDRESS_LABEL));
                order.setContactPhoneNo(rs.getString(CONTACT_PHONE_NUMBER_LABEL));
                order.setStatus(OrderStatus.values()[rs.getInt(STATUSID_LABEL) - 1]);
                order.setDate(rs.getDate(DATE_LABEL));
                return order;
            });
            return Optional.of(optionalOrder);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public List<Order> findAll() {
        List<Order> orderList = selectOrders();
        setOrderListItems(orderList);
        setOrderListPhoneData(orderList);
        return orderList;
    }

    @Transactional
    public Optional<Order> findById(Long orderId) {
        Optional<Order> order = selectOrderById(orderId);
        if(order.isPresent()) {
            setOrderItems(order.get());
            setOrderPhoneData(order.get());
            return order;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void changeStatus(Long orderId, Integer status) {
        jdbcTemplate.update(SQL_CHANGE_STATUS_QUERY, new Object[]{status, orderId});
    }
}
