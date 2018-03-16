package com.es.core.testutils;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JSONAssertionData {
    private Map<Long, Phone> phoneMap = new HashMap<>();
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public JSONAssertionData(String resPath) throws IOException, ParseException {
        InputStream stream = new ClassPathResource(resPath).getInputStream();
        JSONTokener tokener = new JSONTokener(stream);
        JSONArray array = new JSONArray(tokener);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            Phone phone = getPhoneObject(object);
            phoneMap.put(phone.getId(), phone);
        }
    }

    private Phone getPhoneObject(JSONObject jsonObject) throws ParseException {
        jsonObject = new NullFriendlyJSONObject(jsonObject);
        Phone phone = new Phone();
        phone.setId(jsonObject.getLong("id"));
        phone.setBrand(jsonObject.getString("brand"));
        phone.setModel(jsonObject.getString("model"));
        phone.setPrice(jsonObject.getBigDecimal("price"));
        phone.setDisplaySizeInches(jsonObject.getBigDecimal("displaySizeInches"));
        phone.setWeightGr(getIntObject(jsonObject, "weightGr"));
        phone.setLengthMm(jsonObject.getBigDecimal("lengthMm"));
        phone.setWidthMm(jsonObject.getBigDecimal("widthMm"));
        phone.setHeightMm(jsonObject.getBigDecimal("heightMm"));
        phone.setAnnounced(getAnnounced(jsonObject));
        phone.setDeviceType(jsonObject.getString("deviceType"));
        phone.setOs(jsonObject.getString("os"));
        phone.setDisplayResolution(jsonObject.getString("displayResolution"));
        phone.setPixelDensity(getIntObject(jsonObject, "pixelDensity"));
        phone.setDisplayTechnology(jsonObject.getString("displayTechnology"));
        phone.setBackCameraMegapixels(jsonObject.getBigDecimal("backCameraMegapixels"));
        phone.setFrontCameraMegapixels(jsonObject.getBigDecimal("frontCameraMegapixels"));
        phone.setRamGb(jsonObject.getBigDecimal("ramGb"));
        phone.setInternalStorageGb(jsonObject.getBigDecimal("internalStorageGb"));
        phone.setBatteryCapacityMah(getIntObject(jsonObject, "batteryCapacityMah"));
        phone.setTalkTimeHours(jsonObject.getBigDecimal("talkTimeHours"));
        phone.setStandByTimeHours(jsonObject.getBigDecimal("standByTimeHours"));
        phone.setBluetooth(jsonObject.getString("bluetooth"));
        phone.setPositioning(jsonObject.getString("positioning"));
        phone.setImageUrl(jsonObject.getString("imageUrl"));
        phone.setDescription(jsonObject.getString("description"));
        phone.setColors(getColors(jsonObject.getJSONArray("colors")));
        return phone;
    }

    private Set<Color> getColors(JSONArray colorArray) {
        Set<Color> colors = new HashSet<>();
        for (int i = 0; i < colorArray.length(); i++) {
            Color color = new Color();
            color.setId(colorArray.getJSONObject(i).getLong("id"));
            color.setCode(colorArray.getJSONObject(i).getString("code"));
            colors.add(color);
        }
        return colors;
    }

    private Integer getIntObject(JSONObject object, String key) {
        if (object.isNull(key))
            return null;
        return object.getInt(key);
    }

    private Date getAnnounced(JSONObject object) throws ParseException {
        String dateString = object.getString("announced");
        return (dateString == null) ? null : dateFormat.parse(dateString);
    }

    public Phone getPhone(long id) {
        return phoneMap.get(id);
    }

    public int getSize() {
        return phoneMap.size();
    }

    public void reflectChanges(Phone phone) {
        phoneMap.put(phone.getId(), phone);
    }
}
