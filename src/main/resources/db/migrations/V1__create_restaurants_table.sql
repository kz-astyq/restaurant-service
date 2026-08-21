CREATE SEQUENCE s_restaurant
    INCREMENT BY 1
    MINVALUE 1
    START 1;


CREATE TABLE public.common_subjects
(

    created_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    is_deleted SMALLINT,
    updated_at TIMESTAMP,
    name_key   VARCHAR(255)
);

CREATE TABLE restaurants
(
    id          BIGINT                   NOT NULL PRIMARY KEY,
    name        VARCHAR(255)             NOT NULL,
    description TEXT,
    address     VARCHAR(255)             NOT NULL,
    phone       VARCHAR(30)              NOT NULL UNIQUE,
    status      VARCHAR(50)              NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE,
    deleted_at  TIMESTAMP WITH TIME ZONE
);