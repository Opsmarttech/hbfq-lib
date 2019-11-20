package com.opsmarttech.mobile.api.core.http;

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

        URL url = null;
        HttpURLConnection conn = null;
        HbfqResponse response = new HbfqResponse();

        try {

            url = new URL(param.route);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            JSONObject body = new JSONObject();

            for(String key : param.keySet()) {
                body.put(key, param.get(key));
            }

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
        } catch(JSONException e) {
            e.printStackTrace();
        }

        return response;
    }

}
