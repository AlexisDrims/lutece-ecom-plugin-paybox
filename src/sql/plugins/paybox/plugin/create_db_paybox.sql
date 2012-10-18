DROP TABLE IF EXISTS paybox_log;

CREATE TABLE paybox_log (
  id bigint NOT NULL,
  `date` DATETIME,
  order_reference VARCHAR(255)
);

ALTER TABLE paybox_log ADD CONSTRAINT paybox_log_pkey PRIMARY KEY(id );