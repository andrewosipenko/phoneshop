package com.es.phoneshop.web.services;

import com.es.core.model.phone.Phone;
import com.es.core.service.phone.PhoneServiceImpl;
import com.es.phoneshop.web.controller.exceptions.InvalidParametersInUrlException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PhoneWebService extends PhoneServiceImpl {

    public List<Phone> getPhoneList(int offset, String sort, String direction, String search) throws InvalidParametersInUrlException {
        if (!isAvailableSort(sort)){
            throw new InvalidParametersInUrlException("Invalid name of sort: " + sort);
        }
        if (!isAvailableDirection(direction)){
            throw new InvalidParametersInUrlException("Invalid name of direction: " + direction);
        }
        return phoneDao.findAll(offset, PaginationService.PHONES_TO_DISPLAY, search, sort, direction);
    }

    private boolean isAvailableSort(String sort){
        for (SortEnum value : SortEnum.values()) {
            if (value.getColumn().equalsIgnoreCase(sort)){
                return true;
            }
        }
        return false;
    }

    private boolean isAvailableDirection(String direction){
        for (DirectionEnum value : DirectionEnum.values()) {
            if (value.getDirection().equalsIgnoreCase(direction)){
                return true;
            }
        }
        return false;
    }
}
