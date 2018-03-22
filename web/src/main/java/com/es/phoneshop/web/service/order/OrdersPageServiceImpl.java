package com.es.phoneshop.web.service.order;


import com.es.core.model.order.Order;
import com.es.core.service.order.OrderService;
import com.es.phoneshop.web.bean.OrdersPage;
import com.es.phoneshop.web.bean.Pagination;
import com.es.phoneshop.web.service.page.PageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.es.phoneshop.web.service.page.PageService.AMOUNT_PHONES_ON_PAGE;

@Service
public class OrdersPageServiceImpl implements OrdersPageService {

    @Resource
    private OrderService orderService;

    @Resource
    private PageService pageService;

    @Override
    public OrdersPage getOrdersPage(int pageNumber) {
        int orderCount = orderService.orderCount();
        int normalizedPageNumber = pageService.normalizePageNumber(pageNumber, orderCount);

        int offset = ((normalizedPageNumber - 1) * AMOUNT_PHONES_ON_PAGE);
        int limit = AMOUNT_PHONES_ON_PAGE;

        List<Order> orders = orderService.findAll(offset, limit);

        Pagination pagination = pageService.getPagination(normalizedPageNumber, orderCount);

        OrdersPage ordersPage = new OrdersPage(orderCount, orders, pagination);

        return ordersPage;
    }
}
