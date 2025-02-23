package com.laundrygo.shorturl.service;

import com.laundrygo.shorturl.domain.ShortUrl;
import com.laundrygo.shorturl.domain.Url;
import com.laundrygo.shorturl.repository.ShortUrlRepository;
import com.laundrygo.shorturl.service.dto.ShortUrlResponse;
import com.laundrygo.shorturl.service.dto.ShortUrlSearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final Base62ShortUrlGenerator urlGenerator;

    @Value("${shorturl.baseurl}")
    private String baseUrl;

    public ShortUrlResponse getShortUrl(String originalUrl) {
        ShortUrl shortUrl = shortUrlRepository.findByOriginalUrl(Url.create(originalUrl).getUrlString())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid original url : " + originalUrl));

        increaseRequestCount(shortUrl);

        return ShortUrlResponse.of(shortUrl, baseUrl);
    }

    @Transactional
    public ShortUrlResponse createNewUrl(String requestOriginalUrl) {
        Url originalUrl = Url.create(requestOriginalUrl);
        Optional<ShortUrl> shortUrlOptional = shortUrlRepository.findByOriginalUrl(originalUrl.getUrlString());

        if(shortUrlOptional.isPresent()) {
            ShortUrl shortUrl = shortUrlOptional.get();
            increaseRequestCount(shortUrlOptional.get());
            shortUrlRepository.save(shortUrl);
            return ShortUrlResponse.of(shortUrlOptional.get(), baseUrl);
        }

        ShortUrl shortUrl = ShortUrl.createForIdGeneration();
        shortUrlRepository.save(shortUrl);

        shortUrl.updateUrlMapping(
                originalUrl.getUrlString(),
                urlGenerator.generateShortUrl(shortUrl.getId())
        );
        shortUrl.increaseRequestCount();
        log.info("Short URL created: {}", shortUrl.getUrlMapping().toString());
        return ShortUrlResponse.of(shortUrlRepository.save(shortUrl), baseUrl);
    }

    public Page<ShortUrlResponse> getShortUrlList(ShortUrlSearchCondition searchCondition) {
        return shortUrlRepository.findAllByConditions(
                searchCondition
        ).map(shortUrl -> ShortUrlResponse.of(shortUrl, baseUrl));
    }

    private void increaseRequestCount(ShortUrl shortUrl) {
        shortUrl.increaseRequestCount();

        shortUrlRepository.save(shortUrl);
    }

}
