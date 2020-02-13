package com.es.core.configurer;

import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PhoneParametersPreparer {
    private static final String[] FIELD_NAMES = {"brand", "model", "price", "displaySizeInches", "weightGr",
            "lengthMm", "widthMm", "heightMm", "announced", "deviceType", "os", "displayResolution", "pixelDensity",
            "displayTechnology", "backCameraMegapixels", "frontCameraMegapixels", "ramGb", "internalStorageGb",
            "batteryCapacityMah", "talkTimeHours", "standByTimeHours", "bluetooth", "positioning", "imageUrl",
            "description", "id"};

    public Map<String, Object> fillMapForSaving(Phone phone) {
        Map<String, Object> map = new HashMap<>();
        Object[] phoneParameters = getPreparedParameters(phone);
        for (int i=0; i<FIELD_NAMES.length; i++){
            map.put(FIELD_NAMES[i], phoneParameters[i]);
        }
        return map;
    }

    public Object[] getPreparedParameters(Phone phone) {
        Object[] preparedParameters = new Object[26];
        preparedParameters[0] = phone.getBrand();
        preparedParameters[1] = phone.getModel();
        preparedParameters[2] = phone.getPrice();
        preparedParameters[3] = phone.getDisplaySizeInches();
        preparedParameters[4] = phone.getWeightGr();
        preparedParameters[5] = phone.getLengthMm();
        preparedParameters[6] = phone.getWidthMm();
        preparedParameters[7] = phone.getHeightMm();
        preparedParameters[8] = phone.getAnnounced();
        preparedParameters[9] = phone.getDeviceType();
        preparedParameters[10] = phone.getOs();
        preparedParameters[11] =  phone.getDisplayResolution();
        preparedParameters[12] = phone.getPixelDensity();
        preparedParameters[13] =  phone.getDisplayTechnology();
        preparedParameters[14] = phone.getBackCameraMegapixels();
        preparedParameters[15] = phone.getFrontCameraMegapixels();
        preparedParameters[16] =  phone.getRamGb();
        preparedParameters[17] = phone.getInternalStorageGb();
        preparedParameters[18] =  phone.getBatteryCapacityMah();
        preparedParameters[19] = phone.getTalkTimeHours();
        preparedParameters[20] =  phone.getStandByTimeHours();
        preparedParameters[21] =  phone.getBluetooth();
        preparedParameters[22] = phone.getPositioning();
        preparedParameters[23] = phone.getImageUrl();
        preparedParameters[24] = phone.getDescription();
        preparedParameters[25] = phone.getId();
        return preparedParameters;
    }
}
