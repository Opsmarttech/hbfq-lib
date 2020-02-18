package com.opsmarttech.mobile.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.opsmarttech.mobile.api.core.HbfqTradePayDefault;
import com.opsmarttech.mobile.api.core.HbfqTradePayPreCreate;
import com.opsmarttech.mobile.api.core.LbfqTradePayForm;
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
        JSONObject jsonObject = preCreateToPay(context, tradeParam);

        Bitmap bitmap = null;

        try {
            String status = jsonObject.getString("status");
            if("OK".equals(status)) {
                String qrContent = jsonObject.getString("qr_url");

                if(qrContent == null || "".equals(qrContent)) {
                    bitmap = Bitmap.createBitmap(0, 0, Bitmap.Config.RGB_565);
                } else {
                    bitmap = QRCodeUtil.createQRCodeBitmap(qrContent, width, height);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static JSONObject preCreateToPay(Context context, TradeParam tradeParam) {
        JSONObject jsonObject = null;
        IHbfqApi api = new DefaultHbfqApi(context);
        try {
            jsonObject = api.doPay(tradeParam, HbfqTradePayPreCreate.class.getName());
        } catch (ConfigNotFountException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (TradeParamEmptyException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return jsonObject;
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

    public static JSONObject formToPay(Context context, TradeParam tradeParam) {
        JSONObject jsonObject = null;
        IHbfqApi api = new DefaultHbfqApi(context);
        try {
            jsonObject = api.doPay(tradeParam, LbfqTradePayForm.class.getName());
        } catch (ConfigNotFountException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (TradeParamEmptyException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject query(Context context, String outTradeNo, String tradeType) {
        return new DefaultHbfqApi(context).doQuery(outTradeNo, tradeType);
    }

    public static JSONObject fetchClientInfo(String qrCode, String deviceSN) {
        return new DefaultHbfqApi(null).doFetchClientInfo(qrCode, deviceSN);
    }

}
