CREATE TYPE relationship_status AS ENUM (
    'INVITED',
    'DECLINED',
    'FRIEND'
);

CREATE CAST (character varying AS relationship_status) WITH INOUT AS IMPLICIT;

CREATE TABLE relationships (
    user_id bigint NOT NULL,
    friend_id bigint NOT NULL,
    id bigint NOT NULL,
    created_date timestamp without time zone,
    updated_date timestamp without time zone,
    status relationship_status NOT NULL
);

ALTER TABLE relationships ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME friends_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1
);

CREATE TABLE users (
    id bigint NOT NULL,
    login character varying NOT NULL,
    email character varying(254) NOT NULL,
    phone_number character varying(20) NOT NULL,
    created_date timestamp without time zone,
    updated_date timestamp without time zone
);

ALTER TABLE users ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1
);

ALTER TABLE ONLY relationships
    ADD CONSTRAINT friends_pk PRIMARY KEY (id);


ALTER TABLE ONLY relationships
    ADD CONSTRAINT unique_relation UNIQUE (user_id, friend_id);


ALTER TABLE ONLY users
    ADD CONSTRAINT users_email_key UNIQUE (email);


ALTER TABLE ONLY users
    ADD CONSTRAINT users_login_key UNIQUE (login);



ALTER TABLE ONLY users
    ADD CONSTRAINT users_phone_number_key UNIQUE (phone_number);



ALTER TABLE ONLY users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);



ALTER TABLE ONLY relationships
    ADD CONSTRAINT friend_id FOREIGN KEY (friend_id) REFERENCES users(id) ON UPDATE RESTRICT ON DELETE CASCADE;



ALTER TABLE ONLY relationships
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users(id) ON UPDATE RESTRICT ON DELETE CASCADE;

