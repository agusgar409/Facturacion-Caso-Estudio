
CREATE TABLE IF NOT EXISTS direction (
  id BIGSERIAL NOT NULL,
   street VARCHAR(255) NOT NULL,
   height INTEGER NOT NULL,
   id_province INTEGER NOT NULL,
   id_location BIGINT,
   postal_code INTEGER NOT NULL,
   CONSTRAINT pk_direction PRIMARY KEY (id)
);

ALTER TABLE direction ADD CONSTRAINT FK_DIRECTION_ON_ID_LOCATION FOREIGN KEY (id_location) REFERENCES location (id);

ALTER TABLE direction ADD CONSTRAINT FK_DIRECTION_ON_ID_PROVINCE FOREIGN KEY (id_province) REFERENCES province (id);