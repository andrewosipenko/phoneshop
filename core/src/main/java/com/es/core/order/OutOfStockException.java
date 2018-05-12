package com.es.core.order;
import com.es.core.model.phone.Phone;
import java.util.List;

public class OutOfStockException extends Exception {
    private List<Phone> rejectedPhones;
    public OutOfStockException() {
    }

    public OutOfStockException(List<Phone> rejectedPhones) {
        this.rejectedPhones = rejectedPhones;
    }

    public List<Phone> getRejectedPhones() {
        return rejectedPhones;
    }

    public void setRejectedPhones(List<Phone> rejectedPhones) {
        this.rejectedPhones = rejectedPhones;
    }
}
