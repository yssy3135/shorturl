package com.laundrygo.shorturl.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "short_url")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    UrlMapping urlMapping;

    @Column(name = "request_count")
    private Long requestCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    public static ShortUrl createForIdGeneration() {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.requestCount = 0L;
        shortUrl.createdAt = LocalDateTime.now();
        return shortUrl;
    }

    public void updateUrlMapping(
            String originalUrl,
            String shortUrl
    ) {
        this.urlMapping = UrlMapping.create(originalUrl, shortUrl);
    }



    public void increaseRequestCount() {
        requestCount++;
    }

    public String getShortUrlString() {
        return urlMapping.getShorten();
    }

    public String getOriginalUrl() {
        return urlMapping.getOriginal();
    }


    public String getFullShortUrl(String domain) {
        return domain + "/" + urlMapping.getShorten();
    }

    @Override
    public String toString() {
        return "ShortUrl{" +
                "id=" + id +
                ", urlMapping=" + urlMapping +
                ", requestCount=" + requestCount +
                ", createdAt=" + createdAt +
                '}';
    }
}
