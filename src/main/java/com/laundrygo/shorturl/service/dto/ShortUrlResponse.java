package com.laundrygo.shorturl.service.dto;

import com.laundrygo.shorturl.domain.ShortUrl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShortUrlResponse {

    private String shorten;

    private String original;

    private Long RequestCount;

    public static ShortUrlResponse of(ShortUrl shortUrl, String domainName) {
        ShortUrlResponse response = new ShortUrlResponse();
        response.shorten = shortUrl.getFullShortUrl(domainName);
        response.original = shortUrl.getOriginalUrl();
        response.RequestCount = shortUrl.getRequestCount();

        return response;
    }

    public static ShortUrlResponse of(
            String shortUrl,
            String originalUrl,
            Long RequestCount
    ) {
        ShortUrlResponse response = new ShortUrlResponse();
        response.shorten = shortUrl;
        response.original = originalUrl;
        response.RequestCount = RequestCount;

        return response;
    }


}
