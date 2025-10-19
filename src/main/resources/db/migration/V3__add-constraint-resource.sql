alter table resource
    add column consumer_id bigint;

alter table resource
    add constraint fk_resource_consumer foreign key (consumer_id) references consumer(id);