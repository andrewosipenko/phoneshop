package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.StockDao;
import com.es.core.model.phone.Stock;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {

    private static final int COUNT_ON_PAGE = 5;

    @Resource
    private StockDao stockDao;

    @Resource
    private CartService cartService;

    @Resource
    private ApplicationContext applicationContext;

    @RequestMapping(value = "/{pageAction}", method = RequestMethod.GET)
    public String showProductList(@PathVariable String pageAction, @RequestParam(required = false) String sortField, @RequestParam(required = false) String sortOrder,
                                  Model model, HttpSession httpSession) {
        AtomicInteger currentPage_productList = (AtomicInteger) httpSession.getAttribute("currentPage_productList");
        int countOfPages = (int) Math.ceil(stockDao.getCountOfAllAvailableStocks() / 5.0);
        if (currentPage_productList == null) {
            currentPage_productList = new AtomicInteger();
            currentPage_productList.set(1);
            httpSession.setAttribute("currentPage_productList", currentPage_productList);
        }
        String searchFor = (String) httpSession.getAttribute("searchFor");
        if (searchFor != null) {
            currentPage_productList.set(1);
            httpSession.removeAttribute("searchFor");
        }
        if (currentPage_productList.get() < 1) {
            currentPage_productList.set(1);
        }
        if (pageAction.equals("+1")) {
            currentPage_productList.set(currentPage_productList.get() + 1);
        } else if (pageAction.equals("-1")) {
            if (!(currentPage_productList.get() - 1 < 1)) {
                currentPage_productList.set(currentPage_productList.get() - 1);
            }
        } else {
            try {
                currentPage_productList.set(Math.min(countOfPages, Integer.parseInt(pageAction)));
            } catch (Exception e) {

            }
        }
        List<Stock> stockList;
        if (sortField != null) {
            stockList = sortStocks(sortField, sortOrder, httpSession, (currentPage_productList.get() - 1) * COUNT_ON_PAGE + 1);
        } else {
            String currentSortField = (String) httpSession.getAttribute("currentSortField");
            String currentSortOrder = (String) httpSession.getAttribute("currentSortOrder");
            if (currentSortField != null) {
                stockList = sortStocks(currentSortField, currentSortOrder, httpSession, (currentPage_productList.get() - 1) * COUNT_ON_PAGE + 1);
            } else {
                stockList = stockDao.getAvailableStocksByPage((currentPage_productList.get() - 1) * COUNT_ON_PAGE + 1, COUNT_ON_PAGE);
            }
        }
        model.addAttribute("countOfPages", countOfPages);
        model.addAttribute("stockList", stockList);
        cartService.updateTotals();
        model.addAttribute("cart", cartService.getCart());
        return "productList";
    }

    @RequestMapping(value = "/search/{pageAction}", method = RequestMethod.GET)
    public String searchProducts(@PathVariable String pageAction, @RequestParam(required = false) String searchFor,
                                 @RequestParam(required = false) String sortField, @RequestParam(required = false) String sortOrder, Model model, HttpSession httpSession) {
        String searchFor_session = (String) httpSession.getAttribute("searchFor");
        if (searchFor_session == null) {
            searchFor_session = searchFor;
            httpSession.setAttribute("searchFor", searchFor_session);
        }
        if (searchFor == null) {
            searchFor = searchFor_session;
        }
        int countOfPages = (int) Math.ceil(stockDao.getCountOfAllAvailableStocks_search(searchFor) / 5.0);
        AtomicInteger currentPage_searchProductList = (AtomicInteger) httpSession.getAttribute("currentPage_productList");
        if (currentPage_searchProductList == null) {
            currentPage_searchProductList = new AtomicInteger();
            currentPage_searchProductList.set(1);
            httpSession.setAttribute("currentPage_productList", currentPage_searchProductList);
        }
        if (currentPage_searchProductList.get() < 1) {
            currentPage_searchProductList.set(1);
        }
        if (pageAction.equals("+1")) {
            currentPage_searchProductList.set(currentPage_searchProductList.get() + 1);
        } else if (pageAction.equals("-1")) {
            if (!(currentPage_searchProductList.get() - 1 < 1)) {
                currentPage_searchProductList.set(currentPage_searchProductList.get() - 1);
            }
        } else {
            try {
                currentPage_searchProductList.set(Math.min(countOfPages, Integer.parseInt(pageAction)));
            } catch (Exception e) {

            }
        }
        List<Stock> stockList;
        if (sortField != null) {
            stockList = sortStocks_search(sortField, sortOrder, searchFor, httpSession, (currentPage_searchProductList.get() - 1) * COUNT_ON_PAGE + 1);
        } else {
            String currentSortField = (String) httpSession.getAttribute("currentSortField");
            String currentSortOrder = (String) httpSession.getAttribute("currentSortOrder");
            if (currentSortField != null) {
                stockList = sortStocks_search(currentSortField, currentSortOrder, searchFor, httpSession, (currentPage_searchProductList.get() - 1) * COUNT_ON_PAGE + 1);
            } else {
                stockList = stockDao.getAvailableStocksByPage_search(searchFor, (currentPage_searchProductList.get() - 1) * COUNT_ON_PAGE + 1, COUNT_ON_PAGE);
            }
        }
        model.addAttribute("countOfPages", countOfPages);
        model.addAttribute("stockList", stockList);
        model.addAttribute("searchFor", searchFor);
        cartService.updateTotals();
        model.addAttribute("cart", cartService.getCart());
        return "productList";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String defaultProductListControllerMethod() {
        return "redirect:productList/1";
    }

    private List<Stock> sortStocks(String sortFiled, String sortOrder, HttpSession httpSession, int pageId) {
        List<Stock> stockList = null;
        if (sortOrder == null) {
            sortOrder = (String) httpSession.getAttribute("currentSortField");
        }
        switch (sortFiled) {
            case "brand": {
                httpSession.setAttribute("currentSortField", "brand");
                switch (sortOrder) {
                    case "asc": {
                        httpSession.setAttribute("currentSortOrder", "asc");
                        stockList = stockDao.getAvailableStocksSortBy_brand_Order_asc_ByPage(pageId, ProductListPageController.COUNT_ON_PAGE);
                        break;
                    }
                    case "desc": {
                        httpSession.setAttribute("currentSortOrder", "desc");
                        stockList = stockDao.getAvailableStocksSortBy_brand_Order_desc_ByPage(pageId, ProductListPageController.COUNT_ON_PAGE);
                        break;
                    }
                }
                break;
            }
            case "model": {
                httpSession.setAttribute("currentSortField", "model");
                switch (sortOrder) {
                    case "asc": {
                        httpSession.setAttribute("currentSortOrder", "asc");
                        stockList = stockDao.getAvailableStocksSortBy_model_Order_asc_ByPage(pageId, ProductListPageController.COUNT_ON_PAGE);
                        break;
                    }
                    case "desc": {
                        httpSession.setAttribute("currentSortOrder", "desc");
                        stockList = stockDao.getAvailableStocksSortBy_model_Order_desc_ByPage(pageId, ProductListPageController.COUNT_ON_PAGE);
                        break;
                    }
                }
                break;
            }
            case "displaySize": {
                httpSession.setAttribute("currentSortField", "displaySize");
                switch (sortOrder) {
                    case "asc": {
                        httpSession.setAttribute("currentSortOrder", "asc");
                        stockList = stockDao.getAvailableStocksSortBy_displaySize_Order_asc_ByPage(pageId, ProductListPageController.COUNT_ON_PAGE);
                        break;
                    }
                    case "desc": {
                        httpSession.setAttribute("currentSortOrder", "desc");
                        stockList = stockDao.getAvailableStocksSortBy_displaySize_Order_desc_ByPage(pageId, ProductListPageController.COUNT_ON_PAGE);
                        break;
                    }
                }
                break;
            }
            case "price": {
                httpSession.setAttribute("currentSortField", "price");
                switch (sortOrder) {
                    case "asc": {
                        httpSession.setAttribute("currentSortOrder", "asc");
                        stockList = stockDao.getAvailableStocksSortBy_price_Order_asc_ByPage(pageId, ProductListPageController.COUNT_ON_PAGE);
                        break;
                    }
                    case "desc": {
                        httpSession.setAttribute("currentSortOrder", "desc");
                        stockList = stockDao.getAvailableStocksSortBy_price_Order_desc_ByPage(pageId, ProductListPageController.COUNT_ON_PAGE);
                        break;
                    }
                }
                break;
            }
        }
        if (stockList == null) {
            stockList = stockDao.getAvailableStocksByPage(pageId, ProductListPageController.COUNT_ON_PAGE);
        }
        return stockList;
    }

    private List<Stock> sortStocks_search(String sortFiled, String sortOrder, String searchFor, HttpSession httpSession, int pageId) {
        List<Stock> stockList = null;
        if (sortOrder == null) {
            sortOrder = (String) httpSession.getAttribute("currentSortField");
        }
        switch (sortFiled) {
            case "brand": {
                httpSession.setAttribute("currentSortField", "brand");
                switch (sortOrder) {
                    case "asc": {
                        httpSession.setAttribute("currentSortOrder", "asc");
                        stockList = stockDao.getAvailableStocksSortBy_brand_Order_asc_ByPage_search(searchFor, pageId, ProductListPageController.COUNT_ON_PAGE);
                        break;
                    }
                    case "desc": {
                        httpSession.setAttribute("currentSortOrder", "desc");
                        stockList = stockDao.getAvailableStocksSortBy_brand_Order_desc_ByPage_search(searchFor, pageId, ProductListPageController.COUNT_ON_PAGE);
                        break;
                    }
                }
                break;
            }
            case "model": {
                httpSession.setAttribute("currentSortField", "model");
                switch (sortOrder) {
                    case "asc": {
                        httpSession.setAttribute("currentSortOrder", "asc");
                        stockList = stockDao.getAvailableStocksSortBy_model_Order_asc_ByPage_search(searchFor, pageId, ProductListPageController.COUNT_ON_PAGE);
                        break;
                    }
                    case "desc": {
                        httpSession.setAttribute("currentSortOrder", "desc");
                        stockList = stockDao.getAvailableStocksSortBy_model_Order_desc_ByPage_search(searchFor, pageId, ProductListPageController.COUNT_ON_PAGE);
                        break;
                    }
                }
                break;
            }
            case "displaySize": {
                httpSession.setAttribute("currentSortField", "displaySize");
                switch (sortOrder) {
                    case "asc": {
                        httpSession.setAttribute("currentSortOrder", "asc");
                        stockList = stockDao.getAvailableStocksSortBy_displaySize_Order_asc_ByPage_search(searchFor, pageId, ProductListPageController.COUNT_ON_PAGE);
                        break;
                    }
                    case "desc": {
                        httpSession.setAttribute("currentSortOrder", "desc");
                        stockList = stockDao.getAvailableStocksSortBy_displaySize_Order_desc_ByPage_search(searchFor, pageId, ProductListPageController.COUNT_ON_PAGE);
                        break;
                    }
                }
                break;
            }
            case "price": {
                httpSession.setAttribute("currentSortField", "price");
                switch (sortOrder) {
                    case "asc": {
                        httpSession.setAttribute("currentSortOrder", "asc");
                        stockList = stockDao.getAvailableStocksSortBy_price_Order_asc_ByPage_search(searchFor, pageId, ProductListPageController.COUNT_ON_PAGE);
                        break;
                    }
                    case "desc": {
                        httpSession.setAttribute("currentSortOrder", "desc");
                        stockList = stockDao.getAvailableStocksSortBy_price_Order_desc_ByPage_search(searchFor, pageId, ProductListPageController.COUNT_ON_PAGE);
                        break;
                    }
                }
                break;
            }
        }
        if (stockList == null) {
            stockList = stockDao.getAvailableStocksByPage_search(searchFor, pageId, ProductListPageController.COUNT_ON_PAGE);
        }
        return stockList;
    }

}
