package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.phoneshop.web.controller.exception.throwable.InvalidUrlParamException;
import com.es.phoneshop.web.controller.service.PaginationService;
import com.es.phoneshop.web.controller.service.phone.PhoneWebService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    @Resource
    private PaginationService paginationService;
    @Resource
    private PhoneWebService phoneWebService;

    private final String DEFAULT_SORT_BY = "brand";
    private final String DEFAULT_SORT_DIRECTION = "asc";
    private final String SEARCH_ANY = "";

    @RequestMapping(method = RequestMethod.GET, params = {"page", "action"})
    public String changePages(@RequestParam Map<String,String> urlParams)
            throws InvalidUrlParamException{
        Integer currentPage = Integer.parseInt(urlParams.get("page"));
        String pageAction = urlParams.get("action");
        String restParams = getRestParams(urlParams);

        String search = urlParams.get("search");
        return "redirect:/productList?page=" +
                paginationService.getNewPage(currentPage, pageAction, search) +
                restParams;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "sort", required = false, defaultValue = DEFAULT_SORT_BY) String sortBy,
            @RequestParam(name = "dir", required = false, defaultValue = DEFAULT_SORT_DIRECTION) String dir,
            @RequestParam(name = "search", required = false, defaultValue = SEARCH_ANY) String search,
            Model model)
            throws IllegalArgumentException, InvalidUrlParamException {
        Integer pageBeginNumber = paginationService.getPageBeginNumber(page, search);
        model.addAttribute("pageBeginNumber", pageBeginNumber);

        page = paginationService.getValidNewPage(page, search);
        model.addAttribute("page", page);

        model.addAttribute("pagesToDisplay", paginationService.getPageAmountToDisplay(pageBeginNumber, search));
        model.addAttribute("direction", dir);
        model.addAttribute("sort", sortBy);
        model.addAttribute("searchText", search);

        int offset = PaginationService.PHONES_TO_DISPLAY * (page - 1);
        List<Phone> phoneList = phoneWebService.getPhoneList(sortBy, offset, search, dir);
        model.addAttribute("phones", phoneList);
        return "productList";
    }

    private String getRestParams(Map<String, String> urlParams){
        StringBuffer restUrlParams = new StringBuffer();
        for(Map.Entry<String, String> entry : urlParams.entrySet()){
            if(!entry.getKey().equals("page") && !entry.getKey().equals("action"))
                restUrlParams.append("&" + entry.getKey() + "=" + entry.getValue());
        }
        return restUrlParams.toString();
    }
}