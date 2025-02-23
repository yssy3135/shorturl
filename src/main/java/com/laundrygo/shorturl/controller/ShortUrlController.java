package com.laundrygo.shorturl.controller;

import com.laundrygo.shorturl.service.ShortUrlService;
import com.laundrygo.shorturl.service.dto.ShortUrlCreateRequest;
import com.laundrygo.shorturl.service.dto.ShortUrlPageResponse;
import com.laundrygo.shorturl.service.dto.ShortUrlResponse;
import com.laundrygo.shorturl.service.dto.ShortUrlSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/shorturls", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ShortUrlResponse createShortUrl(@RequestBody @Valid ShortUrlCreateRequest shortUrlCreateRequest) {

        return shortUrlService.createNewUrl(shortUrlCreateRequest.getOriginalUrl());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/shorturls/short")
    public ShortUrlResponse getShortUrl(@RequestParam(value = "url", required = true) String originalUrl) {
            return shortUrlService.getShortUrl(originalUrl);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/shorturls")
    public ShortUrlPageResponse getShortUrlList(
            @RequestParam String keyword,
            @RequestParam @Valid @Pattern(regexp = "ALL|SHORT_URL|ORIGINAL_URL") String searchType,
            @RequestParam(value = "page", required = false, defaultValue = "1") @Positive Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") @Positive Integer size
    ) {
        return ShortUrlPageResponse.from(
                shortUrlService.getShortUrlList(ShortUrlSearchCondition.of(keyword, searchType, page, size))
        );
    }
}
