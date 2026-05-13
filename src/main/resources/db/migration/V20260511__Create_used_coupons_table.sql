CREATE TABLE usedcoupons (
    user_id UUID         NOT NULL,
    code    VARCHAR(255) NOT NULL references coupons(code),

    PRIMARY KEY (user_id, code)
);
