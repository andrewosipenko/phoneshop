package com.es.core.dao.order;

import com.es.core.extractor.order.OrderSetExtractor;
import com.es.core.extractor.order.OrdersSetExtractor;
import com.es.core.model.order.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class JdbcOrderDaoImpl implements OrderDao {
    private static final String REPLACE_REGEX = "var";
    private static final String QUERY_TO_GET_ORDER_BY_SECURE_ID =
            "select orders.*, phones.*, item.quantity from " +
                    "(select * from orders where secureId = ?) orders " +
                    "left join orderItems item on item.orderId = orders.orderId " +
                    "left join phones on phones.id = item.phoneId";
    private static final String QUERY_TO_GET_ORDER_BY_ID =
            "select orders.*, phones.*, item.quantity from " +
                    "(select * from orders where orderId = ?) orders " +
                    "left join orderItems item on item.orderId = orders.orderId " +
                    "left join phones on phones.id = item.phoneId";
    private static final String QUERY_TO_FIND_ALL_ORDERS =
            "select orders.*, phones.*, item.quantity from orders " +
                    "left join orderItems item on item.orderId = orders.orderId " +
                    "left join phones on phones.id = item.phoneId";
    private static final String QUERY_TO_SAVE_ORDER =
            "insert into orders (secureId, firstName, lastName, subtotal, " +
                    "deliveryPrice, totalPrice, deliveryAddress, additionalInfo, contactPhoneNo) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String QUERY_TO_FIND_ORDER_ID =
            "select orderId from orders where secureId = ?";
    private static final String QUERY_TO_UPDATE_ORDER_STATUS =
            "update orders set orderStatus = ? where orderId = ?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private OrdersSetExtractor ordersSetExtractor;

    @Resource
    private OrderSetExtractor orderSetExtractor;

    @Override
    public Order findOrderBySecureId(String secureId) {
        return jdbcTemplate.query(QUERY_TO_GET_ORDER_BY_SECURE_ID, orderSetExtractor, secureId);
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query(QUERY_TO_FIND_ALL_ORDERS, ordersSetExtractor);
    }

    @Override
    public void save(Order order) {
        jdbcTemplate.update(QUERY_TO_SAVE_ORDER,
                order.getSecureId(),
                order.getFirstName(),
                order.getLastName(),
                order.getSubtotal(),
                order.getDeliveryPrice(),
                order.getTotalPrice(),
                order.getDeliveryAddress(),
                order.getAdditionalInfo(),
                order.getContactPhoneNo());
    }

    @Override
    public Order findOrderById(Long id) {
        return jdbcTemplate.query(QUERY_TO_GET_ORDER_BY_ID, orderSetExtractor, id);
    }

    @Override
    public Long findOrderIdBySecureId(String secureId) {
        return jdbcTemplate.queryForObject(QUERY_TO_FIND_ORDER_ID, Long.class, secureId);
    }

    @Override
    public void updateOrderStatus(Long id, String status) {
        jdbcTemplate.update(QUERY_TO_UPDATE_ORDER_STATUS, status, id);
    }
}
