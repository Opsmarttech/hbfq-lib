package com.opsmarttech.mobile.api.core.http;

import com.opsmarttech.mobile.api.core.constant.Constants;
import com.opsmarttech.mobile.service.Hbfq;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HbfqHttpUtil {

    public static HbfqResponse post(TradeParam param) {

        HbfqResponse response = new HbfqResponse();

        try {

            JSONObject body = new JSONObject();
            for(String key : param.keySet()) {
                body.put(key, param.get(key));
            }
            response = doRequest(param.route + Constants.METHOD, body);

        } catch(JSONException e) {

            e.printStackTrace();

        }

        return response;
    }

    public static HbfqResponse query(String queryUri, String outTradeNo, String tradeType, String deviceSN) {

        HbfqResponse response = new HbfqResponse();

        try {

            JSONObject queryParam = new JSONObject();
            queryParam.put("outTradeNo", outTradeNo);
            queryParam.put("tradeType", tradeType);
            queryParam.put("deviceSN", deviceSN);
            response = doRequest(queryUri + Constants.QUERY, queryParam);

        } catch(JSONException e) {

            e.printStackTrace();

        }

        return response;
    }

    public static HbfqResponse fetchClientInfo(String queryUri, String storeQrCode, String deviceSN) {
        HbfqResponse response = new HbfqResponse();

        try {

            JSONObject queryParam = new JSONObject();
            queryParam.put("storeQrCode", storeQrCode);
            queryParam.put("deviceSN", deviceSN);
            response = doRequest(queryUri + Constants.CLIENTINFO, queryParam);

        } catch(JSONException e) {

            e.printStackTrace();

        }

        return response;
    }

    private static HbfqResponse doRequest(String uri, JSONObject body) {

        URL url = null;
        HttpURLConnection conn = null;
        HbfqResponse response = new HbfqResponse();

        try {
            url = new URL(uri);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            try (DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream())) {
                dataOutputStream.writeBytes(String.valueOf(body));
                dataOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

            int respCode = conn.getResponseCode();
            response.responseCode = respCode;

            if(respCode == HttpURLConnection.HTTP_OK) {
                try(InputStreamReader in = new InputStreamReader(conn.getInputStream())) {
                    BufferedReader bf = new BufferedReader(in);
                    String recieveData = "";
                    String result = "";
                    while ((recieveData = bf.readLine()) != null){
                        result += recieveData + "\n";
                    }
                    response.responseMessage = result;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }

}
