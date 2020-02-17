package com.opsmarttech.mobile.api.core;

import com.opsmarttech.mobile.api.core.http.HbfqHttpUtil;
import com.opsmarttech.mobile.api.core.http.HbfqResponse;
import com.opsmarttech.mobile.api.core.http.TradeParam;

public abstract class HbfqTradePayGeneric {

    public abstract HbfqResponse executeTradePayRequset(TradeParam param);

    public HbfqResponse queryTradeResult(String queryUri, String outTradeNo, String tradeType, String deviceSN) {
        return HbfqHttpUtil.query(queryUri, outTradeNo, tradeType, deviceSN);
    }

    public HbfqResponse fetchClientInfo(String queryUri, String storeQrCode, String deviceSN) {
        return HbfqHttpUtil.fetchClientInfo(queryUri, storeQrCode, deviceSN);
    }

}
