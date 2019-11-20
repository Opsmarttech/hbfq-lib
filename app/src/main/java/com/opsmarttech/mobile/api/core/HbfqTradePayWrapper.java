package com.opsmarttech.mobile.api.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.opsmarttech.mobile.api.core.constant.Constants;
import com.opsmarttech.mobile.api.core.exception.ConfigNotFountException;
import com.opsmarttech.mobile.api.core.exception.TradeParamEmptyException;
import com.opsmarttech.mobile.api.core.http.HbfqResponse;
import com.opsmarttech.mobile.api.core.http.TradeParam;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class HbfqTradePayWrapper<T extends HbfqTradePayGeneric> {

    private static final String TAG = "HbfqTradePayWrapper";

    private T trade;

    public HbfqTradePayWrapper(T trade) {
        this.trade = trade;
    }

    public JSONObject executeTradePayRequset(Context context, TradeParam param) throws ConfigNotFountException, TradeParamEmptyException {
        JSONObject jsonObject = null;
        SharedPreferences sp = context.getSharedPreferences(Constants.SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE);
        String uriString = sp.getString(Constants.PRE_CREATE_ROUTE, "");
        String meid = sp.getString(Constants.DEVICE_MEID, "");
        if(uriString == null || "".equals(uriString) || meid == null || "".equals(meid)) throw new ConfigNotFountException();
        if(param == null || param.size() == 0) throw new TradeParamEmptyException();
        param.route = uriString;
        param.put("deviceMeid", meid);
        HbfqResponse resp = trade.executeTradePayRequset(param);
        if(resp.responseCode == HttpURLConnection.HTTP_OK) {
            try {
                jsonObject = new JSONObject(resp.responseMessage);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

}
