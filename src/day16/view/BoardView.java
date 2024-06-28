package day16.view; //view 클래스들이 있는 패키지

import day16.controller.MemberController; // MemberController 클래스 호출
import day16.model.dto.MemberDto;         // MemberDto 클래스 호출

import java.util.Scanner;               // Scanner 클래스 호출

public class BoardView { // class s
    // 0.
    public static BoardView bView = new BoardView(); // 현재 클래스를 이용해 bView 멤버변수 객체에 인스턴스 주소값 대입
    public Scanner scan = new Scanner(System.in); // 입력값 받기위해 scan 변수 생성
    // 0. 로그인 성공시 초기화면
    public void index2( ){ // m s
        while (true){//무한반복문
            System.out.print(">> 1.로그아웃 2.회원수정 3.회원탈퇴 4.게시판 : "); // 출력
            int ch = scan.nextInt(); // int타입 입력받아 ch에 대입
            if( ch == 1 ){ logout( ); return; } // ch가 1이면 logout 메소드 실행하고 반복문 종료
            else if( ch == 2 ){ mUpdate( ); }   // ch가 2이면 mUpdate 메소드 실행하고 반복문 종료
            else if( ch == 3 ){                 // ch가 3이고 mDelete 메소드가 참이면 logout 메소드 실행후 반복문 종료
                if( mDelete( ) ) {
                    logout(); return;
                }
            }
            else if( ch == 4 ){ bPrint( ); }// ch가 4이면 bPrint 메소드 실행
        } // w end
    } // main end
    // 1. 로그아웃 함수
    public void logout( ){ // m s
        MemberController.mcontrol.logout();  // 회원 컨트롤러에 로그아웃 메소드 호출
        System.out.println(">> 로그아웃 성공 [초기메뉴로]");
    } // m e
    // 2. 회원수정 함수
    public void mUpdate( ){ // m s
        System.out.print("수정할 회원명 : ");  String newMname = scan.next(); // 출력 후 String타입 입력값 대입
        System.out.print("수정할 연락처 : ");  String newMphone = scan.next();// 출력 후 String타입 입력값 대입
        MemberDto memberDto = new MemberDto();  //MemberDto클래스 이용해 memberDto 객체 생성
        memberDto.setMname( newMname ); memberDto.setMphone( newMphone ); // 생성된객체에 입력받은 2개의 값 대입
        boolean result = MemberController.mcontrol.mUpdate( memberDto ); // boolean타입 result에 MemberController 클래스 memberDto를 매개변수로 받는 mUpdate메소드 리턴값 대입
        if( result ){   System.out.println("수정성공"); } else{   System.out.println("수정실패"); }//result 값이 조건인 if문 생성
    } // m e

    // 3. 회원탈퇴 함수
    public boolean mDelete( ){ // 반환타입 boolean 메소드 생성
        System.out.print("[정말 탈퇴할까요?]"); // 출력
        System.out.print("확인 비밀번호 : ");
        String confirmPwd = scan.next(); // 입력값 대입
        boolean result  = MemberController.mcontrol.mDelete( confirmPwd ); //boolean 타입 result에 MemberController 클래스 입력받은 값을 매개변수로 받는 mDelete메소드 리턴값 대입
        if( result ){ System.out.println("탈퇴 성공"); return true; } // result 값이 조건인 if문 생성
        else{  System.out.println("비밀번호 불일치");   return false; }
    }

    // 4. 게시판(게시물전체출력) 함수
    public void bPrint( ) { }

} // class end







