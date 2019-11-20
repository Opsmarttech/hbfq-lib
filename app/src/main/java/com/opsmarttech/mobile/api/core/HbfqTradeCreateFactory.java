package com.opsmarttech.mobile.api.core;

import java.util.HashMap;

public class HbfqTradeCreateFactory extends TradeCreateFactory<HbfqTradePayGeneric> {

    private static HbfqTradeCreateFactory self;

    private HbfqTradeCreateFactory() {
        registMap = new HashMap();
    }

    public static HbfqTradeCreateFactory getInstance() {
        if(self == null) {
            synchronized (HbfqTradeCreateFactory.class) {
                if(self == null) {
                    self = new HbfqTradeCreateFactory();
                }
            }
        }
        return self;
    }

    @Override
    public HbfqTradePayGeneric createTrade(String key) {
        HbfqTradePayGeneric hbfqTradePayGeneric = null;
        try {
            hbfqTradePayGeneric = registMap.get(key).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return hbfqTradePayGeneric;
    }

    @Override
    public void registType(Class<? extends HbfqTradePayGeneric> clazz) {
        if(registMap.get(clazz.getName()) != null)  return;
        registMap.put(clazz.getName(), clazz);
    }

}
