package com.laundrygo.shorturl.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
public class UrlMapping {

    @Column(name = "original")
    private String original;

    @Column(name = "shorten")
    private String shorten;

    public static UrlMapping create(String originalUrl, String shortUrl) {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.original = originalUrl;
        urlMapping.shorten = shortUrl;
        return urlMapping;
    }
    public String getShorten() {
        return shorten;
    }

    public String getOriginal() {
        return original;
    }

    @Override
    public String toString() {
        return "UrlMapping{" +
                "originalUrl='" + original + '\'' +
                ", shortUrl='" + shorten + '\'' +
                '}';
    }
}
