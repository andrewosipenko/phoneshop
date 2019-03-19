package com.es.phoneshop.web.service.error;

import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Optional;

@Service
public class ErrorMessageService {
    public String[] getErrorToField(List<ObjectError> errors, String fieldName) {
        String[] errorParameters = new String[2];
        System.out.println(fieldName);
        Optional<ObjectError> optionalError = errors.stream()
                .filter(p -> fieldName.equals(p.getArguments()[0]))
                .findAny();

        if (optionalError.isPresent()) {
            ObjectError error = optionalError.get();
            errorParameters[0] = error.getCodes()[3];
            errorParameters[1] = error.getDefaultMessage();
        }

        return errorParameters;
    }
}
