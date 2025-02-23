package com.laundrygo.shorturl.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShortUrlTest {

    @DisplayName("Id를 가져오기위한 팩토리 메서드는 created_at값만 설정한다")
    @Test
    public void createForIdGeneration() {
        ShortUrl shortUrl = ShortUrl.createForIdGeneration();


        assertThat(shortUrl.getCreatedAt()).isNotNull();
        assertThat(shortUrl.getUrlMapping()).isNull();
        assertThat(shortUrl.getRequestCount()).isEqualTo(0L);
    }


    @DisplayName("ShortUrl의 URL 매핑을 업데이트한다")
    @Test
    public void updateUrlMapping() {
        ShortUrl shortUrl = ShortUrl.createForIdGeneration();
        shortUrl.updateUrlMapping("http://www.google.com", "ws");

        assertThat(shortUrl.getUrlMapping().getOriginal()).isEqualTo("http://www.google.com");
        assertThat(shortUrl.getUrlMapping().getShorten()).isEqualTo("ws");
    }
}
