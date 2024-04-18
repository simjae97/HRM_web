drop database if exists springteam1;
create database springteam1;
use springteam1;

create table part( #파트테이블
	pno int auto_increment,
    pname varchar(30),
    constraint primary key(pno)
);

create table employee( #사원테이블
	eno int auto_increment,
    eeducation varchar(10),
    id varchar(30) not null unique,
    pw varchar(15) not null,
    ename varchar(20) not null,
    email varchar(30) not null unique,
    phone varchar(15) not null unique,
    address varchar(15) not null,
    sex bool not null,
    img varchar(255) default 'default.jpg',
    edate datetime default now(),
    pno int not null,
    constraint e_pk primary key(eno),
    foreign key(pno) references part(pno)
);

create table project( #프로젝트 전체 테이블
	pjno int auto_increment,
    start_date date not null,
    end_date date not null,
    rank1_count int,
    rank2_count int,
    rank3_count int,
    title varchar(30) not null,
    request varchar(255),
    note varchar(255) default "",
    compannyname varchar(20) not null,
    state int default 0,
    price text not null,
    constraint primary key(pjno)
);

create table report( #보고서 보낸 로그
	rno int auto_increment,
    settoeno int, #얘는 보고서 받는사람
	setfromeno int, #얘는 보고서 보낸사람
    state bool default 0,
    title varchar(20),
    content text,
    img longtext ,
    note varchar(255),
    rdate datetime default now(),
    constraint primary key(rno),
    foreign key(settoeno) references employee(eno),
    foreign key(settoeno) references employee(eno)
);

-- create table reportlog( 만약 1:n으로 보내게 되면
-- 	rno int,
--     eno int, #얘는 보고서 보낸사람 로그
--     constraint foreign key(rno) references report(rno),
--     foreign key(eno) references employee(eno)
-- );

create table projectlog( #프로젝트-멤버 1:1로그
	eno int,
    pjno int,
    state int default 0,
    score int default 0,
    constraint foreign key(eno) references employee(eno),
    foreign key(pjno) references project(pjno)
);

create table projectlike( #좋아요 누른 프로젝트로그
	eno int,
    pjno int,
    foreign key(eno) references employee(eno),
    foreign key(pjno) references project(pjno)
);

create table license( #자격증 종류 테이블
	lno int auto_increment,
    lname varchar(50),
    lprice int,
    constraint primary key(lno)
);

create table employeelicense( #사원 자격증 로그
	eno int,
    lno int,
	ldate datetime default now(),
    constraint license_eno_fk foreign key(eno) references employee(eno),
    constraint license_lno_fk2 foreign key(lno) references license(lno)
);


create table employeecareer( #사원 경력
	eno int,
    companyname varchar(255),
    note varchar(255), #무슨 업무를 맡았는지
    eimg longtext,	#경력 증명서
    start_date date,
    end_date date,
    constraint employeecareer_eno_fk foreign key(eno) references employee(eno)
);

create table price(
	pno int auto_increment,	#1이면 초급, 2이면 중급 3이면고급
	startyear int,
    endyear int, #3,5,7
    pprice int,		#연봉
    constraint primary key(pno)
);


insert into price(startyear,endyear,pprice) values(0,5,3500);
insert into price(startyear,endyear,pprice) values(6,10,6000);
insert into price(startyear,endyear,pprice) values(11,15,10000);

insert into part values(1,"인사과");
insert into part values(2,"영업");
insert into part value(3,"프로그래머");
insert into employee(id,eeducation,ename,pw,email,phone,address,pno,sex) values("admina","고졸","admina","admina","aaa@aa.aa","phonea","adressa",1,0);
insert into employee(id,eeducation,ename,pw,email,phone,address,pno,sex) values("adminb","초대졸","adminb","adminb","bbb@bb.bb","phoneb","adressb",1,1);
insert into employee(id,eeducation,ename,pw,email,phone,address,pno,sex) values("adminc","대졸","adminc","adminc","ccc@cc.cc","phonec","adressc",1,0);

insert into employee(id,ename,pw,email,phone,address,pno,sex) values("salesa","salesa","salesa","ddd@dd.dd","phoned","adressd",2,0);
insert into employee(id,ename,pw,email,phone,address,pno,sex) values("salesb","salesb","salesb","eee@ee.ee","phonee","adresse",2,1);
insert into employee(id,ename,pw,email,phone,address,pno,sex) values("salesc","salesc","salesc","fff@ff.ff","phonef","adressf",2,0);

insert into employee(id,ename,pw,email,phone,address,pno,sex) values("programmera","programmera","programmera","ggg@gg.gg","phoneg","adressg",3,0);
insert into employee(id,ename,pw,email,phone,address,pno,sex) values("programmerb","programmerb","programmerb","hhh@hh.hh","phoneh","adressh",3,1);
insert into employee(id,ename,pw,email,phone,address,pno,sex) values("programmerc","programmerc","programmerc","iii@ii.ii","phonei","adressi",3,0);


insert into project(start_date,end_date,rank1_count,rank2_count,rank3_count,title,request,compannyname,price)
values("2024-02-29","2024-03-09",3,5,1,"ezen site 차세대 프로젝트","일잘하는애들로","ezen","10억");

