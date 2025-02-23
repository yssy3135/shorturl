package com.laundrygo.shorturl.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Base62ShortUrlGenerator implements ShortUrlGenerator {

    private final Base62Encoder encoder;

    public String generateShortUrl(Long id) {
        return encoder.encode(id);
    }
}
