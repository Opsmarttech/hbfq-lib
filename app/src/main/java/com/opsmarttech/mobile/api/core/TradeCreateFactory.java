package com.opsmarttech.mobile.api.core;

import java.util.Map;

public abstract class TradeCreateFactory<BaseTradeType> {

    protected Map<String, Class<? extends BaseTradeType>> registMap;

    public abstract BaseTradeType createTrade(String key);

    public abstract void registType(Class<? extends BaseTradeType> clazz);

}
