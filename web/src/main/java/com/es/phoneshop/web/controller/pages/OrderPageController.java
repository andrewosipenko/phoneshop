package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.ProductDao;
import com.es.core.model.order.Order;
import com.es.core.model.phone.Phone;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/order")
@PropertySource(value = {"WEB-INF/conf/application.properties"})
public class OrderPageController {

    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;

    @Resource
    private ProductDao productDao;

    @Resource
    private ApplicationContext applicationContext;

    @Value("${delivery.price}")
    private double deliveryPrice;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model, Order order) throws OutOfStockException {
        cartService.updateTotals();
        Map<Phone, Long> phonesAndCount = new HashMap<>();
        for (Map.Entry<Long, Long> entry : cartService.getCart().getProducts().entrySet()) {
            phonesAndCount.put(productDao.loadPhoneById(entry.getKey()), entry.getValue());
        }
        model.addAttribute("phonesAndCount", phonesAndCount);
        model.addAttribute("cart", cartService.getCart());
        model.addAttribute("total", deliveryPrice + cartService.getCart().getTotalPrice());
        return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@Valid @ModelAttribute("order") Order order, BindingResult bindingResult,
                             Model model) {
        cartService.updateTotals();
        if (!bindingResult.hasErrors()) {
            try {
                order = orderService.createOrder(order, cartService.getCart());
                orderService.placeOrder(order, cartService.getCart());
                return "redirect:/orderOverview?orderId=" + order.getId();
            } catch (OutOfStockException ex) {
                cartService.updateTotals();
                Map<Phone, Long> phonesAndCount = new HashMap<>();
                for (Map.Entry<Long, Long> entry : cartService.getCart().getProducts().entrySet()) {
                    phonesAndCount.put(productDao.loadPhoneById(entry.getKey()), entry.getValue());
                }
                model.addAttribute("phonesAndCount", phonesAndCount);
                model.addAttribute("cart", cartService.getCart());
                model.addAttribute("total", deliveryPrice + cartService.getCart().getTotalPrice());
                model.addAttribute("errorMessage", "Can not place order, because some phones are out of stock! They were removed from the cart");
                return "order";
            }
        }
        Map<Phone, Long> phonesAndCount = new HashMap<>();
        for (Map.Entry<Long, Long> entry : cartService.getCart().getProducts().entrySet()) {
            phonesAndCount.put(productDao.loadPhoneById(entry.getKey()), entry.getValue());
        }
        model.addAttribute("phonesAndCount", phonesAndCount);
        model.addAttribute("cart", cartService.getCart());
        model.addAttribute("total", deliveryPrice + cartService.getCart().getTotalPrice());
        return "order";
    }
}
