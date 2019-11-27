package com.es.phoneshop.web.controller.service.phone;

import com.es.core.model.phone.Phone;
import com.es.core.service.phone.PhoneService;
import com.es.phoneshop.web.controller.exception.throwable.InvalidUrlParamException;
import com.es.phoneshop.web.controller.service.PaginationService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PhoneWebService extends PhoneService {

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