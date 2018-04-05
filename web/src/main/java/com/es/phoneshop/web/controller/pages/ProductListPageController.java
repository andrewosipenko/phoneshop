package com.es.phoneshop.web.controller.pages;

import com.es.core.dao.PhoneDao;
import com.es.core.model.phone.Phone;
import com.es.phoneshop.web.controller.exception.InvalidUrlParamException;
import com.es.phoneshop.web.controller.paginator.PaginatorService;
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
    private final String SORT_ABSENT = "sortAbsent";
    private final String SEARCH_ANY = "";
    @Resource
    private PhoneDao phoneDao;

    @RequestMapping(method = RequestMethod.GET, params = {"page", "action"})
    public String changePages(@RequestParam Map<String,String> urlParams)
            throws InvalidUrlParamException{
        Integer currentPage = Integer.parseInt(urlParams.get("page"));
        String pageAction = urlParams.get("action");
        String restParams = getRestParams(urlParams);

        String search = urlParams.get("search");
        return "redirect:/productList?page=" +
                PaginatorService.getNewPage(currentPage, pageAction, search) +
                restParams;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "sort", required = false, defaultValue = SORT_ABSENT) String sortBy,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") String dir,
            @RequestParam(name = "search", required = false, defaultValue = SEARCH_ANY) String search,
            Model model)
            throws IllegalArgumentException, InvalidUrlParamException {
        Integer pageBeginNumber = PaginatorService.getPageBeginNumber(page, search);
        model.addAttribute("pageBeginNumber", pageBeginNumber);

        page = PaginatorService.getValidNewPage(page, search);
        model.addAttribute("page", page);

        model.addAttribute("pagesToDisplay", PaginatorService.getPageAmountToDisplay(pageBeginNumber, search));
        model.addAttribute("direction", dir);
        model.addAttribute("sort", sortBy);
        model.addAttribute("searchText", search);

        int offset = PaginatorService.PHONES_TO_DISPLAY * (page - 1);
        List<Phone> phoneList = getPhoneList(sortBy, offset, search, dir);
        model.addAttribute("phones", phoneList);
        return "productList";
    }

    private List<Phone> getPhoneList(String sortBy, int offset, String search, String dir){
        if(SORT_ABSENT.equals(sortBy)) {
            return phoneDao.findAll(offset, PaginatorService.PHONES_TO_DISPLAY, search);
        }
        else{
            return phoneDao.findAllSortedBy(offset, PaginatorService.PHONES_TO_DISPLAY,
                    search, sortBy, dir);
        }
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