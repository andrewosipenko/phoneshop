package com.es.phoneshop.web.service.phone;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.phone.OrderBy;
import com.es.core.model.phone.Phone;
import com.es.phoneshop.web.bean.Pagination;
import com.es.phoneshop.web.bean.ProductPage;
import com.es.phoneshop.web.service.page.PageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.es.phoneshop.web.service.page.PageService.AMOUNT_PHONES_ON_PAGE;

@Service
public class PhonePageServiceImpl implements PhonePageService {

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private PageService pageService;

    @Override
    public ProductPage getPhonePage(OrderBy order, String query, int pageNumber) {
        int phoneCount = getPhoneCount(query);
        int normalizedPageNumber = pageService.normalizePageNumber(pageNumber, phoneCount);

        int offset = ((normalizedPageNumber - 1) * AMOUNT_PHONES_ON_PAGE);
        int limit = AMOUNT_PHONES_ON_PAGE;

        List<Phone> phones = getPhoneList(query, order, offset, limit);

        Pagination pagination = pageService.getPagination(normalizedPageNumber, phoneCount);

        ProductPage productPage = new ProductPage(phoneCount, phones, pagination);

        return productPage;
    }

    private int getPhoneCount(String query) {
        if (query == null) {
            return phoneDao.phoneCount();
        }
        return phoneDao.phoneCountByQuery(query);
    }

    private List<Phone> getPhoneList(String query, OrderBy order, int offset, int limit) {
        if (query == null) {
            return phoneDao.findAllInOrder(order, offset, limit);
        }
        return phoneDao.getPhonesByQuery(query, order, offset, limit);
    }


}
