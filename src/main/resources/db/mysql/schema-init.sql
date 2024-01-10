-- comment 테이블 생성
drop table comment_like;

drop table comment;

drop table hashtag;

drop table post_like;

drop table post;

drop table member;

DROP TABLE IF EXISTS comment;;
CREATE TABLE comment (
                         comment_idx BIGINT NOT NULL AUTO_INCREMENT,
                         created_at DATETIME(6),
                         member_idx BIGINT,
                         parennt_comment_idx BIGINT,
                         post_idx BIGINT,
                         tag_member_idx BIGINT,
                         updated_at DATETIME(6),
                         content VARCHAR(255),
                         row_status ENUM ('U','N','D'),
                         PRIMARY KEY (comment_idx)
) ENGINE=InnoDB;;

-- comment_like 테이블 생성
DROP TABLE IF EXISTS comment_like;;
CREATE TABLE comment_like (
                              comment_idx BIGINT NOT NULL,
                              created_at DATETIME(6),
                              member_idx BIGINT NOT NULL,
                              updated_at DATETIME(6),
                              row_status ENUM ('U','N','D'),
                              PRIMARY KEY (comment_idx, member_idx)
) ENGINE=InnoDB;;

-- hashtag 테이블 생성
DROP TABLE IF EXISTS hashtag;;
CREATE TABLE hashtag (
                         created_at DATETIME(6),
                         hash_tag_idx BIGINT NOT NULL AUTO_INCREMENT,
                         post_idx BIGINT,
                         updated_at DATETIME(6),
                         tag_title VARCHAR(255),
                         row_status ENUM ('U','N','D'),
                         PRIMARY KEY (hash_tag_idx)
) ENGINE=InnoDB;;

-- member 테이블 생성
DROP TABLE IF EXISTS member;;
CREATE TABLE member (
                        created_at DATETIME(6),
                        member_idx BIGINT NOT NULL AUTO_INCREMENT,
                        updated_at DATETIME(6),
                        name VARCHAR(255),
                        nick_name VARCHAR(255),
                        password VARCHAR(255),
                        phone_number VARCHAR(255),
                        row_status ENUM ('U','N','D'),
                        PRIMARY KEY (member_idx)
) ENGINE=InnoDB;;

-- post 테이블 생성
DROP TABLE IF EXISTS post;;
CREATE TABLE post (
                      created_at DATETIME(6),
                      member_idx BIGINT,
                      post_idx BIGINT NOT NULL AUTO_INCREMENT,
                      updated_at DATETIME(6),
                      content VARCHAR(255),
                      title VARCHAR(255),
                      row_status ENUM ('U','N','D'),
                      PRIMARY KEY (post_idx)
) ENGINE=InnoDB;;

-- post_like 테이블 생성
DROP TABLE IF EXISTS post_like;;
CREATE TABLE post_like (
                           created_at DATETIME(6),
                           member_idx BIGINT NOT NULL,
                           post_idx BIGINT NOT NULL,
                           updated_at DATETIME(6),
                           row_status ENUM ('U','N','D'),
                           PRIMARY KEY (member_idx, post_idx)
) ENGINE=InnoDB;;

alter table comment
    add constraint FKs1ycpri6yxxo259s77d6rgj5n
        foreign key (parennt_comment_idx)
            references comment (comment_idx);;
    
alter table comment
    add constraint FK4x0ed95d7btd26400uea2d7gt
        foreign key (post_idx)
            references post (post_idx);;
    
alter table comment
    add constraint FKj1902jstv26v1pp4qekyqolwy
        foreign key (tag_member_idx)
            references member (member_idx);;
    
alter table comment
    add constraint FKb4ib76no8d165u5of7yjy3kau
        foreign key (member_idx)
            references member (member_idx);;
    
alter table comment_like
    add constraint FK9fllndv7xagf0ju1tohuw3vb5
        foreign key (comment_idx)
            references comment (comment_idx);;
    
alter table comment_like
    add constraint FKdn6b7faxklb3g9t52s8rauw6
        foreign key (member_idx)
            references member (member_idx);;
    
alter table hashtag
    add constraint FKbxwlnghtww5k8spkdomr6y1ad
        foreign key (post_idx)
            references post (post_idx);;
    
alter table post
    add constraint FK7tht0nbf8t72dgvadge8yq8mt
        foreign key (member_idx)
            references member (member_idx);;
    
alter table post_like
    add constraint FKslqgn9k293frjqwfsbkuhdo63
        foreign key (member_idx)
            references member (member_idx);;
    
alter table post_like
    add constraint FKm2sf6ujtx24p9ecboon3bfimr
        foreign key (post_idx)
            references post (post_idx);;