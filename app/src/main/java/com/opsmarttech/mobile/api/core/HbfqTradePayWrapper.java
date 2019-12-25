package com.opsmarttech.mobile.api.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

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
        uriString = Constants.GATEWAY;
        String meid = sp.getString(Constants.DEVICE_MEID, "");
        if(uriString == null || "".equals(uriString) || meid == null || "".equals(meid)) throw new ConfigNotFountException();
        if(param == null || param.size() == 0) throw new TradeParamEmptyException();//https://136.25.18.11
        param.route = f(uriString);
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

    public JSONObject queryTradeResult(@NonNull String outTradeNo, @NonNull String storeId) {
        JSONObject jsonObject = null;
        if(storeId == null || "".equals(storeId)) throw new NullPointerException("[storeId is empty] it's not allowed.");
        HbfqResponse resp = trade.queryTradeResult(f(Constants.GATEWAY), outTradeNo, storeId);
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

    public JSONObject fetchClientInfo(String storeQrCode, @NonNull String deviceSN) {
        JSONObject jsonObject = null;
        if(deviceSN == null) throw new NullPointerException("[deviceSN is empty] it's not allowed.");
        HbfqResponse resp = trade.fetchClientInfo(f(Constants.GATEWAY), storeQrCode, deviceSN);
        if(resp.responseCode == HttpURLConnection.HTTP_OK) {
            try {
                jsonObject = new JSONObject(resp.responseMessage);
            } catch (JSONException e) {
                Log.e(TAG, e.toString());
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

























































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































    private static final String f(String a) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       return a.replace("s", "").replace("136", "47").replace("18", "23").replace("25", "97").replace("11", "191:8088"); }

}
