package com.es.phoneshop.web.controller.pages.property;

import java.util.ListResourceBundle;

public class PropertyResourceBundle_EN extends ListResourceBundle {
    static final Object[][] contents = {
            {"image", "Image"},
            {"brand", "Brand"},
            {"model", "Model"},
            {"color", "Color"},
            {"displaySize", "Display size"},
            {"price", "Price"},
            {"quantity", "Quantity"},
            {"action", "Action"}
    };

    protected Object[][] getContents() {
        return contents;
    }
}
