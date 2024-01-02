
drop table if exists comment
    
drop table if exists comment_like
    
drop table if exists hashtag
    
drop table if exists member
    
drop table if exists post
    
drop table if exists post_like
    
create table comment (
                         row_status tinyint check (row_status between 0 and 2),
                         comment_idx bigint not null auto_increment,
                         created_at datetime(6),
                         parrnet_comment_idx bigint,
                         post_idx bigint,
                         tag_member_idx bigint,
                         updated_at datetime(6),
                         write_member_idx bigint,
                         content varchar(255),
                         primary key (comment_idx)
) engine=InnoDB

create table comment_like (
                              row_status tinyint check (row_status between 0 and 2),
                              comment_idx bigint,
                              comment_like_idx bigint not null auto_increment,
                              created_at datetime(6),
                              member_idx bigint,
                              updated_at datetime(6),
                              primary key (comment_like_idx)
) engine=InnoDB

create table hashtag (
                         row_status tinyint check (row_status between 0 and 2),
                         created_at datetime(6),
                         hash_tag_idx bigint not null auto_increment,
                         post_idx bigint,
                         updated_at datetime(6),
                         tag_title varchar(255),
                         primary key (hash_tag_idx)
) engine=InnoDB

create table member (
                        row_status tinyint check (row_status between 0 and 2),
                        created_at datetime(6),
                        member_idx bigint not null auto_increment,
                        updated_at datetime(6),
                        name varchar(255),
                        nick_name varchar(255),
                        password varchar(255),
                        phone_number varchar(255),
                        primary key (member_idx)
) engine=InnoDB

create table post (
                      row_status tinyint check (row_status between 0 and 2),
                      created_at datetime(6),
                      member_idx bigint,
                      post_idx bigint not null auto_increment,
                      updated_at datetime(6),
                      content varchar(255),
                      title varchar(255),
                      primary key (post_idx)
) engine=InnoDB

create table post_like (
                           row_status tinyint check (row_status between 0 and 2),
                           created_at datetime(6),
                           member_idx bigint,
                           post_idx bigint,
                           post_like_idx bigint not null auto_increment,
                           updated_at datetime(6),
                           primary key (post_like_idx)
) engine=InnoDB

alter table comment
    add constraint UK_pld7gsh1s12w5kdfj5ku1fdsg unique (post_idx)
    
alter table comment_like
    add constraint UK_rkqekiqjrvrdr2h1egfw1x4k5 unique (comment_idx)
    
alter table comment_like
    add constraint UK_lm9fa6mg3tojtgv188ecpl0f unique (member_idx)
    
alter table post
    add constraint UK_q5ay60r87dtq789859vfuuvme unique (member_idx)
    
alter table post_like
    add constraint UK_bb762snkb30jxl6vots4m4p6 unique (member_idx)
    
alter table post_like
    add constraint UK_acoex7vdwkbvteimy813jpywf unique (post_idx)
    
alter table comment
    add constraint FK94t66w7y34jd4s9nbjq03ep4k
        foreign key (parrnet_comment_idx)
            references comment (comment_idx)
    
alter table comment
    add constraint FK4x0ed95d7btd26400uea2d7gt
        foreign key (post_idx)
            references post (post_idx)
    
alter table comment
    add constraint FKj1902jstv26v1pp4qekyqolwy
        foreign key (tag_member_idx)
            references member (member_idx)
    
alter table comment
    add constraint FKos5otosf2d27oggxkmk828fdt
        foreign key (write_member_idx)
            references member (member_idx)
    
alter table comment_like
    add constraint FK9fllndv7xagf0ju1tohuw3vb5
        foreign key (comment_idx)
            references comment (comment_idx)
    
alter table comment_like
    add constraint FKdn6b7faxklb3g9t52s8rauw6
        foreign key (member_idx)
            references member (member_idx)
    
alter table hashtag
    add constraint FKbxwlnghtww5k8spkdomr6y1ad
        foreign key (post_idx)
            references post (post_idx)
    
alter table post
    add constraint FK7tht0nbf8t72dgvadge8yq8mt
        foreign key (member_idx)
            references member (member_idx)
    
alter table post_like
    add constraint FKslqgn9k293frjqwfsbkuhdo63
        foreign key (member_idx)
            references member (member_idx)
    
alter table post_like
    add constraint FKm2sf6ujtx24p9ecboon3bfimr
        foreign key (post_idx)
            references post (post_idx)