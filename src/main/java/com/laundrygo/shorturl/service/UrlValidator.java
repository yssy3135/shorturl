package com.laundrygo.shorturl.service;

import java.net.URL;
import java.net.URLConnection;

public class UrlValidator {
    public static boolean validate(String url) {
        if(url == null || url.isEmpty()) {
            return false;
        }
        try {
            URL connectionUrl = new URL(url);
            URLConnection conn = connectionUrl.openConnection();
            conn.connect();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid URL");
        }
        return true;
    }
}
