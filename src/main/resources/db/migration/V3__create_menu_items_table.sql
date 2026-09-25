CREATE SEQUENCE s_menu_items
    INCREMENT BY 1
    MINVALUE 1
    START 1;



CREATE TABLE public.menu_items
(
    id                       BIGINT                   NOT NULL PRIMARY KEY,
    name                     VARCHAR(255)             NOT NULL,
    description              TEXT                     NOT NULL,
    price                    NUMERIC(12, 2)           NOT NULL,
    image_key                VARCHAR(255)             NOT NULL,
    is_available             BOOLEAN                  NOT NULL,
    preparation_time_minutes INTEGER                  NOT NULL,
    restaurant_id            BIGINT                   NOT NULL,
    category_id              BIGINT                   NOT NULL,
    created_at               TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at               TIMESTAMP WITH TIME ZONE,
    deleted_at               TIMESTAMP WITH TIME ZONE,
    is_deleted               BOOLEAN                  NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_menu_items_restaurant
        FOREIGN KEY (restaurant_id) REFERENCES public.restaurants (id),
    CONSTRAINT fk_menu_items_category
        FOREIGN KEY (category_id) REFERENCES public.menu_category (id)
);

CREATE INDEX ix_menu_items_restaurant_id
    ON menu_items (restaurant_id);

CREATE INDEX ix_menu_items_category_id
    ON menu_items (category_id);
