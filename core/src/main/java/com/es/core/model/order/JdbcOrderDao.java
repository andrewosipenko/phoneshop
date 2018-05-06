package com.es.core.model.order;

import com.es.core.order.EmptyOrderListException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.es.core.model.order.OrderDaoQueries.FIND_ALL_QUERY;
import static com.es.core.model.order.OrderDaoQueries.GET_BY_KEY_QUERY;
import static com.es.core.model.order.OrderDaoQueries.SELECT_ORDER_COUNT;

@Component
public class JdbcOrderDao implements OrderDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertOrder;
    private SimpleJdbcInsert insertOrderItems;

    @PostConstruct
    public void initSimpleJdbcInsert() {
        this.insertOrder = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");

        this.insertOrderItems = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("orderItems")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(SELECT_ORDER_COUNT, Integer.class);
    }

    @Override
    public Optional<Order> get(Long key) {
        List<Order> orders = jdbcTemplate.query(GET_BY_KEY_QUERY, new OrderListResultSetExtractor(), key);
        if(orders.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(orders.get(0));
    }

    @Override
    public List<Order> findAll(int offset, int limit) {
        return jdbcTemplate.query(FIND_ALL_QUERY, new OrderListResultSetExtractor(), offset, limit);
    }

    @Override
    public void save(Order order) throws EmptyOrderListException {
        if(order.getOrderItems().size() == 0)
            throw new EmptyOrderListException();
        SqlParameterSource parameters = getParameters(order);
        Long newId = insertOrder.executeAndReturnKey(parameters).longValue();
        order.setId(newId);
        insertOrderItems(order.getOrderItems());
    }

    private void insertOrderItems(List<OrderItem> orderItems) {
            for (OrderItem orderItem : orderItems) {
                SqlParameterSource parameters;
                parameters = getParameters(orderItem);
                long newId = insertOrderItems.executeAndReturnKey(parameters).longValue();
                orderItem.setId(newId);
            }
    }

    private SqlParameterSource getParameters(Order order) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("subtotal", order.getSubtotal());
        mapSqlParameterSource.addValue("deliveryPrice", order.getDeliveryPrice());
        mapSqlParameterSource.addValue("totalPrice", order.getTotalPrice());
        mapSqlParameterSource.addValue("firstName", order.getFirstName());
        mapSqlParameterSource.addValue("lastName", order.getLastName());
        mapSqlParameterSource.addValue("deliveryAddress", order.getDeliveryAddress());
        mapSqlParameterSource.addValue("contactPhoneNo", order.getContactPhoneNo());
        mapSqlParameterSource.addValue("additionalInfo", order.getAdditionalInfo());
        mapSqlParameterSource.addValue("status", order.getStatus().name(), 12);
        return mapSqlParameterSource;
    }

    private SqlParameterSource getParameters(OrderItem orderItem) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", orderItem.getId());
        mapSqlParameterSource.addValue("phoneId", orderItem.getPhone().getId());
        mapSqlParameterSource.addValue("orderId", orderItem.getOrder().getId());
        mapSqlParameterSource.addValue("quantity", orderItem.getQuantity());
        return mapSqlParameterSource;
    }
}
