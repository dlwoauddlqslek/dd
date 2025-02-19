drop database if exists day09;
create database day09;
use day09;

# 1. 회원테이블
drop table if exists member;
create table member(            # 아이돌 그룹
   mid char(8) not null ,         # 식별키       최대 8자리
    mname varchar(10) not null ,   # 그룹명      최대 10자리
    mnumber int not null ,         # 인원수      정수 +-21억정도
    maddr char(2) not null ,       # 지역      최대 2자리
    mphone1 char(3) ,            # 지역번호   최대 2자리 
    mphone2 char(8) ,            # 전화번호    최대 8자리
    mheight smallint ,            # 평균키       정수 +-3만정도
   mdebut date ,               # 데뷔일       yyyy-mm-dd 
    primary key ( mid )            # 제약조건 
);
# 2. 구매테이블
drop table if exists buy;
create table buy(
   bnum int auto_increment ,          # 구매번호   정수    자동번호 부여 
    mid char(8),                  # 구매자      FK 
    bpname char(6) not null ,         # 제품명      최대 6자리 
    bgname char(4) ,                # 분류명       최대   4자리
    bprice int not null ,            # 가격       정수 
    bamout smallint not null ,         # 구매수량   정수 
    primary key(bnum) ,               # 제약조건 
    foreign key ( mid ) references member(mid) # 제약조건 
);

# 샘플데이터 
INSERT INTO member VALUES('TWC', '트와이스', 9, '서울', '02', '11111111', 167, '2015.10.19');
INSERT INTO member VALUES('BLK', '블랙핑크', 4, '경남', '055', '22222222', 163, '2016.08.08');
INSERT INTO member VALUES('WMN', '여자친구', 6, '경기', '031', '33333333', 166, '2015.01.15');
INSERT INTO member VALUES('OMY', '오마이걸', 7, '서울', NULL, NULL, 160, '2015.04.21');
INSERT INTO member VALUES('GRL', '소녀시대', 8, '서울', '02', '44444444', 168, '2007.08.02');
INSERT INTO member VALUES('ITZ', '잇지', 5, '경남', NULL, NULL, 167, '2019.02.12');
INSERT INTO member VALUES('RED', '레드벨벳', 4, '경북', '054', '55555555', 161, '2014.08.01');
INSERT INTO member VALUES('APN', '에이핑크', 6, '경기', '031', '77777777', 164, '2011.02.10');
INSERT INTO member VALUES('SPC', '우주소녀', 13, '서울', '02', '88888888', 162, '2016.02.25');
INSERT INTO member VALUES('MMU', '마마무', 4, '전남', '061', '99999999', 165, '2014.06.19');

INSERT INTO buy VALUES(NULL, 'BLK', '지갑', NULL, 30, 2);
INSERT INTO buy VALUES(NULL, 'BLK', '맥북프로', '디지털', 1000, 1);
INSERT INTO buy VALUES(NULL, 'APN', '아이폰', '디지털', 200, 1);
INSERT INTO buy VALUES(NULL, 'MMU', '아이폰', '디지털', 200, 5);
INSERT INTO buy VALUES(NULL, 'BLK', '청바지', '패션', 50, 3);
INSERT INTO buy VALUES(NULL, 'MMU', '에어팟', '디지털', 80, 10);
INSERT INTO buy VALUES(NULL, 'GRL', '혼공SQL', '서적', 15, 5);
INSERT INTO buy VALUES(NULL, 'APN', '혼공SQL', '서적', 15, 2);
INSERT INTO buy VALUES(NULL, 'APN', '청바지', '패션', 50, 1);
INSERT INTO buy VALUES(NULL, 'MMU', '지갑', NULL, 30, 1);
INSERT INTO buy VALUES(NULL, 'APN', '혼공SQL', '서적', 15, 1);
INSERT INTO buy VALUES(NULL, 'MMU', '지갑', NULL, 30, 4);

# 1. 테이블 조회	[ * : 모든 필드 표시 ]
select * from member;
select * from buy;

# 2. 테이블 조회	[ 특정 필드 표시 ,(쉼표) ]
select mid from member;
select mid , mname from member;

#3. as 별칭(조회 결과의 필드명을 설정)
select mid as a1 from member;
select mid a1 from member m;

