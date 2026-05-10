CREATE TABLE coupons (
    id           BIGSERIAL    PRIMARY KEY,
    version      INT          NOT NULL CONSTRAINT df_version DEFAULT 0,
    code         VARCHAR(255) NOT NULL,
    max_usages   INT          NOT NULL,
    created_at   TIMESTAMP    NOT NULL,
    country_code VARCHAR(2)   NOT NULL
);

CREATE UNIQUE INDEX uq_coupon_code
    ON coupons (UPPER(code));

CREATE INDEX idx_coupon_code
    ON coupons (code);
