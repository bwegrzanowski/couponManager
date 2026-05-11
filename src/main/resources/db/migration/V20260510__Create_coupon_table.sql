CREATE TABLE coupons (
    code         VARCHAR(255) PRIMARY KEY,
    version      INT          NOT NULL CONSTRAINT df_version     DEFAULT 0,
    usage_count  INT          NOT NULL CONSTRAINT df_usage_count DEFAULT 0,
    max_usages   INT          NOT NULL,
    created_at   TIMESTAMP    NOT NULL,
    country_code VARCHAR(2)   NOT NULL
);

CREATE UNIQUE INDEX uq_coupon_code
    ON coupons (UPPER(code));
