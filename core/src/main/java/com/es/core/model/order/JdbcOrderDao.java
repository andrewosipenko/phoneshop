package com.es.core.model.order;

import com.es.core.exception.OrderNotFindException;
import com.es.core.model.phone.PhoneDao;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcOrderDao implements OrderDao {
    public static final String SELECT_FROM_ORDERS_WHERE_ID = "select * from orders where id=?";
    public static final String SELECT_ORDER_ITEMS = "select ORDERITEMS.ID, PHONEID, QUANTITY from ORDERS left join ORDER2ORDERITEM on ORDERS.ID = ORDER2ORDERITEM.ORDERID left join ORDERITEMS on ORDER2ORDERITEM.ORDERITEMID=ORDERITEMS.ID where ORDERS.ID=?";
    public static final String DELETE_FROM_ORDERS_WHERE_ID = "delete from orders where id=?";
    public static final String DELETE_FROM_ORDER_2_ORDER_ITEM_WHERE_ORDER_ID = "delete from order2orderItem where orderId=?";
    public static final String SELECT_MAX_ID_FROM_ORDER_ITEMS = "select max(id) from orderItems";
    public static final String SELECT_MAX_ID_FROM_ORDERS = "select max(id) from orders";
    public static final String INSERT_INTO_ORDERS = "insert into orders values(?,?,?,?,?,?,?,?,?,?)";
    public static final String INSERT_INTO_ORDER_ITEMS = "insert into orderItems values (?,?,?)";
    public static final String INSERT_INTO_ORDER_2_ORDER_ITEM = "insert into order2orderItem values(?,?)";
    public static final String SELECT_ORDER_ITEM_ID_FROM_ORDER_2_ORDER_ITEM_WHERE_ORDER_ID = "select orderItemId from order2orderItem where orderId=?";
    public static final String DELETE_FROM_ORDER_ITEMS_WHERE_ID = "delete from orderItems where id=?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private PhoneDao phoneDao;

    @Override
    public void deleteOrder(Long id) throws OrderNotFindException {
        List<Long> orderItemIdList =
                jdbcTemplate.queryForList(SELECT_ORDER_ITEM_ID_FROM_ORDER_2_ORDER_ITEM_WHERE_ORDER_ID,
                        new Object[]{id}, Long.class);
        jdbcTemplate.batchUpdate(DELETE_FROM_ORDER_ITEMS_WHERE_ID, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, orderItemIdList.get(i));
            }

            @Override
            public int getBatchSize() {
                return orderItemIdList.size();
            }
        });
        jdbcTemplate.update(DELETE_FROM_ORDERS_WHERE_ID, id);
        jdbcTemplate.update(DELETE_FROM_ORDER_2_ORDER_ITEM_WHERE_ORDER_ID, id);
    }

    @Override
    public void saveOrder(Order order) throws OrderNotFindException {
        Long orderItemNewIndex = jdbcTemplate.queryForObject(SELECT_MAX_ID_FROM_ORDER_ITEMS, Long.class) + 1;
        Long orderNewIndex = jdbcTemplate.queryForObject(SELECT_MAX_ID_FROM_ORDERS, Long.class) + 1;
        jdbcTemplate.update(INSERT_INTO_ORDERS, getOrdersParam(order, orderNewIndex));

        jdbcTemplate.batchUpdate(INSERT_INTO_ORDER_ITEMS, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, orderItemNewIndex + i);
                preparedStatement.setLong(2, order.getOrderItems().get(i).getPhone().getId());
                preparedStatement.setLong(3, order.getOrderItems().get(i).getQuantity());
            }

            @Override
            public int getBatchSize() {
                return order.getOrderItems().size();
            }
        });

        jdbcTemplate.batchUpdate(INSERT_INTO_ORDER_2_ORDER_ITEM, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, orderNewIndex);
                preparedStatement.setLong(2, order.getOrderItems().get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return order.getOrderItems().size();
            }
        });
    }

    private Object[] getOrdersParam(Order order, Long orderItemNewIndex) {
        return new Object[]{orderItemNewIndex,
                order.getSubtotal(),
                order.getDeliveryPrice(),
                order.getTotalPrice(),
                order.getFirstName(),
                order.getLastName(),
                order.getDeliveryAddress(),
                order.getContactPhoneNo(),
                order.getStatus().toString(),
                order.getAdditionalInfo()};
    }

    @Override
    public Optional<Order> getOrder(Long id) throws OrderNotFindException {
        Optional<Order> optionalOrder = jdbcTemplate.query(SELECT_FROM_ORDERS_WHERE_ID,
                new Object[]{id}, new OrderRowMapper()).stream().findAny();
        if (optionalOrder.isEmpty()) {
            throw new OrderNotFindException();
        }
        List<OrderItem> orderItemList = new ArrayList<>(jdbcTemplate.query(SELECT_ORDER_ITEMS, new Object[]{id},
                new OrderItemRowMapper()));
        orderItemList.forEach(orderItem -> {
            orderItem.setOrder(optionalOrder.get());
            orderItem.setPhone(phoneDao.get(orderItem.getPhoneId()).get());
        });
        optionalOrder.get().setOrderItems(orderItemList);
        return optionalOrder;
    }

    @Override
    public Order getOrderBySecureId(String secureId) throws OrderNotFindException {
        return null;
    }

    @Override
    public Long getLastOrderItemId() {
        return jdbcTemplate.queryForObject(SELECT_MAX_ID_FROM_ORDER_ITEMS, Long.class);
    }

    @Override
    public Long getLastOrderId() {
        return jdbcTemplate.queryForObject(SELECT_MAX_ID_FROM_ORDERS, Long.class);
    }
}
