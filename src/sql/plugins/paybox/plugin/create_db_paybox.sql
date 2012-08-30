DROP TABLE IF EXISTS paybox_log;

CREATE TABLE paybox_log (
  id bigint NOT NULL,
  date timestamp without time zone,
  order_reference character varying(255)
);

ALTER TABLE paybox_log ADD CONSTRAINT paybox_log_pkey PRIMARY KEY(id );