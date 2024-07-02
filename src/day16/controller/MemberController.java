package day16.controller; // dao와 view의 정보를 전달해주는 MemberController 클래스가 위치한 패키지

import day16.model.dao.MemberDao; // model패키지에 포함된 dao패키지의 MemberDao 클래스 호출
import day16.model.dto.MemberDto; // model패키지에 포함된 dto패키지의 MemberDto 클래스 호출

public class MemberController { // class s
    // 0.
    public static MemberController mcontrol = new MemberController(); // MemberController 클래스를 이용해 public static 상태로 mcontrol 멤버변수 객체를 만들고 클래스의 인스턴스 주소값 대입

    // 1. 회원가입 화면 함수 , 매개변수 : MemberDto(mid , mpwd , mname , mphone)  / 리턴값 : boolean
    public boolean signup(MemberDto memberDto ){ // m start, 리턴값을 boolean 타입으로 받고, MemberDto클래스의 회원가입용 생성자를 이용해 만든 memberDto 객체를 매개변수로 받는 public 상태의 signup 함수
        boolean result  = MemberDao.mdao.signup( memberDto ); // boolean 타입 result 변수에 memberDto를 매개변수로 받는 MemberDao클래스의 signup함수 리턴값 대입
        return result; // result에 대입된 값을 반환
    }// m end

    // 2-1. 로그인 성공한 회원의 번호 저장하는 멤버변수
    // - 글쓰기,댓글쓰기 할때 현재 로그인된 회원을 식별하기 위해서
    // - 초기값:0 비로그인상태 , 1~ : 로그인된회원번호(로그인된상태)
    public int loginMno = 0; // public 상태로 초기값이 0인 int타입 loginMno 변수 생성
    // 2-2 : 로그아웃 함수
    public void logout(){ loginMno = 0;  } // 반환값이 없고 loginMno를 0으로 만드는 logout 메소드 생성
    // 2-3 : 현재 로그인 상태 반환 함수
    public boolean loginState(){
        return loginMno==0?false:true;
    }
    // 2. 로그인 함수
    public boolean login( MemberDto memberDto ){// boolean 반환타입 memberDto를 매개변수로 받는 login 메소드생성
        loginMno = MemberDao.mdao.login( memberDto ); // loginMno에 MemberDao클래스 memberDto를 매개변수로 받는 login 반환값 대입
        return loginMno == 0 ? false : true; // loginMno가 0이면 false 아니면 true 리턴
    } // m end



    // 3. 아이디찾기 화면 함수
    public String findId( MemberDto memberDto ){ //  String 반환타입 memberDto를 매개변수로 받는 findId 메소드
        return MemberDao.mdao.findId( memberDto ); // MemberDao클래스의 memberDto를 매개변수로 받는 findId 메소드 리턴값을 리턴
    } // m end,
    // 4. 비밀번호찾기 화면 함수
    public String findPwd( MemberDto memberDto ){ // String 반환타입 memberDto를 매개변수로 받는 findPwd 메소드
        return MemberDao.mdao.findPwd( memberDto ); // MemberDao클래스의 memberDto를 매개변수로 받는 findPwd 메소드 리턴값을 리턴
    } // m end,

    // 5. 회원탈퇴 함수
    public boolean mDelete( String confirmPwd ){ //boolean 반환타입  confirmPwd를 매개변수로 받는 mDelete 메소드
        return MemberDao.mdao.mDelete( confirmPwd , loginMno ); // view로 부터 받은 확인비밀번호와 로그인된회원번호를 Dao 전달
    }
    // 6. 회원수정 함수
    public boolean mUpdate( MemberDto memberDto ){ //boolean 반환타입 memberDto를 매개변수로 받는 mUpdate 메소드
        // + memberDto에 로그인된 회원번호
        memberDto.setMno( loginMno ); // 현재 로그인된 회원번호를 mno에 대입
        return MemberDao.mdao.mUpdate( memberDto ); // MemberDao클래스의 memberDto를 매개변수로 받는 mUpdate메소드 리턴값을 리턴
    }
}
/*

[ 기능구현 ]
1. 아이디찾기	: 이름과 연락처를 입력받아 일치한 정보가 있으면'찾은아이디'출력 없으면 '없습니다'출력
			매개변수 : MemberDto( 이름,연락처)
			리턴값 : String( 찾은아이디 /  null ) - 찾았으면 문자열이 들어있고 못찾으면 null 대입

2. 비밀번호찾기	: 아이디과 연락처를 입력받아 일치한 정보가 있으면'찾은비밀번호'출력 없으면 '없습니다'출력
			매개변수 : MemberDto( 아이디,연락처)
			리턴값 : String( 찾은비밀번호, null ) - 찾았으면 문자열이 들어있고 못찾으면 null 대입

3. 로그인 		: 아이디와 비밀번호를 입력받아 일치한 정보가 있으면'로그인성공'출력 없으면 '로그인실패'출력
			매개변수 : MemberDto( 아이디,비밀번호 )
			리턴값 : boolean( 성공 / 실패 )

[ 각 기능들 개발순서 ]

	1. Controller  매개변수와 리턴값 구상 -> 2. Dao 매개변수 와 리턴값 구상 ->
	3. VIEW  로직 -> 4. CONTROLLER  로직 -> 5. DAO  로직 -> 6. 테스트

*/