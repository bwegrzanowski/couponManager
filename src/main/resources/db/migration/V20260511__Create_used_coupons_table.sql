CREATE TABLE usedcoupons (
    id      BIGSERIAL    PRIMARY KEY,
    user_id UUID         NOT NULL,
    code    VARCHAR(255) NOT NULL references coupons(code)
);

CREATE INDEX idx_usedCoupons_userId_code
    ON usedcoupons (user_id, code);

CREATE UNIQUE INDEX uq_userId_code
    ON usedcoupons (user_id, code);
