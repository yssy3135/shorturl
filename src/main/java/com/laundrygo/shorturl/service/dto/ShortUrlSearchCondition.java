package com.laundrygo.shorturl.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShortUrlSearchCondition {

    private String keyword;

    private String searchType;

    private Integer page;

    private Integer size;

    public static ShortUrlSearchCondition of(String keyword, String searchType, Integer page, Integer size) {
        ShortUrlSearchCondition shortUrlSearchCondition = new ShortUrlSearchCondition();
        shortUrlSearchCondition.keyword = keyword;
        shortUrlSearchCondition.searchType = searchType;
        shortUrlSearchCondition.page = page;
        shortUrlSearchCondition.size = size;
        return shortUrlSearchCondition;
    }



}
