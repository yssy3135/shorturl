package com.laundrygo.shorturl.repository;

import com.laundrygo.shorturl.domain.ShortUrl;
import com.laundrygo.shorturl.service.dto.ShortUrlSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface ShortUrlCustomRepository {


    Page<ShortUrl> findAllByConditions(ShortUrlSearchCondition searchCondition);
}