insert into project(start_date,end_date,rank1_count,rank2_count,rank3_count,title,request,compannyname,price)
values("2024-03-08","2024-07-10",5,0,0,"쇼핑몰 구축"," ","ezen쇼핑몰","3억");

# 테스트용 더미데이터 하나 더 추가.
insert into project(start_date,end_date,rank1_count,rank2_count,rank3_count,title,request,compannyname,price)
values("2024-03-01","2024-03-10",2,2,1,"쇼핑몰 상세페이지 보수"," ","ezen쇼핑몰","300만원");

# 이전프로젝트 더미데이터 하나 더 추가.
insert into project(start_date,end_date,rank1_count,rank2_count,rank3_count,title,request,compannyname,price)
values("2024-03-01","2024-03-10",2,2,1,"쇼핑몰 결제페이지 보수"," ","ezen쇼핑몰","300만원");

# 이전 프로젝트 더미데이터 하나 더 추가.
insert into project(start_date,end_date,rank1_count,rank2_count,rank3_count,title,request,compannyname,price)
values("2024-04-08","2024-10-10",2,2,1,"포커사이트 구축"," ","ezen게임스","5억");

# 테스트용 더미데이터 하나 더 추가. 즐겨찾기용
insert into project(start_date,end_date,rank1_count,rank2_count,rank3_count,title,request,compannyname,price)
values("2024-03-01","2024-03-10",2,2,1,"카페 사이트 보수"," ","ezen카페","300만원");

# 테스트용 더미데이터 하나 더 추가. 즐겨찾기용
insert into project(start_date,end_date,rank1_count,rank2_count,rank3_count,title,request,compannyname,price)
values("2024-03-01","2024-03-10",2,2,1,"햄버거 페이지 유지보수"," ","ezen햄버거","300만원");

insert into report(settoeno,setfromeno,title,content) values(1,2,"이거사줘","붕어싸만코");
insert into report(settoeno,setfromeno,title,content) values(1,3,"휴가","ㅇㅇ");

insert into projectlog(eno, pjno) values(1,1);
insert into projectlog(eno, pjno) values(2,1);
insert into projectlog(eno, pjno) values(3,1);
insert into projectlog(eno, pjno) values(2,2);
insert into projectlog(eno, pjno) values(3,2);
insert into projectlog(eno, pjno , state) values(1,4,2);
insert into projectlog(eno, pjno , state) values(1,5,2);


insert into projectlike(eno, pjno) values(2,1);
insert into projectlike(eno, pjno) values(3,2);
insert into projectlike(eno, pjno) values(1,2); # 테스트 위한 더미 데이터 삽입.
insert into projectlike(eno, pjno) values(1,3); # 테스트 위한 더미 데이터 삽입.
insert into projectlike(eno, pjno) values(1,4); # 테스트 위한 더미 데이터 삽입.
insert into projectlike(eno, pjno) values(1,5); # 테스트 위한 더미 데이터 삽입.
insert into projectlike(eno, pjno) values(1,6); # 테스트 위한 더미 데이터 삽입.
insert into projectlike(eno, pjno) values(1,7); # 테스트 위한 더미 데이터 삽입.


# 프로젝트 4, 5 테스트 용 state 종료로 변경
UPDATE project SET state=2 WHERE pjno = 4;
UPDATE project SET state=2 WHERE pjno = 5;

select * from employee;
# 특정 회원 회원번호로 검색
select * from employee where eno = 1;
# 특정 필드 값이 조건에 충족하는 레코드만 검색
select id from employee where eno = 1;



select * from projectlike;
# 프로젝트 로그 검색
select * from projectlog;
select * from projectlog where eno = 1;
# 프로젝트 검색
select * from project;
# 프로젝트와 프로젝트 로그에서 내 사원 번호 검색 후 값 검색하기
select * from project p inner join projectlog l on p.pjno = l.pjno where eno = 1;
SELECT DISTINCT * FROM project p INNER JOIN projectlog l ON p.pjno = l.pjno WHERE eno = 1;

# 즐겨찾기한 프로젝트 조회
# SELECT * FROM test1 INNER JOIN test2 ON test1.b = test2.b INNER JOIN test3 ON test2.b = test3.b
select DISTINCT * from projectlike p inner join project p2 ON p.pjno = p2.pjno where eno = 1 and state = 0;

# 이전프로젝트 조회
select b.state , b.title , b.pjno, b.start_date , b.end_date from projectlog as a inner join project as b on a.pjno = b.pjno where eno = 1 and b.state = 2;
# DAO 가져갈시 eno = ? 로 변경

select * from projectlog;

DELIMITER //

CREATE EVENT IF NOT EXISTS update_project_state
ON SCHEDULE EVERY 1 DAY
DO
BEGIN
  UPDATE project
  SET state = CASE
                WHEN start_date > NOW() THEN 0 #시작일자가 오늘 날짜보다 크면 아직 시작안함
                WHEN end_date < NOW() THEN 2 #종료일자가 오늘 날짜보다 작으면 종료된 프로젝트
                ELSE 1 #그 사이인 경우이니 아직 진행중인 프로젝트
              END;
END//

DELIMITER ;

show events;
