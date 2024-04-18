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
    price int  not null,
    constraint primary key(pjno)
);

create table companny(
	cno int auto_increment,
    cname varchar(100),
    constraint com_cno_pk primary key(cno)    
);

create table project2( #프로젝트 영업?
	pjno int auto_increment,
    start_date date not null,
    end_date date not null,
    rank1_count int,
    rank2_count int,
    rank3_count int,
    title varchar(30) not null,
    request varchar(255),
    note varchar(255) default "",
    cno int not null,
    state int default 0,
    price text not null,
    constraint primary key(pjno),
    constraint pj2_cno foreign key(cno) references companny(cno)
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
    note Varchar(255),
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
insert into employee(id,ename,pw,email,phone,address,pno,sex) values("admina","admina","admina","aaa@aa.aa","phonea","adressa",1,0);
insert into employee(id,ename,pw,email,phone,address,pno,sex) values("adminb","adminb","adminb","bbb@bb.bb","phoneb","adressb",1,1);
insert into employee(id,ename,pw,email,phone,address,pno,sex) values("adminc","adminc","adminc","ccc@cc.cc","phonec","adressc",1,0);

insert into employee(id,ename,pw,email,phone,address,pno,sex) values("salesa","salesa","salesa","ddd@dd.dd","phoned","adressd",2,0);
insert into employee(id,ename,pw,email,phone,address,pno,sex) values("salesb","salesb","salesb","eee@ee.ee","phonee","adresse",2,1);
insert into employee(id,ename,pw,email,phone,address,pno,sex) values("salesc","salesc","salesc","fff@ff.ff","phonef","adressf",2,0);

insert into employee(id,ename,pw,email,phone,address,pno,sex) values("programmera","programmera","programmera","ggg@gg.gg","phoneg","adressg",3,0);
insert into employee(id,ename,pw,email,phone,address,pno,sex) values("programmerb","programmerb","programmerb","hhh@hh.hh","phoneh","adressh",3,1);
insert into employee(id,ename,pw,email,phone,address,pno,sex) values("programmerc","programmerc","programmerc","iii@ii.ii","phonei","adressi",3,0);

insert into employeecareer values(1,"ㅇㅇ","ㅇㅇ","ㅇㅇ","2021-01-01","2024-01-01");
insert into project(start_date,end_date,rank1_count,rank2_count,rank3_count,title,request,compannyname,price) 
values("2024-02-29","2024-03-09",3,5,1,"ezen site 차세대 프로젝트","일잘하는애들로","ezen",5000000);

insert into project(start_date,end_date,rank1_count,rank2_count,rank3_count,title,request,compannyname,price) 
values("2024-03-08","2024-07-10",5,0,0,"쇼핑몰 구축"," ","ezen쇼핑몰",30000000);

insert into project(start_date,end_date,rank1_count,rank2_count,rank3_count,title,request,compannyname,price) 
values("2030-03-08","2030-07-10",5,0,0,"쇼핑몰 구축"," ","ezen쇼핑몰",30000000);
insert into report(settoeno,setfromeno,title,content) values(1,2,"이거사줘","붕어싸만코");
insert into report(settoeno,setfromeno,title,content) values(1,3,"휴가","ㅇㅇ");

truncate projectlog;
insert into projectlog(eno, pjno) values(2,1);
insert into projectlog(eno, pjno) values(3,1);
insert into projectlog(eno, pjno) values(7,1);
insert into projectlog(eno, pjno) values(6,1);

insert into projectlog(eno, pjno) values(2,2);
insert into projectlog(eno, pjno) values(3,2);

insert into projectlike(eno, pjno) values(2,1);
insert into projectlike(eno, pjno) values(3,2);


select * from employee;
select * from project;
select * from projectlog;
update projectlog set score = 100;
select * from projectlike;
select * from employeecareer;
# select d.eno, coalesce(TIMESTAMPDIFF(day,"2022-03-03",max(end_date)),-1) as endproject #이전 프로젝트 끝나는 날짜 찾기(두번째 매개변수에 이 프로젝트가 시작하는 날짜 집어넣기) 이거에서 서브쿼리로 보내려고 having절로 옮김

select d.eno ,coalesce(datediff("2025-03-03",max(end_date)),1)
from 
(select b.pjno , end_date, a.eno  from project b 
right outer join projectlog a
on a.pjno = b.pjno) as c 
right outer join
employee d 
on c.eno = d.eno
group by d.eno;

         #그룹핑 해서 "프로젝트 시작일자"-"최근프로젝트가 끝난 일자"가 양수 즉 프로젝트가 시작하기 전에 프로젝트가 끝난사람의 eno만 가져오겠다,
 #만약 프로젝트 로그에 없는 사람일 경우 로그에 안찍히므로 무조건 1을 부여
 
 

select a.eno,a.평가점수
from (select e.* , sum(coalesce(score,0)) as 평가점수
	from employee as e left outer join projectlog p on e.eno = p.eno
    group by e.eno
 )as a left outer join  employeecareer as b on a.eno = b.eno
where a.eno in(
		select d.eno
		from 
		(select b.pjno , end_date, a.eno  from project b 
		right outer join projectlog a
		 on a.pjno = b.pjno) as c 
		right outer join
		 employee d 
		 on c.eno = d.eno
		 group by d.eno
		 having ( coalesce(datediff("2024-03-07",max(end_date)),1) > 0)
)	
group by a.eno;
#올해기준으로 확인하기 : datediff(CONCAT(YEAR(NOW()), '-01-01') ,a.edate))
select  a.eno, a.평가점수, (sum(coalesce(datediff(b.end_date,b.start_date),0))+datediff(CONCAT(YEAR(NOW()), '-01-01') ,a.edate)) as alltime #사원번호와 프로젝트로그에서 사원이 한 총 누적점수와 사원이전경력테이블+현재 경력을 합친 필드를 가져와야함
from (select e.* , sum(coalesce(score,0)) as 평가점수    #사원번호와 총 누적점수를 구하기위해 스칼라쿼리 생성후 사원과 프로젝트로그를 레프트아우터 조인한 테이블을 만들어서 사원필드 모든것+평가점수를 뽑아옴
	from employee as e left outer join projectlog p on e.eno = p.eno
    group by e.eno
 )as a left outer join  employeecareer as b on a.eno = b.eno #그렇게 만들어둔 필드와 사원의 이전 경력테이블을 조인해 사원 경력을 뽑아내게함, left outer 한 이유: 이전경력이 없는 사원이 있을 경우가 있으므로
where a.eno in( #그렇게 만들어낸 테이블에서 조건: 프로젝트로그테이블에서 가장 최근에 끝난 프로젝트의 끝 날짜를 따와 현재 받아올 프로젝트의 시작일자보다 먼저 끝난 eno들만 가져오겠다
		select d.eno
		from 
		(select b.pjno , end_date, a.eno  from project b  
		right outer join projectlog a
		 on a.pjno = b.pjno) as c 
		right outer join
		 employee d 
		 on c.eno = d.eno
		 group by d.eno
		 having ( coalesce(datediff("2024-03-07",max(end_date)),1) > 0)
)
group by a.eno; #누계이므로 그룹핑

select  a.eno as eno, a.평가점수 as score, (sum(coalesce(datediff(b.end_date,b.start_date),0))+datediff(now(),a.edate)) as alltime 
from (select e.* , sum(coalesce(score,0)) as 평가점수  
	from employee as e left outer join projectlog p on e.eno = p.eno
    group by e.eno
 )as a left outer join  employeecareer as b on a.eno = b.eno 
where a.eno in(
		select d.eno
		from 
		(select b.pjno , end_date, a.eno  from project b  
		right outer join projectlog a
		 on a.pjno = b.pjno) as c 
		right outer join
		 employee d 
		 on c.eno = d.eno
		 group by d.eno
		 having ( coalesce(datediff("2024-03-07",max(end_date)),1) > 0)
)
group by a.eno;

select  a.eno as eno, a.평가점수 as score
from (select e.* , sum(coalesce(score,0)) as 평가점수  
	from employee as e left outer join projectlog p on e.eno = p.eno
    group by e.eno
 )as a left outer join  employeecareer as b on a.eno = b.eno ;


select edate from employee;

 select eno,datediff(edate,"2025-02-06") as 근속일자
 from employee;
 
 select eno from projectlog where pjno = 1;
 
 
 
 select a.eno, (sum(coalesce(datediff(b.end_date,b.start_date),0))+datediff(now(),a.edate)) as alltime
from employee as a left outer join  employeecareer as b on a.eno = b.eno
where a.eno in(
		select d.eno
		from 
		(select b.pjno , end_date, a.eno  from project b 
		right outer join projectlog a
		 on a.pjno = b.pjno) as c 
		right outer join
		 employee d 
		 on c.eno = d.eno
		 group by d.eno
		 having ( coalesce(datediff("2020-03-03",max(end_date)),1) > 0)
)
or a.eno in(
	select eno from projectlog where pjno = 2
)
group by a.eno; #스코어 안 뽑을경우+수정할떄


 select a.eno, (sum(coalesce(datediff(b.end_date,b.start_date),0))+datediff(now(),a.edate)) as alltime
from employee as a left outer join  employeecareer as b on a.eno = b.eno
where a.eno in(
		select d.eno
		from 
		(select b.pjno , end_date, a.eno  from project b 
		right outer join projectlog a
		 on a.pjno = b.pjno) as c 
		right outer join
		 employee d 
		 on c.eno = d.eno
		 group by d.eno
		 having ( coalesce(datediff("2020-03-03",max(end_date)),1) > 0)
)
group by a.eno; #스코어 안 뽑을경우

select  a.ename, a.img, a.eno as eno, a.평가점수 as score, (sum(coalesce(datediff(b.end_date,b.start_date),0))+datediff(now(),a.edate)) as alltime 
from (select e.* , sum(coalesce(score,0)) as 평가점수  
	from employee as e left outer join projectlog p on e.eno = p.eno
    group by e.eno
 )as a left outer join  employeecareer as b on a.eno = b.eno 
where a.eno in(
		select d.eno
		from 
		(select b.pjno , end_date, a.eno  from project b  
		right outer join projectlog a
		 on a.pjno = b.pjno) as c 
		right outer join
		 employee d 
		 on c.eno = d.eno
		 group by d.eno
		 having ( coalesce(datediff("2024-03-07",max(end_date)),1) > 0)
)
group by a.eno;#스코어 뽑을경우+수정할떄


select  a.eno as eno, a.평가점수 as score, (sum(coalesce(datediff(b.end_date,b.start_date),0))+datediff(now(),a.edate)) as alltime 
from (select e.* , sum(coalesce(score,0)) as 평가점수  
	from employee as e left outer join projectlog p on e.eno = p.eno
    group by e.eno
 )as a left outer join  employeecareer as b on a.eno = b.eno 
where a.eno in(
		select d.eno
		from 
		(select b.pjno , end_date, a.eno  from project b  
		right outer join projectlog a
		 on a.pjno = b.pjno) as c 
		right outer join
		 employee d 
		 on c.eno = d.eno
		 group by d.eno
		 having ( coalesce(datediff("2024-03-07",max(end_date)),1) > 0)
)
or a.eno in(
	select eno from projectlog where pjno = 1
)
group by a.eno;#스코어 뽑을경우+수정할떄



select a.평가점수 as score from (
    select e.*, sum(coalesce(score,0)) as 평가점수  
    from employee as e left outer join projectlog p on e.eno = p.eno
    group by e.eno
) as a left outer join employeecareer as b on a.eno = b.eno 
where a.eno = 1; #특정 인원 평가점수만 뽑아오기


select a.eno as eno, (sum(coalesce(datediff(b.end_date,b.start_date),0))+datediff(now(),a.edate)) as alltime  #프로젝트 로그에 남은 사원들 경력+eno 가져오기
from (select e.* , sum(coalesce(score,0)) as 평가점수  
	from employee as e left outer join projectlog p on e.eno = p.eno
    group by e.eno
 )as a left outer join  employeecareer as b on a.eno = b.eno
 where a.eno in(
	select eno from projectlog where pjno = 1
)
 group by a.eno;
 
 select * from project;
 
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

CREATE TRIGGER update_project_state
AFTER UPDATE  
ON project -- 트리거를 부착할 테이블
FOR EACH ROW -- 아래 나올 조건에 해당하는 모든 row에 적용한다는 뜻

BEGIN
  -- 트리거시 실행되는 코드
  IF NEW.state != OLD.state and new.state = 2 THEN -- update 트리거는 old와 new 값이 존재한다.
    UPDATE projectlog SET state = 1 WHERE pjno = new.pjno;
  END IF;
END //

DELIMITER ;

show events;
show triggers;

insert into companny(cname) values("dd");
select * from companny;
select * from project2;