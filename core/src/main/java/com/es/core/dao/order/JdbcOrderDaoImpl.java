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
    private static final String QUERY_TO_GET_ORDER_BY_KEY =
            "select orders.*, phones.*, item.quantity from " +
                    "(select * from orders where secureId = ?) orders " +
                    "left join orderItems item on item.orderId = orders.orderId " +
                    "left join phones on phones.id = item.phoneId";
    private static final String QUERY_TO_FIND_ALL_ORDERS =
            "select orders.*, phones.*, item.quantity from orders " +
                    "left join orderItems item on item.orderId = orders.orderId " +
                    "left join phones on phones.id = item.phoneId";
    private final static String QUERY_TO_SAVE_ORDER =
            "insert into orders (secureId, firstName, lastName, subtotal, " +
                    "deliveryPrice, totalPrice, deliveryAddress, additionalInfo, contactPhoneNo) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String QUERY_TO_FIND_ORDER_ID =
            "select orderId from orders where secureId = ?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private OrdersSetExtractor ordersSetExtractor;

    @Resource
    private OrderSetExtractor orderSetExtractor;

    @Override
    public Order findOrder(String secureId) {
        return jdbcTemplate.query(QUERY_TO_GET_ORDER_BY_KEY, orderSetExtractor, secureId);
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
    public Long findOrderIdBySecureId(String secureId) {
        return jdbcTemplate.queryForObject(QUERY_TO_FIND_ORDER_ID, Long.class, secureId);
    }
}
