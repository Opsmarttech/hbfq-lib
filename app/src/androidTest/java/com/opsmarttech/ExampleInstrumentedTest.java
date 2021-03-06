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
        context.getSharedPreferences(Constants.SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE).edit().putString(Constants.DEVICE_MEID, "HYTBB18A12507991").commit();
        TradeParam tradeParam = new TradeParam();
        tradeParam.put("totalMount", "100");
        tradeParam.put("hbfqSellerPercent", "100");
        tradeParam.put("hbfqPhaseNum", "12");
        tradeParam.put("merchantType", Constants.TRADE_TYPE_HB);
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
        tradeParam.put("merchantType", Constants.TRADE_TYPE_HB);
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
    public void testPosDevForm() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        context.getSharedPreferences(Constants.SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE).edit().putString(Constants.PRE_CREATE_ROUTE, "https://136.25.18.11").commit();
        context.getSharedPreferences(Constants.SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE).edit().putString(Constants.DEVICE_MEID, "PE18197C61939").commit();
        TradeParam tradeParam = new TradeParam();
        tradeParam.put("totalMount", "100000");
        tradeParam.put("storeId", "GZW-001");
        tradeParam.put("insNum", "6");
        JSONObject jsonObject = Hbfq.formToPay(context, tradeParam);
        String url = "";
        try {
            url = jsonObject.getString("formUrl");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertEquals(true, url != null ? true : false);
    }

    @Test
    public void testQuery() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        JSONObject jsonObject = Hbfq.query(context, "0431f864_16ac_4cee_9a2b_ff68a24920fc", "001");
        String tradeResult = "";
        try {
            JSONObject queryJson = jsonObject.getJSONObject("alipay_trade_query_response");
            tradeResult = queryJson.getString("trade_status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertEquals(true, "TRADE_SUCCESS".equals(tradeResult));
    }

    @Test
    public void testFetchClientInfo() {
        JSONObject jsonObject = Hbfq.fetchClientInfo(null, "123123123");
        String clientTitle = null;
        try {
            clientTitle = jsonObject.getString("clientTitle");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(true, "[error : device unbind]".equals(clientTitle));
    }

}
