package com.es.core.service.phone;

public interface PhoneStockService {

    boolean hasEnoughStock(Long phoneId, Long quantity);

}
