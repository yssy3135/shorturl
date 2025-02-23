package com.laundrygo.shorturl.repository;

import com.laundrygo.shorturl.domain.ShortUrl;
import com.laundrygo.shorturl.service.dto.ShortUrlSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.EmptyExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.laundrygo.shorturl.domain.QShortUrl.shortUrl;

@Repository
@RequiredArgsConstructor
public class ShortUrlCustomRepositoryImpl implements ShortUrlCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ShortUrl> findAllByConditions(ShortUrlSearchCondition searchCondition) {
        List<ShortUrl> fetchResult = queryFactory
                .selectFrom(shortUrl)
                .orderBy(shortUrl.id.desc())
                .where(
                        condition(searchCondition)
                )
                .offset(searchCondition.getPage() - 1)
                .limit(searchCondition.getSize())
                .fetch();


        JPAQuery<Long> countQuery = queryFactory
                .select(shortUrl.countDistinct())
                .from(shortUrl)
                .orderBy(shortUrl.id.desc())
                .where(
                        condition(searchCondition)
                );


        return PageableExecutionUtils.getPage(
                fetchResult,
                PageRequest.of(
                        searchCondition.getPage() - 1,
                        searchCondition.getSize()
                ),
                countQuery::fetchOne
        );
    }

    public BooleanExpression condition(ShortUrlSearchCondition searchCondition) {
        String searchType = searchCondition.getSearchType();

        switch (searchType) {
            case "ORIGINAL_URL":
                return likeOriginalUrl(searchCondition.getKeyword());
            case "SHORT_URL":
                return likeShortUrl(searchCondition.getKeyword());
            default:
                return allSearchTypeCondition(searchCondition.getKeyword());
        }
    }

    public BooleanExpression allSearchTypeCondition(String keyword) {
        if(keyword == null || keyword.isEmpty()) {
            return null;
        }
        return likeOriginalUrl(keyword)
                .or(likeShortUrl(keyword));
    }

    public BooleanExpression likeOriginalUrl(String originalUrl) {
        if(originalUrl == null || originalUrl.isEmpty()) {
            return null;
        }

        return likeCondition(shortUrl.urlMapping.original);
    }

    public BooleanExpression likeShortUrl(String shortUrlString) {
        if(shortUrlString == null || shortUrlString.isEmpty()) {
            return null;
        }

        return likeCondition(shortUrl.urlMapping.shorten);
    }


    public BooleanExpression likeCondition(StringPath attribute) {
        return attribute.like("%" + attribute + "%");
    }
}
