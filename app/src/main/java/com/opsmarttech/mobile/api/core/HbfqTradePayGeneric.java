package com.opsmarttech.mobile.api.core;

import com.opsmarttech.mobile.api.core.http.HbfqResponse;
import com.opsmarttech.mobile.api.core.http.TradeParam;

public abstract class HbfqTradePayGeneric {

    public abstract HbfqResponse executeTradePayRequset(TradeParam param);
    public abstract Object queryTradeResult(String storeId, String tradeNum);

}
