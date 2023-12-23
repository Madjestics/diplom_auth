


CREATE TABLE IF NOT EXISTS public.profile
(
    id bigint NOT NULL,
    login text NOT NULL,
    password text NOT NULL,
    is_enabled boolean NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public.role
(
    id bigint NOT NULL,
    name text NOT NULL,
    description text,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public.permission
(
    id bigint NOT NULL,
    description text NOT NULL,
    granted_authority text NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public.role_permission
(
    role_id bigint NOT NULL,
    permission_id bigint NOT NULL,
    PRIMARY KEY (role_id, permission_id)
    );

CREATE TABLE IF NOT EXISTS public.profile_role
(
    profile_id bigint NOT NULL,
    role_id bigint NOT NULL,
    PRIMARY KEY (profile_id, role_id)
    );

create sequence if not exists profile_id_seq;

