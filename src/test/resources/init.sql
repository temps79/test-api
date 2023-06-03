drop table if exists article CASCADE;
drop table if exists article_content CASCADE;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;
create table article
(
    id           bigint not null,
    author       varchar(255),
    publish_date timestamp,
    title        varchar(100),
    primary key (id)
);
create table article_content
(
    article_id  bigint       not null,
    content     varchar(255),
    content_key varchar(255) not null,
    primary key (article_id, content_key)
);
alter table article_content
    add constraint FKr2fwi9p8yrsnwdpuijkai2mx3 foreign key (article_id) references article;

