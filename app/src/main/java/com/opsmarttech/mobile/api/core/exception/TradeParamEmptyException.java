package com.opsmarttech.mobile.api.core.exception;

import androidx.annotation.Nullable;

public class TradeParamEmptyException extends RuntimeException {

    @Nullable
    @Override
    public String getMessage() {
        return "No params in it is not allowed !";
    }
}
