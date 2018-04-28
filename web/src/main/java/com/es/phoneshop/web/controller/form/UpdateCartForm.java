package com.es.phoneshop.web.controller.form;

import com.es.phoneshop.web.controller.util.UpdateCartRecord;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class UpdateCartForm {
    @Valid
    private List<UpdateCartRecord> updateCartRecords = new ArrayList<>();

    public UpdateCartForm() {
    }

    public List<UpdateCartRecord> getUpdateCartRecords() {
        return updateCartRecords;
    }

    public void setUpdateCartRecords(List<UpdateCartRecord> updateCartRecords) {
        this.updateCartRecords = updateCartRecords;
    }
}
