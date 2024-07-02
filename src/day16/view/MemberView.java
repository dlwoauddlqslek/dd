package day16.view; //view 클래스들이 들어있는 패키지

import day16.controller.MemberController; // MemberController 클래스 호출
import day16.model.dto.MemberDto;         // MemberDto 클래스 호출

import java.util.InputMismatchException; // 예외처리 클래스 호출
import java.util.Scanner;                // 스캐너 클래스 호출

public class MemberView { // class s
    // - 해당 클래스의 함수들을 다른 클래스에서 호출 할수 있도록 static변수에 해당 객체 만들기
    // - 멤버변수는 객체 마다 할당, 메소드는 객체마다 실행을 하지만 코드는 공유-
    // - 전역객체 -> 모든곳에서 호출 가능한 객체1개
    // - 싱글톤: 생성자에 private, 프로그램내 전역으로 하나의 객체를 생성
    public static MemberView mView = new MemberView(); // public static 상태로 MemberView 클래스를 이용해 mView 멤버변수 객체 생성
    private MemberView(){} // 생성자 private -> 다른 클래스에서 new 를 못한다(객체1개)
    // 멤버변수 : 입력객체
    Scanner scan = new Scanner(System.in); // 입력값 받기 위해 scan 변수 생성
    // 0. 초기화면 함수
    public void index(){// m s
        while (true){ //무한반복문
            try { // 예외처리
                System.out.print("1.회원가입 2.로그인 3.아이디찾기 4.비밀번호찾기 : "); // 출력
                int ch = scan.nextInt();    // int타입 입력값 ch에 대입
                if( ch == 1 ){ signup(); }  // ch가 1이면 sginup메소드 호출
                else if( ch == 2 ){ login(); }  // ch가 2이면 login메소드 호출
                else if( ch == 3 ){ findId(); } // ch가 3이면 finId 호출
                else if( ch == 4 ){ findPwd(); }// ch가 4이면 findPwd 호출
                else {  System.out.println(">> 없는 기능 번호 입니다.");  } // 그 밖의 값, 출력
            }catch ( InputMismatchException e ){// 입력값이 int 값이 아닐 때 예외처리
                System.out.println(">> 잘목된 입력이 있습니다. 초기메뉴로 "); // 출력
                scan = new Scanner(System.in); //scan변수값 초기화
            } // c end
        } // w end
    } // m end
    // 1. 회원가입 화면 함수
    public void signup(){// m s
        // 1. 입력
        System.out.print("아이디:");   String mid = scan.next(); //String 타입값 입력 후 대입
        System.out.print("비밀번호:");  String mpwd = scan.next();
        System.out.print("이름:");    String mname = scan.next();
        System.out.print("연락처:");   String mphone = scan.next();
        // 3. 객체화
        MemberDto memberDto = new MemberDto( mid , mpwd , mname , mphone ); //회원가입용 생성자를 통해 객체 생성
        // 4. Controller에게 전달
        boolean result =  MemberController.mcontrol.signup( memberDto ); //boolean 타입 result 변수에 memberDto를 매개변수로 받는 MemberController클래스 signUp 함수 리턴값을 대입
        if( result ){  System.out.println(">>회원가입 성공 "); } // result 값을 조건으로 하는 조건문 생성, 참일 때
        else{  System.out.println(">>회원가입 실패");  } // 거짓일 때
    }// m end

    // 2. 로그인 화면 함수
    public void login(){// m s
        System.out.print("아이디 : ");//출력
        String mid = scan.next(); //변수에 String타입 입력값 대입
        System.out.print("비밀번호 : ");
        String mpwd = scan.next();
        MemberDto memberDto = new MemberDto(); //기본생성자를 통해 객체 생성
        memberDto.setMid( mid ); memberDto.setMpwd( mpwd ); //생성된 객체의 멤버변수에 값 대입
        boolean result = MemberController.mcontrol.login( memberDto ); //boolean 타입 result에 memberDto를 매개변수로 받는 MemberController클래스의 login 함수 리턴값 대입
        if( result ){ // result 값이 조건인 if문 생성
            System.out.println("로그인성공 "); // 참일 때 출력
            BoardView.bView.index2();// 로그인 성공시 로그인된 메뉴로 이동
        }
        else{ System.out.println("로그인실패");} // 거짓일 때 출력
    } // m end

    // 3. 아이디찾기 화면 함수
    public void findId(){ // m s
        System.out.print("이름 : "); // 출력
        String mname = scan.next(); // String 타입을 변수에 대입
        System.out.print("연락처 : ");
        String mphone = scan.next();
        MemberDto memberDto = new MemberDto(); //기본생성자를 이용해 객체 생성
        memberDto.setMname( mname ); memberDto.setMphone( mphone ); // 생성된 객체에 멤버변수값 대입
        String result =  MemberController.mcontrol.findId( memberDto ); // String타입 result에 memberDto를 매개변수로 가지는 findId 함수 리턴값을 대입
        if( result != null ){  System.out.println("회원아이디 : "+result);} //result값이 null이 아니라면 result값 출력
        else{  System.out.println("동일한 회원정보가 없습니다."); }//null이라면 문장 출력
    } // m end
    // 4. 비밀번호찾기 화면 함수
    public void findPwd(){//m s
        System.out.print("아이디 : ");// 출력
        String mid = scan.next();   // String 타입을 변수에 대입
        System.out.print("연락처 : ");
        String mphone = scan.next();
        MemberDto memberDto = new MemberDto(); //기본생성자를 이용해 객체 생성
        memberDto.setMid( mid ); memberDto.setMphone( mphone ); //생성된 객체에 멤버변수값 대입
        String result = MemberController.mcontrol.findPwd( memberDto );//String타입 result에 memberDto를 매개변수로 가지는 findPwd 함수 리턴값을 대입
        if( result != null ){ System.out.println("회원비밀번호 : " + result );}// result값이 null이 아니라면 result값 출력
        else{  System.out.println("동일한 회원정보가 없습니다."); }//null이라면 문장 출력
    } // m end
} // c end







