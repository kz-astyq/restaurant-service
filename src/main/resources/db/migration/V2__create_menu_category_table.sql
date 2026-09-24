CREATE SEQUENCE s_menu_category
    INCREMENT BY 1
    MINVALUE 1
    START 1;



CREATE TABLE public.menu_category
(
    id            BIGINT                   NOT NULL PRIMARY KEY,
    name          VARCHAR(100)             NOT NULL,
    display_order INTEGER                  NOT NULL,
    restaurant_id BIGINT                   NOT NULL,
    created_at    TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITH TIME ZONE,
    deleted_at    TIMESTAMP WITH TIME ZONE,
    is_deleted    BOOLEAN                  NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_menu_category_restaurant
        FOREIGN KEY (restaurant_id) REFERENCES public.restaurants (id)
);

CREATE INDEX ix_menu_category_restaurant_id
    ON menu_category (restaurant_id);

CREATE UNIQUE INDEX ux_menu_category_restaurant_name_active
    ON menu_category (restaurant_id, name)
    WHERE is_deleted = false;
