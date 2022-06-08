--board 테이블 삭제
drop table board;
--board 시퀀스 삭제
drop sequence seq_board_no;


--board 테이블 생성
create table board (
    no    number,
    title    varchar2(500)    not null,
    content  varchar2(4000),
    hit  number,
    reg_date  date    not null,
    user_no   number  not null,
    primary key(no),
    constraint user_fk foreign key (user_no)
    references users(no)
);

--board 시퀀스 생성
create sequence seq_board_no
increment by 1
start with 1
nocache;


--insert
insert into board
values (seq_board_no.nextval, '제목', '글글글', 1, sysdate, 1);


--board 출력
select  b.no  
        ,b.title
        ,b.content
        ,b.hit
        ,to_char(b.reg_date,'YY-MM-DD HH24:MI') "reg_date"
        ,b.user_no
        ,u.name
from board b, users u
where b.user_no = u.no
order by no desc;

commit;