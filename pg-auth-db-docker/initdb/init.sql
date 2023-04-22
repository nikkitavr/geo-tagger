CREATE TABLE public.roles (
    id bigint NOT NULL,
    created_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone,
    role_title character varying(255)
);

CREATE SEQUENCE public.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.roles_users (
    role_id bigint NOT NULL,
    user_id bigint NOT NULL,
    id bigint NOT NULL
);

ALTER TABLE public.roles_users ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.roles_users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1
);

CREATE TABLE public.users (
    id bigint NOT NULL,
    created_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone,
    email character varying(255),
    login character varying(255),
    password character varying(255),
    phone_number character varying(255)
);


CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


ALTER TABLE ONLY public.roles_users
    ADD CONSTRAINT roles_users_id_pk PRIMARY KEY (id);


ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


ALTER TABLE ONLY public.roles_users
    ADD CONSTRAINT fklkcn1l0gnfshcn4rnmak73ta FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


ALTER TABLE ONLY public.roles_users
    ADD CONSTRAINT fksmos02hm32191ogexm2ljik9x FOREIGN KEY (role_id) REFERENCES public.roles(id) ON DELETE CASCADE;