#4. 조건절[where 조건식]
select * from member where mname='블랙핑크'; # mname의 필드값이 '블랙핑크'인 레코드 검색
select * from member where mnumber=4; # mnumber의 필드값이 '4'인 레코드 검색
select * from member where mheight <=162; # mheight의 필드값이 162 이하인 레코드 검색
select * from member where mheight >=165 and mheight <=170; #mheight의 필드값이 165이상 이면서 170 이하인 레코드 검색
select * from member where mheight between 165 and 170;
select * from member where mheight >=165 or mnumber >6; # mheight의 필드값이 165이상 이거나 mnumber의 필드값이 6 초과인 레코드 검색
select * from member where maddr='경기' or maddr='전남' or maddr='경남';
select * from member where maddr in('경기','전남','경남'); # maddr의 필드값이 '경기'또는'전남'또는'경남'인 레코드 검색
select * from member where maddr not in('경기','전남','경남'); # maddr의 필드값이 '경기'또는'전남'또는'경남'을 제외한 레코드 검색
select * from member where mname !='에이핑크'; #mname의 필드값이 '에이핑크'가 아닌 레코드 검색
select * from member where not mname ='에이핑크'; #mname의 필드값이 '에이핑크'가 아닌 레코드 검색
select * from member where mname like '에이%'; #mname의 필드값이 '에이'로 시작하는 문자인 레코드 검색
select * from member where mname like '%핑크'; #mname의 필드값이 '핑크'로 끝나느 문자인 레코드 검색
select * from member where mname like '%이%'; #mname의 필드값이 '이'가 포함된 레코드 검색
select * from member where mname like '에이_'; #mname의 필드값이 '에이'로 시작하는 세글자 문자인 레코드 검색
select * from member where mname like '__핑크'; #mname의 필드값이 '핑크'로 끝나는 네글자 문자 레코드 검색
select * from member where mname like '_이_'; #mname의 필드값이 '이'가 2번째 위치한 세글자 문자 레코드 검색
select * from member where mnumber >=1+5;
select mnumber +3, mnumber -3, mnumber *3, mnumber /3, mnumber div 3 몫, mnumber mod 3 나머지 from member;
select mid 기존필드, 3+3 새로운필드 from member; #검색시 연산 결과를 새로운 필드로 조회 가능한데. 
#관례적으로 연산식/통계 관련된 필드는 create table 할 때 포함하지 않는다.  

select * from member where mphone1=''; #공백 검색
select * from member where mphone1=''; #띄어쓰기 하나 들어간 데이터 검색
select * from member where mphone1=null; #null 검색이 안된다.*
select * from member where mphone1 is null; #is null 검색이 된다.
select * from member where mphone1 is not null; #null이 아닌 레코드 검색
/*
	SQL 연산자
		1. 산술연산자: +, -, *, /, div 몫, mod 나머지
		2. 비교연산자: =같다 !=같지않다, <미만 <=이하, >초과 >=이상
        3. 논리/관계 연산자: and 이면서 or 이거나 not 부정
        4. 기타연산자:
				필드명 between 시작값 and 끝값: 시작값 부터 끝값 사이의 값 조회
                필드명 in (값1, 값2, 값3): 여러 값 중에 하나라도 포함하면 조회
					필드명 not in(값1, 값2, 값3): 포함하지 않는
				패턴 비교 검색: 필드명 like 패턴
					1. %: 모든 문자수 대응					2. _: 개수만큼 문자수 대응
                    - 필드명 like '값%': 값으로 시작하는		- 필드명 like '값_': 값으로 시작하는 두글자
                    - 필드명 like '%값': 값으로 끝나는		- 필드명 like '__값': 값으로 끝나는 세글자
                    - 필드명 like '%값%': 값이 포함된		- 필드명 like '_값_': 두번째 글자가 값인 세글자
				null 검색
					- 필드명 is null
                    - 필드명 is not null
*/

#5. 조회 결과의 레코드를 필드 기준으로 정렬
	#order by 필드명 [생략]asc			: 필드기준으로 레코드를 오름차순 정렬, 작은수->큰수, 과거날짜->최근날짜
    #order by 필드명 desc				: 필드기준으로 레코드를 내림차순 정렬, 큰수->작은수, 최근날짜->과거날짜
select * from member order by mdebut;
select * from member order by mdebut asc;
select * from member order by mdebut desc;
	#정렬필드 기준이 2개 이상일 때 주의할점
		# 첫번째 필드 정렬후 첫번째 필드내 동일한 값이 있을경우 동일한 값 중에서 두번째 정렬 실행
select * from member order by maddr desc, mdebut asc;

#6. 검색 레코드 수 제한
	#limit 레코드수
    #limit 시작레코드번호(0~), 출력개수
select * from member limit 2; #검색 결과의 레코드를 위에서부터 2개만 출력
select * from member limit 0,3; #0(첫번째)레코드 부터 3개 출력 제한
select * from member limit 1,2; #1(두번째)레코드부터 2개 출력 제한
select * from member order by mheight desc limit 3; # 'mheight'필드값 상위 3개 레코드 검색

#7. 검색된 필드의 중복 제거
select maddr from member;			# 모든 주소 검색
select distinct maddr from member;	# 모든 주소에서 중복 제거 검색

#select 에서 where절, order절, limit절 동시에 작성시에는순서 존재.
#select 필드명 from 테이블명 where 조건절 order by 필드명[정렬기준] limit 레코드제한수
