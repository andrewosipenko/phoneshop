package com.es.core.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.PhoneService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JdbcOrderDao implements OrderDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private PhoneService phoneService;

    private final static String SQL_ADD_ORDER_ITEM =
            "INSERT INTO orderItems (orderId, phoneId, quantity) " +
            "VALUES (?, ?, ?)";

    private final static String SQL_GET_ORDER_ID_BY_UUID = "SELECT orders.id FROM orders WHERE orders.uuid = ?";

    private final static String SQL_GET_ITEMS_FOR_ORDER =
            "SELECT * " +
            "FROM orderItems " +
            "WHERE orderItems.orderId = ? ";

    private final static String SQL_ADD_ORDER =
            "INSERT INTO orders (uuid, subtotal, deliveryPrice, totalPrice, firstName, lastName, " +
                    "deliveryAddress, contactPhoneNo, additionalInfo, status, placementDate) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final static String SQL_GET_ORDER_BY_ID = "SELECT * FROM orders WHERE orders.id = ?";

    private final static String SQL_GET_ORDER_BY_UUID = "SELECT * FROM orders WHERE orders.uuid = ?";

    private final static String SQL_GET_ORDERS = "SELECT * FROM orders";

    private final static String SQL_UPDATE_ORDER_STATUS = "UPDATE orders SET orders.status = ?";

    private void addOrder(Order order){
        jdbcTemplate.update(
                SQL_ADD_ORDER,
                order.getOrderUUID(),
                order.getSubtotal(),
                order.getDeliveryPrice(),
                order.getTotalPrice(),
                order.getFirstName(),
                order.getLastName(),
                order.getDeliveryAddress(),
                order.getContactPhoneNo(),
                order.getAdditionalInfo(),
                order.getStatus().name(),
                order.getPlacementDate()
        );
    }

    private void addOrderItems(Order order){
        List<Object[]> batchArgs = order.getOrderItems().stream()
                .map((item)->new Object[]{
                        order.getId(),
                        item.getPhone().getId(),
                        item.getQuantity()})
                .collect(Collectors.toList());

        jdbcTemplate.batchUpdate(SQL_ADD_ORDER_ITEM, batchArgs);
    }

    private List<OrderItem> queryOrderItems(Order order) {
        return jdbcTemplate.query(
                SQL_GET_ITEMS_FOR_ORDER,
                (rs, rowNum) -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setQuantity(rs.getLong("quantity"));
                    item.setPhone(phoneService.getById(rs.getLong("phoneId")));
                    item.setId(rs.getLong("id"));
                    return item;
                },
                order.getId());
    }

    private Long queryOrderId(Order order){
        return jdbcTemplate.queryForObject(SQL_GET_ORDER_ID_BY_UUID, Long.class, order.getOrderUUID());
    }

    @Override
    public void placeOrder(Order order) {
        //add order
        addOrder(order);
        order.setId(queryOrderId(order));
        //add order items
        addOrderItems(order);
    }

    @Override
    public void deliverOrder(Order order) {
        jdbcTemplate.update(SQL_UPDATE_ORDER_STATUS, OrderStatus.DELIVERED.name());
    }

    @Override
    public void rejectOrder(Order order) {
        jdbcTemplate.update(SQL_UPDATE_ORDER_STATUS, OrderStatus.REJECTED.name());
    }


    @Override
    public Optional<Order> getOrderById(Long id) {
        List<Order> result = jdbcTemplate.query(SQL_GET_ORDER_BY_ID, new BeanPropertyRowMapper<>(Order.class), id);
        return processOrderQueryResult(result);
    }

    @Override
    public Optional<Order> getOrderByUUID(UUID uuid) {
        List<Order> result = jdbcTemplate.query(SQL_GET_ORDER_BY_UUID, new BeanPropertyRowMapper<>(Order.class), uuid);
        return processOrderQueryResult(result);
    }

    private Optional<Order> processOrderQueryResult(List<Order> result){
        if(result.isEmpty()){
            return Optional.empty();
        }
        Order order = result.get(0);
        order.setOrderItems(queryOrderItems(order));

        return Optional.of(order);
    }

    @Override
    public List<Order> getOrders() {
        return jdbcTemplate.query(SQL_GET_ORDERS, new BeanPropertyRowMapper<>(Order.class));
    }
}
