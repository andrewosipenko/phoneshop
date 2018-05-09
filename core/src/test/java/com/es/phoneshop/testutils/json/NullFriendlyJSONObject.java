package com.es.phoneshop.testutils.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

class NullFriendlyJSONObject extends JSONObject {
    private JSONObject inner;

    NullFriendlyJSONObject(JSONObject inner) {
        if (inner == null)
            throw new IllegalArgumentException();
        this.inner = inner;
    }

    @Override
    public boolean isNull(String key) {
        return inner.isNull(key);
    }

    @Override
    public BigDecimal getBigDecimal(String key) throws JSONException {
        if (isNull(key))
            return null;
        return inner.getBigDecimal(key);
    }

    @Override
    public String getString(String key) throws JSONException {
        if (isNull(key))
            return null;
        return inner.getString(key);
    }

    @Override
    public int getInt(String key) throws JSONException {
        return inner.getInt(key);
    }

    @Override
    public long getLong(String key) throws JSONException {
        return inner.getLong(key);
    }

    @Override
    public JSONArray getJSONArray(String key) throws JSONException {
        return inner.getJSONArray(key);
    }
}
