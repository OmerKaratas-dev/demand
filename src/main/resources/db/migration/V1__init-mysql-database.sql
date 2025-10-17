create table if not exists resource(
                          id  bigint not null auto_increment,
                          code varchar(255),
                          name varchar(100),
                          type varchar(255),
                          description varchar(255),
                          available boolean,
                          primary key (id)
) engine=InnoDB;