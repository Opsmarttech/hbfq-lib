package com.opsmarttech.mobile.api.core;

import com.opsmarttech.mobile.api.core.http.HbfqHttpUtil;
import com.opsmarttech.mobile.api.core.http.HbfqResponse;
import com.opsmarttech.mobile.api.core.http.TradeParam;

public class LbfqTradePayForm extends HbfqTradePayGeneric {

//    @Override
//    public HbfqResponse executeTradePayRequset(TradeParam param) {
//        HbfqResponse response = new HbfqResponse();
//        response.responseCode = HttpURLConnection.HTTP_OK;
//        response.responseMessage = "{\"fromUrl\":\"" + param.route + "/lbf/doPay/" + param.get("totalAmount") + "/" + param.get("lbfStoreId") + "/"+ param.get("insNum") +"?tradeFrom=pos_qr\"}";
//        return response;
//    }

    @Override
    public HbfqResponse executeTradePayRequset(TradeParam param) {
        param.put("tradeType", "form_lbf");
        return HbfqHttpUtil.post(param);
    }


}
