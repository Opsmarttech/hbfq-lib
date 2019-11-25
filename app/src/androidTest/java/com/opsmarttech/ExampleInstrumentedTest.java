package com.opsmarttech;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.opsmarttech.mobile.api.core.HbfqTradeCreateFactory;
import com.opsmarttech.mobile.api.core.HbfqTradePayDefault;
import com.opsmarttech.mobile.api.core.HbfqTradePayPreCreate;
import com.opsmarttech.mobile.api.core.HbfqTradePayWrapper;
import com.opsmarttech.mobile.api.core.constant.Constants;
import com.opsmarttech.mobile.api.core.http.DefaultHbfqApi;
import com.opsmarttech.mobile.api.core.http.HbfqHttpUtil;
import com.opsmarttech.mobile.api.core.http.HbfqResponse;
import com.opsmarttech.mobile.api.core.http.TradeParam;
import com.opsmarttech.mobile.service.Hbfq;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.opsmarttech", appContext.getPackageName());
    }

    @Test
    public void testPosDevPreCreate() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        //context.getSharedPreferences(Constants.SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE).edit().putString(Constants.PRE_CREATE_ROUTE, "https://136.25.18.11").commit();
        context.getSharedPreferences(Constants.SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE).edit().putString(Constants.DEVICE_MEID, "A000009B09D368").commit();
        TradeParam tradeParam = new TradeParam();
        tradeParam.put("totalMount", "100");
        tradeParam.put("hbfqSellerPercent", "100");
        tradeParam.put("hbfqPhaseNum", "12");
        String qrUrl = null;
        try {
            qrUrl = Hbfq.preCreateToPay(context, tradeParam).getString("qr_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertEquals(true, !"".equals(qrUrl));
    }

    @Test
    public void testPosDevPreCreate2() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        context.getSharedPreferences(Constants.SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE).edit().putString(Constants.PRE_CREATE_ROUTE, "https://136.25.18.11").commit();
        context.getSharedPreferences(Constants.SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE).edit().putString(Constants.DEVICE_MEID, "666554423").commit();
        TradeParam tradeParam = new TradeParam();
        tradeParam.put("totalMount", "100");
        tradeParam.put("hbfqSellerPercent", "100");
        tradeParam.put("hbfqPhaseNum", "12");
        JSONObject resp = Hbfq.preCreateToPay(context, tradeParam);
        String status = null;
        try {
            status = resp.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertEquals(true, "device unbind".equals(status));
    }

    @Test
    public void testPosDevScan() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        context.getSharedPreferences(Constants.SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE).edit().putString(Constants.PRE_CREATE_ROUTE, "https://136.25.18.11").commit();
        context.getSharedPreferences(Constants.SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE).edit().putString(Constants.DEVICE_MEID, "666554423").commit();
        TradeParam tradeParam = new TradeParam();
        tradeParam.put("totalMount", "0.1");
        tradeParam.put("hbfqSellerPercent", "100");
        tradeParam.put("hbfqPhaseNum", "12");
        tradeParam.put("authCode", "285476910192082196");
        JSONObject jsonObject = Hbfq.scanToPay(context, tradeParam);
        String resp = "";
        try {
            resp = jsonObject.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertEquals(true, "device unbind".equals(resp));
    }

    @Test
    public void testPosDevScan2() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        context.getSharedPreferences(Constants.SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE).edit().putString(Constants.PRE_CREATE_ROUTE, "https://136.25.18.11").commit();
        context.getSharedPreferences(Constants.SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE).edit().putString(Constants.DEVICE_MEID, "A000009B09D368").commit();
        TradeParam tradeParam = new TradeParam();
        tradeParam.put("totalMount", "0.2");
        tradeParam.put("hbfqSellerPercent", "100");
        tradeParam.put("hbfqPhaseNum", "12");
        tradeParam.put("authCode", "285476910192082196");
        JSONObject jsonObject = Hbfq.scanToPay(context, tradeParam);
        String code = "";
        try {
            code = jsonObject.getString("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertEquals(true, "10003".equals(code));
    }

    @Test
    public void testQuery() {
        JSONObject jsonObject = Hbfq.query("5510b6db_0326_4e2b_b009_fe5e81516720", "2088901200045560");
        String tradeResult = "";
        try {
            JSONObject queryJson = jsonObject.getJSONObject("alipay_trade_query_response");
            tradeResult = queryJson.getString("trade_status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertEquals(true, "TRADE_SUCCESS".equals(tradeResult));
    }

}
