package com.es.phoneshop.web.controller.service.phone;

import com.es.core.cart.Cart;
import com.es.core.dao.phoneDao.PhoneDao;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.exception.NoSuchPhoneException;
import com.es.phoneshop.web.controller.exception.throwable.InvalidUrlParamException;
import com.es.phoneshop.web.controller.service.PaginationService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class PhoneService {
    @Resource
    private PhoneDao phoneDao;

    public Phone getPhone(Long id){
        return phoneDao.get(id).orElseThrow(NoSuchPhoneException::new);
    }

    public List<Phone> getPhoneList(String sortBy, int offset, String search, String dir)throws InvalidUrlParamException{
        if(!isValidSortBy(sortBy)){
            throw new InvalidUrlParamException("Invalid sort value: " + sortBy);
        }
        else if(!isValidSortDirection(dir)){
            throw new InvalidUrlParamException("Invalid sort direction: " + dir);
        }
        return phoneDao.findAllSortedBy(offset, PaginationService.PHONES_TO_DISPLAY,
                    search, sortBy, dir);
    }

    public List<Phone> getPhonesFromCart(Cart cart){
        List<Phone> phones = new ArrayList<>();
        for(Long phoneId : cart.getItems().keySet()){
            phones.add(phoneDao.get(phoneId).get());
        }
        return phones;
    }

    private boolean isValidSortBy(String sortBy){
        for(SortableFields sortableField: SortableFields.values()){
            if(sortableField.getName().equals(sortBy)){
                return true;
            }
        }
        return false;
    }

    private boolean isValidSortDirection(String dir) {
        for(SortDirection sortDirection: SortDirection.values()){
            if(sortDirection.getDirection().equals(dir)){
                return true;
            }
        }
        return false;
    }
}