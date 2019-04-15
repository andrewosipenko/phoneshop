package com.es.phoneshop.web.validator;

import com.es.core.model.phone.Phone;
import com.es.core.service.phone.PhoneService;
import com.es.core.service.stock.StockService;
import com.es.phoneshop.web.form.QuickOrderInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@Service
@PropertySource("classpath:/message/errorMessageEN.properties")
public class QuickOrderInfoValidator implements Validator {
    private static final String NUMBER_REGEX = "^\\d+";
    private static final Pattern pattern = Pattern.compile(NUMBER_REGEX);
    private static final String PHONES_ID_FIELD = "phonesId";
    private static final String QUANTITIES_FIELD = "quantities";

    @Value("${message.notPositive}")
    private String messageNotPositive;

    @Value("${message.notNumberFormat}")
    private String messageNotNumberFormat;

    @Value("${message.notEnoughStock}")
    private String messageNotEnoughStock;

    @Value("${message.noPhoneWithSuchId}")
    private String messageNoPhoneWithSuchId;

    @Resource
    private StockService stockService;

    @Resource
    private PhoneService phoneService;

    @Override
    public boolean supports(Class<?> aClass) {
        return QuickOrderInfo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        QuickOrderInfo quickOrderInfo = (QuickOrderInfo) o;
        String[] phonesId = quickOrderInfo.getPhonesId();
        String[] quantities = quickOrderInfo.getQuantities();


        IntStream.range(0, phonesId.length).forEach(i -> {
            if (validateOnEmpty(phonesId[i]) && validateOnEmpty(quantities[i])) {
                boolean isValidPhoneId = validateSymbols(phonesId[i], PHONES_ID_FIELD, i, errors);
                boolean isValidQuantities = validateSymbols(quantities[i], QUANTITIES_FIELD, i, errors);
                if (isValidPhoneId && isValidQuantities) {
                    Long phoneId = Long.valueOf(phonesId[i]);
                    Long quantity = Long.valueOf(quantities[i]);
                    if (validatePhoneId(phoneId, PHONES_ID_FIELD, i, errors) &&
                            validateStock(phoneId, quantity, QUANTITIES_FIELD, i, errors)) {
                        quickOrderInfo.getValidData().put(phoneId, quantity);
                    }
                }
            }
        });
    }

    private boolean validateOnEmpty(String value) {
        return !value.isEmpty();
    }

    private boolean validateSymbols(String stringValue, String field, int index, Errors errors) {
        Matcher matcher = pattern.matcher(stringValue);
        if (matcher.matches()) {
            errors.rejectValue(field, String.valueOf(index), new String[]{stringValue}, messageNotNumberFormat);
            return false;
        }
        Long value = Long.valueOf(stringValue);
        if (value < 0) {
            errors.rejectValue(field, String.valueOf(index), new String[]{stringValue}, messageNotPositive);
            return false;
        }
        return true;
    }

    private boolean validatePhoneId(Long phoneId, String field, int index, Errors errors) {
        Optional<Phone> phone = phoneService.get(phoneId);
        if (phone.isPresent()) {
            return true;
        } else {
            errors.rejectValue(field, String.valueOf(index), new String[]{String.valueOf(phoneId)}, messageNoPhoneWithSuchId);
            return false;
        }
    }

    private boolean validateStock(Long phoneId, Long quantity, String field, int index, Errors errors) {
        Long maxStock = stockService.findPhoneStock(phoneId).getStock();
        if (maxStock < quantity) {
            errors.rejectValue(field, String.valueOf(index), new String[]{String.valueOf(quantity)}, messageNotEnoughStock);
            return false;
        }
        return true;
    }
}
