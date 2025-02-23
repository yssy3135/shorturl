package com.laundrygo.shorturl.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class UrlTest {


    @DisplayName("Domain에 대문자가 포함되어 있을 경우 소문자로 변경되어야 한다.")
    @Test
    public void convertDomainLowerCase() {
        Url url = Url.create("WWW.GOOGLE.COM");
        Assertions.assertThat(url.getUrlString()).isEqualTo("www.google.com");
    }

    @DisplayName("Domain에 대문자가 포함되어 있을 경우 도메인 이름만 소문자로 변경되어야 한다.")
    @Test
    public void convertDomainLowerCaseWithSlash() {
        Url url = Url.create("WWW.GOOGLE.COM/TEST");
        Assertions.assertThat(url.getUrlString()).isEqualTo("www.google.com/TEST");
    }

    @DisplayName("URL에 프로토콜이 포함되어 있을 경우 제거되어야 한다.")
    @Test
    public void removeHttps() {
        Url url = Url.create("https://www.google.com");
        Assertions.assertThat(url.getUrlString()).isEqualTo("www.google.com");
    }

    @DisplayName("URL 마지막에 슬래시가 포함되어 있을 경우 제거되어야 한다.")
    @Test
    public void removeLastSlash() {
        Url url = Url.create("https://www.google.com/");
        Assertions.assertThat(url.getUrlString()).isEqualTo("www.google.com");
    }
}
