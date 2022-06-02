--users 테이블 삭제
drop table users;
--users 시퀀스 삭제
drop sequence seq_users_no;


--users 테이블 생성
create table users (
  no    number,
  id    varchar2(20)    unique  not null,
  password  varchar2(20)    not null,
  name  varchar2(20),
  gender    varchar2(20),
  PRIMARY KEY (no)
);

--users 시퀀스 생성
create sequence seq_users_no
increment by 1
start with 1
nocache;


--insert
insert into users
values (seq_users_no.nextval, 'hee', '0000', '박희지', 'female');


--users 출력
select *
from users;

select no
        ,id
        ,password
        ,name
        ,gender
from users
where id = 'hee'
and password = '0000';