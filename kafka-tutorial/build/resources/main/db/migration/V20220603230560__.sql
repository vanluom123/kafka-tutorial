CREATE TABLE student
(
    id         BIGINT NOT NULL,
    first_name VARCHAR(255) NULL,
    last_name  VARCHAR(255) NULL,
    CONSTRAINT pk_student PRIMARY KEY (id)
);