package com.es.phoneshop.web.form;

import java.util.Arrays;
import java.util.Map;

public class QuickOrderInfo {
    private String[] phonesId;
    private String[] quantities;
    private Map<Long, Long> validData;

    public QuickOrderInfo() {

    }

    public QuickOrderInfo(String[] phonesId, String[] quantities) {
        this.phonesId = phonesId;
        this.quantities = quantities;
    }

    public String[] getPhonesId() {
        return phonesId;
    }

    public void setPhonesId(String[] phonesId) {
        this.phonesId = phonesId;
    }

    public String[] getQuantities() {
        return quantities;
    }

    public void setQuantities(String[] quantities) {
        this.quantities = quantities;
    }

    public Map<Long, Long> getValidData() {
        return validData;
    }

    public void setValidData(Map<Long, Long> validData) {
        this.validData = validData;
    }

    @Override
    public String toString() {
        return "QuickOrderInfo{" +
                "phonesId=" + Arrays.toString(phonesId) +
                ", quantities=" + Arrays.toString(quantities) +
                ", validDate=" + validData +
                '}';
    }
}
