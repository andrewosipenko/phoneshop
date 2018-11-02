package com.es.phoneshop.web.controller.pages;

import com.es.phoneshop.core.model.phone.Phone;
import com.es.phoneshop.core.model.phone.PhoneDao;
import com.es.phoneshop.core.order.OrderServiceImpl;
import com.es.phoneshop.core.page.PagingService;
import com.es.phoneshop.web.controller.exceptions.PageNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/productList")
public class ProductListPageController {
    @Resource
    private PhoneDao phoneDao;

    @Resource
    private PagingService pagingService;

    @Resource
    private OrderServiceImpl orderService;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model,
                                  @RequestParam(required = false) String search,
                                  @RequestParam(required = false, defaultValue = "1") int page,
                                  @RequestParam(required = false, defaultValue = "id asc") String order) {
        List<Phone> phoneList;
        int total = pagingService.getTotalPages(search);

        if(page < 1 || page > total) {
            throw new PageNotFoundException();
        }

        int offset = pagingService.getOffset(page);

        if(search != null) {
            phoneList = phoneDao.search(search, order, offset, 10);
        } else {
            phoneList = phoneDao.findAll(order, offset, 10);
        }

        model.addAttribute("search", search);
        model.addAttribute("order", order);
        model.addAttribute("page", page);
        model.addAttribute("total", total);
        model.addAttribute("pagesNum", pagingService.calculatePagesNum(page, total));
        model.addAttribute("pageUrl", pagingService.getPageURL(search, order));
        model.addAttribute("searchParameter", pagingService.addSearchToOrder(search));
        model.addAttribute("phones", setColors(phoneList));
        return "productList";
    }

    private List<Phone> setColors(List<Phone> phoneList) {
        for (Phone phone : phoneList) {
            phone.setColors(new HashSet<>(phoneDao.getPhoneColors(phone.getId())));
        }
        return phoneList;
    }
}
