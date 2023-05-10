--liquibase formatted sql
--changeset <makhynko>:<add-external-id-column-to-movie-characters-table.sql>

ALTER TABLE public.movie_characters ADD external_id bigint;

--rollback ALTER TABLE DROP COLUMN external_id