CREATE TABLE IF NOT EXISTS consumer (
                                        id BIGINT NOT NULL AUTO_INCREMENT,
                                        name VARCHAR(255) NOT NULL,
                                        created_date DATETIME(6),
                                        username VARCHAR(255) NOT NULL,
                                        mail VARCHAR(255) NOT NULL,
                                        phone_number VARCHAR(20) NOT NULL,
                                        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
