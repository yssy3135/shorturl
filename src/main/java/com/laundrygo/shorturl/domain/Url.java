package com.laundrygo.shorturl.domain;

public class Url {

    private String urlText;

    private final String SLASH = "/";

    private Url(String urlText) {
        this.urlText = urlText;
        removeHttpProtocol();
        convertDomainLowerCase();
        removeLastSlash();
    }

    private void removeLastSlash() {
        if (urlText.endsWith(SLASH)) {
            urlText = urlText.substring(0, urlText.length() - 1);
        }
    }

    public static Url create(String urlString) {
        Url url = new Url(urlString);
        return url;
    }

    private void removeHttpProtocol() {
        if (urlText.startsWith("https://")) {
            urlText =  urlText.substring(8);
        }

        if (urlText.startsWith("http://")) {
            urlText = urlText.substring(7);
        }
    }

    private void convertDomainLowerCase() {

        if(isNotContainHyphen()) {
            urlText = urlText.toLowerCase();
            return ;
        }

        int firstHyphenIdx = urlText.indexOf(SLASH);
        urlText = urlText.substring(0, firstHyphenIdx).toLowerCase() + urlText.substring(firstHyphenIdx);
    }

    private boolean isNotContainHyphen() {
        return !urlText.contains(SLASH);
    }


    public String getUrlString() {
        return urlText;
    }


}
