package com.opsmarttech.mobile.api.core.http;

import android.content.Context;

import com.opsmarttech.mobile.api.core.HbfqTradeCreateFactory;
import com.opsmarttech.mobile.api.core.HbfqTradePayDefault;
import com.opsmarttech.mobile.api.core.HbfqTradePayPreCreate;
import com.opsmarttech.mobile.api.core.HbfqTradePayWrapper;

import org.json.JSONObject;

public class DefaultHbfqApi implements IHbfqApi {

    private Context context;

    protected void init() {
        HbfqTradeCreateFactory.getInstance().registType(HbfqTradePayDefault.class);
        HbfqTradeCreateFactory.getInstance().registType(HbfqTradePayPreCreate.class);
    }

    public DefaultHbfqApi(Context context) {
        this.context = context;
        init();
    }

    @Override
    public JSONObject doPay(TradeParam param, String action) {
        return new HbfqTradePayWrapper(HbfqTradeCreateFactory.getInstance().createTrade(action)).executeTradePayRequset(context, param);
    }

    @Override
    public JSONObject doQuery(TradeParam param) {
        return null;
    }

}
