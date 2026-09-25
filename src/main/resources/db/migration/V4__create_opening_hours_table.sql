CREATE SEQUENCE IF NOT EXISTS s_opening_hours START WITH 1 INCREMENT BY 1;

CREATE TABLE opening_hours
(
    id            BIGINT PRIMARY KEY DEFAULT nextval('s_opening_hours'),
    restaurant_id BIGINT     NOT NULL REFERENCES restaurants (id) ON DELETE CASCADE,
    day_of_week   VARCHAR(9) NOT NULL,
    open_time     TIME       NOT NULL,
    close_time    TIME       NOT NULL,

    CONSTRAINT chk_opening_hours_day CHECK (day_of_week IN
                                            ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY')),
    CONSTRAINT chk_opening_hours_time CHECK (open_time <> close_time)
);

ALTER SEQUENCE s_opening_hours OWNED BY opening_hours.id;

CREATE INDEX ix_opening_hours_restaurant_day
    ON opening_hours (restaurant_id, day_of_week);