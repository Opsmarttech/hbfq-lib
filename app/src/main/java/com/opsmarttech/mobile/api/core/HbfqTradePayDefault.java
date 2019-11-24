package com.opsmarttech.mobile.api.core;

import com.opsmarttech.mobile.api.core.http.HbfqHttpUtil;
import com.opsmarttech.mobile.api.core.http.HbfqResponse;
import com.opsmarttech.mobile.api.core.http.TradeParam;

public class HbfqTradePayDefault extends HbfqTradePayGeneric {

    @Override
    public HbfqResponse executeTradePayRequset(TradeParam param) {
        param.put("tradeType", "pos_scan");
        return HbfqHttpUtil.post(param);
    }

}
