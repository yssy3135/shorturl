package com.laundrygo.shorturl.repository;

import com.laundrygo.shorturl.domain.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long>, ShortUrlCustomRepository {

    @Query("SELECT u " +
            "FROM ShortUrl u " +
            "WHERE u.urlMapping.original = :originalUrl")
    Optional<ShortUrl> findByOriginalUrl(String originalUrl);

    @Query("SELECT u " +
            "FROM ShortUrl u " +
            "WHERE u.urlMapping.shorten = :shortUrl")
    Optional<ShortUrl> findByShortUrl(String shortUrl);
}
