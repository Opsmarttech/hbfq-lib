package com.opsmarttech.mobile.api.core.http;

import org.json.JSONObject;

public interface IHbfqApi {

    public JSONObject doPay(TradeParam param, String action);
    public JSONObject doQuery(String outTradeNo);

}
