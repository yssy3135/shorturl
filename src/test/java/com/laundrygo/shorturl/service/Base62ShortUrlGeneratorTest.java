package com.laundrygo.shorturl.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class Base62ShortUrlGeneratorTest {

    @DisplayName("id가 1인 경우 s로 변환되어야 한다.")
    @Test
    public void generateShortUrl() {
        Base62ShortUrlGenerator generator = new Base62ShortUrlGenerator(new Base62Encoder());
        String shortUrl = generator.generateShortUrl(1L);
        assertThat(shortUrl).isEqualTo("s");
    }
}