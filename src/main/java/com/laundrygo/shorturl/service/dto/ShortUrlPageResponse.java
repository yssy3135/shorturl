package com.laundrygo.shorturl.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShortUrlPageResponse {

    List<ShortUrlResponse> shortUrls;

    Long totalCount;

    public static ShortUrlPageResponse from(Page<ShortUrlResponse> shortUrl) {
        ShortUrlPageResponse shortUrlPageResponse = new ShortUrlPageResponse();
        shortUrlPageResponse.shortUrls = shortUrl.getContent();
        shortUrlPageResponse.totalCount = shortUrl.getTotalElements();

        return shortUrlPageResponse;
    }
}
