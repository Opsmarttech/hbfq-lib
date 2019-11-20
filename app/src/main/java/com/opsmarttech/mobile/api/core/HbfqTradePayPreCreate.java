package com.opsmarttech.mobile.api.core;

import com.opsmarttech.mobile.api.core.http.HbfqHttpUtil;
import com.opsmarttech.mobile.api.core.http.HbfqResponse;
import com.opsmarttech.mobile.api.core.http.TradeParam;

public class HbfqTradePayPreCreate extends HbfqTradePayGeneric {

    @Override
    public HbfqResponse executeTradePayRequset(TradeParam param) {
        param.put("tradeType", "pos_qr");
        return HbfqHttpUtil.post(param);
    }

    @Override
    public Object queryTradeResult(String storeId, String tradeNum) {
        return null;
    }

}
