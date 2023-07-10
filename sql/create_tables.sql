CREATE TABLE public.investor_details
(
    id bigint,
    name character varying NOT NULL,
    surname character varying NOT NULL,
    dob character varying,
    address character varying,
    mobile character varying NOT NULL,
    email character varying,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.investor_details
    OWNER to postgres;

CREATE TABLE public.investor_products
(
    id bigint,
    product_type character varying NOT NULL,
    current_balance bigint,
    investor_id bigint,
    PRIMARY KEY (id),
    CONSTRAINT fk_investor_id FOREIGN KEY (investor_id)
        REFERENCES public.investor_details (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS public.investor_products
    OWNER to postgres;
