package com.opsmarttech.mobile.api.core;

import com.opsmarttech.mobile.api.core.http.HbfqHttpUtil;
import com.opsmarttech.mobile.api.core.http.HbfqResponse;
import com.opsmarttech.mobile.api.core.http.TradeParam;

public class LbfqTradePayForm extends HbfqTradePayGeneric {

    @Override
    public HbfqResponse executeTradePayRequset(TradeParam param) {
        param.put("tradeType", "pos_form");
        return HbfqHttpUtil.post(param);
    }

}
