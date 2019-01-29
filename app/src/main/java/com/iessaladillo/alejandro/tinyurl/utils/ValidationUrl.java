package com.iessaladillo.alejandro.tinyurl.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class ValidationUrl {

    private ValidationUrl() {

    }

    public static boolean isValidUrl(String url) {
        return !TextUtils.isEmpty(url) && Patterns.WEB_URL.matcher(url).matches();
    }

}
