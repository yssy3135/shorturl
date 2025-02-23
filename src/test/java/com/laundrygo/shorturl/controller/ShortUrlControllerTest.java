package com.laundrygo.shorturl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laundrygo.shorturl.repository.ShortUrlRepository;
import com.laundrygo.shorturl.service.ShortUrlService;
import com.laundrygo.shorturl.service.dto.ShortUrlCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SqlGroup({
        @Sql(value = "/shorturl-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/delete-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
})
@SpringBootTest
class ShortUrlControllerTest {

    @Autowired
    ShortUrlController shortUrlController;

    @Autowired
    ShortUrlService shortUrlService;

    @Autowired
    ShortUrlRepository shortUrlRepository;

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(shortUrlController)
                .build();
    }

    @DisplayName("Short URL을 생성한다.")
    @Test
    public void createShortUrl() throws Exception {
        // given
        String url = "https://www.laundrygo.com";

        // when
        mockMvc.perform(post("/shorturls")
                        .content(
                                mapper.writeValueAsString(
                                        new ShortUrlCreateRequest(url)
                                ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$.original").value("www.laundrygo.com"))
                .andExpect(jsonPath("$.shorten").isNotEmpty())
                .andExpect(jsonPath("$.requestCount").value("1"))
                .andExpect(status().isCreated());
    }

    @DisplayName("Short URL을 조회한다.")
    @Test
    public void getShortUrl() throws Exception {
        // given
        String url = "https://www.google.com";

        // when
        mockMvc.perform(get("/shorturls/short")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("url", url)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.original").value("www.google.com"))
                .andExpect(jsonPath("$.shorten").value("localhost:8080/w"))
                .andExpect(jsonPath("$.requestCount").value("11"));


    }


    @DisplayName("Original URL을 조회한다.")
    @Test
    public void getOriginalUrl() throws Exception {
        // given
        String url = "s";

        // when
        mockMvc.perform(get("/shorturls/original")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("url", url)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.original").value("www.google.com"))
                .andExpect(jsonPath("$.shorten").value("localhost:8080/w"))
                .andExpect(jsonPath("$.requestCount").value("11"));


    }

    @DisplayName("ShortURL 리스트를 조회한다.")
    @Test
    public void getShortUrlList() throws Exception {
        // given
        String keyword = "";
        String searchType = "ALL";
        int page = 1;
        int size = 10;

        // when
        mockMvc.perform(get("/shorturls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("keyword", keyword)
                        .param("searchType", searchType)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrls").isArray())
                .andExpect(jsonPath("$.shortUrls[0].original").value("www.netflix.com"))
                .andExpect(jsonPath("$.shortUrls[0].shorten").value("localhost:8080/4"))
                .andExpect(jsonPath("$.shortUrls[0].requestCount").value("1"))
                .andExpect(jsonPath("$.totalCount").value(5));
    }


}