--guestbook 테이블 삭제
drop table guestbook;

--시퀀스 삭제
drop sequence seq_guestbook_no;

--guestbook 테이블 생성
create table guestbook (
    no          number,
    name        varchar2(80),
    password    varchar2(20),
    content     varchar2(2000),
    reg_date    date,
    primary key (no)
);

--시퀀스 생성
create sequence seq_guestbook_no
increment by 1
start with 1
nocache;

--guestbook 전체 출력
select no
        ,name
        ,password
        ,content
        ,to_char(reg_date, 'YYYY-MM-DD HH:MI:SS') "reg_date"
from guestbook;

insert into guestbook
values (seq_guestbook_no.nextval, '박깜이', '1234', '안녕하시오', to_date('2022-05-27 04:43:20','YYYY-MM-DD HH:MI:SS'));
commit;