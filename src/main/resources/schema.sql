DROP TABLE IF EXISTS short_url;
create table short_url
(
    id            BIGINT auto_increment primary key,
    created_at    TIMESTAMP,
    request_count BIGINT,
    original    VARCHAR(255),
    shorten     VARCHAR(255)
);

