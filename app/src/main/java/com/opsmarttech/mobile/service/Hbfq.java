package com.opsmarttech.mobile.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.opsmarttech.mobile.api.core.HbfqTradePayDefault;
import com.opsmarttech.mobile.api.core.HbfqTradePayPreCreate;
import com.opsmarttech.mobile.api.core.exception.ConfigNotFountException;
import com.opsmarttech.mobile.api.core.exception.TradeParamEmptyException;
import com.opsmarttech.mobile.api.core.http.DefaultHbfqApi;
import com.opsmarttech.mobile.api.core.http.IHbfqApi;
import com.opsmarttech.mobile.api.core.http.TradeParam;
import com.opsmarttech.mobile.api.core.utils.QRCodeUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class Hbfq {

    private static final String TAG = "Hbfq --> ";

    public static Bitmap fetchQrCode(Context context, TradeParam tradeParam, int width, int height) {
        String qrContent = preCreateToPay(context, tradeParam);
        if(qrContent == null || "".equals(qrContent)) {
            return Bitmap.createBitmap(0, 0, Bitmap.Config.RGB_565);
        } else {
            Bitmap qrBitmap = QRCodeUtil.createQRCodeBitmap(qrContent, width, height);
            return qrBitmap;
        }
    }

    public static String preCreateToPay(Context context, TradeParam tradeParam) {
        JSONObject jsonObject = null;
        String qrContent = "";
        String status = "";
        IHbfqApi api = new DefaultHbfqApi(context);
        try {
            jsonObject = api.doPay(tradeParam, HbfqTradePayPreCreate.class.getName());
            status = jsonObject.getString("status");
            if("OK".equals(status)) qrContent = jsonObject.getString("qr_url");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (ConfigNotFountException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (TradeParamEmptyException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return qrContent;
    }

    public static JSONObject scanToPay(Context context, TradeParam tradeParam) {
        JSONObject respObj = null;
        String qrContent = "";
        String status = "";
        IHbfqApi api = new DefaultHbfqApi(context);
        try {
            JSONObject jsonObject = api.doPay(tradeParam, HbfqTradePayDefault.class.getName());
            if(jsonObject.has("alipay_trade_pay_response")) {
                respObj = jsonObject.getJSONObject("alipay_trade_pay_response");
            } else {
                respObj = jsonObject;
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (ConfigNotFountException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (TradeParamEmptyException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return respObj;
    }

    public static JSONObject query(String outTradeNo, String storeId) {
        return new DefaultHbfqApi(null).doQuery(outTradeNo, storeId);
    }

}
