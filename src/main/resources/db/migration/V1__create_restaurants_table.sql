CREATE SEQUENCE s_restaurant
    INCREMENT BY 1
    MINVALUE 1
    START 1;



CREATE TABLE public.restaurants
(
    id          BIGINT                   NOT NULL PRIMARY KEY,
    name        VARCHAR(255)             NOT NULL,
    description TEXT,
    address     VARCHAR(255)             NOT NULL,
    phone       VARCHAR(30)              NOT NULL,
    status      VARCHAR(50)              NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE,
    deleted_at  TIMESTAMP WITH TIME ZONE,
    is_deleted  BOOLEAN DEFAULT FALSE
);

CREATE UNIQUE INDEX ux_restaurants_phone_active
    ON restaurants (phone)
    WHERE is_deleted = false;