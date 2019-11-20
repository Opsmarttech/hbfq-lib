package com.opsmarttech.mobile.api.core.exception;

import androidx.annotation.Nullable;

public class ConfigNotFountException extends RuntimeException {

    @Nullable
    @Override
    public String getMessage() {
        return "ConfigNotFoundException: please config opsmarttech-hbfq gateway and your store id first !";
    }
}
