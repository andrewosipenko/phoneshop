package com.es.core.model.order;

import com.es.core.order.EmptyOrderListException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.es.core.model.order.OrderDaoQueries.*;

@Component
public class JdbcOrderDao implements OrderDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertOrderItems;

    @PostConstruct
    public void initSimpleJdbcInsert() {

        this.insertOrderItems = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("orderItems")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(SELECT_ORDER_COUNT, Integer.class);
    }

    @Override
    public Optional<Order> get(String key) {
        List<Order> orders = jdbcTemplate.query(GET_BY_KEY_QUERY, new OrderListResultSetExtractor(), key);
        if(orders.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(orders.get(0));
    }

    @Override
    public List<Order> findAll(int offset, int limit) {
        String offsetAndLimit = offset > 0 ? "limit " + limit : "";
        if(offset > 0)
            offsetAndLimit += " offset " + offset;
        return jdbcTemplate.query(FIRST_PART_FIND_ALL_QUERY + offsetAndLimit + SECOND_PART_FIND_ALL_QUERY, new OrderListResultSetExtractor());
    }

    @Override
    public void save(Order order) throws EmptyOrderListException {
        if(order.getOrderItems().size() == 0)
            throw new EmptyOrderListException();
        jdbcTemplate.update(INSERT_ORDER_QUERY, getParameters(order));
        insertOrderItems(order.getOrderItems());
    }

    @Override
    public void updateStatus(String key, OrderStatus orderStatus) {
        jdbcTemplate.update(UPDATE_STATUS_QUERY, orderStatus.name(), key);
    }

    private void insertOrderItems(List<OrderItem> orderItems) {
            for (OrderItem orderItem : orderItems) {
                SqlParameterSource parameters;
                parameters = getParameters(orderItem);
                long newId = insertOrderItems.executeAndReturnKey(parameters).longValue();
                orderItem.setId(newId);
            }
    }

    private Object[] getParameters(Order order) {
        return new Object[] {order.getId(), order.getSubtotal(),
        order.getDeliveryPrice(), order.getTotalPrice(),
        order.getFirstName(), order.getLastName(),
        order.getDeliveryAddress(), order.getContactPhoneNo(), order.getAdditionalInfo(), order.getStatus().name()};
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
