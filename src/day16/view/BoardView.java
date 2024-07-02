package day16.view; //view 클래스들이 있는 패키지

import day16.controller.BoardController;
import day16.controller.MemberController; // MemberController 클래스 호출
import day16.model.dao.BoardDao;
import day16.model.dto.BoardDto;
import day16.model.dto.MemberDto;         // MemberDto 클래스 호출
import day16.model.dto.ReplyDto;

import java.util.ArrayList;
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
    public void bPrint( ) {

        ArrayList<BoardDto> result=BoardController.getInstance().bPrint();
        System.out.println("번호\t조회수\t작성일\t\t\t제목");
        //리스트객체명.forEach(반복변수 ->{실행문;});
            // 리스트내 전체 dto를 하나씩 반복변수에 대입 반복
        result.forEach(dto ->{
            System.out.printf("%2d\t%2d\t%10s\t%10s\t%s\t%s \n",dto.getBno(),dto.getBview(),dto.getBdate(),dto.getMid(),dto.getBtitle(),dto.getBcontent());
        });
        System.out.print(" 0.글쓰기 1~:개별글조회: "); int ch =scan.nextInt();
        if(ch==0){bWrite();}
        else if(ch>=1){bView(ch);}
    }

    // 5. 게시물 쓰기 함수
    public void bWrite(){
        System.out.print("제목: "); scan.nextLine(); String bTitle=scan.nextLine();
        System.out.print("내용: "); String bContent=scan.nextLine();
        BoardDto boardDto=new BoardDto();
        boardDto.setBtitle(bTitle); boardDto.setBcontent(bContent);
        boolean result=BoardController.getInstance().bWrite(boardDto);
        if (result){
            System.out.println("글쓰기 성공");
        }else{
            System.out.println("글쓰기 실패");
        }

    }

    // 6. 게시물 개별 조회 함수
    public void bView(int bno){
        BoardDto result=BoardController.getInstance().bView(bno);
        if (result==null){System.out.println("존재하지 않는 게시물 입니다."); return;}
        System.out.println("제목: "+result.getBtitle());
        System.out.println("작성자: "+result.getMno());
        System.out.print("작성일: "+result.getBdate());
        System.out.println("\t조회수: "+result.getBview());
        System.out.println("내용: "+result.getBcontent());
        // ======== 댓글 출력 =========
        rPrint(bno);
        System.out.print("0.뒤로가기 1.삭제 2.수정 3.댓글쓰기 : "); int ch=scan.nextInt();
        if(ch==1){bDelete(bno);}
        else if(ch==2){bUpdate(bno);}
        else if(ch==3){rWrite(bno);}
        else if(ch==0){return;}
    }

    public void bDelete(int bno){
        boolean result=BoardController.getInstance().bDelete(bno);
        if (result){System.out.println("삭제 성공");}
        else {System.out.println("삭제 실패");}
    }

    public void bUpdate(int bno){
        boolean result=BoardController.getInstance().bUpdate(bno);

        if(result){
            scan.nextLine();
            String btitle=scan.nextLine();
            String bcontent=scan.nextLine();
            BoardDto boardDto=new BoardDto();
            boardDto.setBtitle(btitle); boardDto.setBcontent(bcontent); boardDto.setBno(bno);
            BoardController.getInstance().bUpdate2(boardDto);


            System.out.println("수정 성공");}
        else {System.out.println("작성자가 아닙니다.");}
    }

    public void rPrint(int bno){

        ArrayList<ReplyDto> result=BoardController.getInstance().rPrint(bno);
        result.forEach(replyDto1 -> {
            System.out.printf("%s %s %s\n",replyDto1.getRdate(),replyDto1.getMid(),replyDto1.getRcontent());
        });
    }

    public void rWrite(int bno){

        // - 만약에 코드 상황상 로그인 후 댓글쓰기가 아니였다면
            // 로그인상태를 확인 후 댓글 쓰기 진행
        if(!MemberController.mcontrol.loginState()){
            System.out.println("로그인 후 가능합니다.");
            return;
        }

        scan.nextLine();
        System.out.print("댓글 입력: ");
        // 위에서 next() 후 엔터 쳤을 때 scan객체에 엔터/개행 기록이 남아있기 때문에 nextLine() 인식해서 입력했다는걸로 간주
        // 해결받안: next() nextLine() 사이에 의미 없는 scan.nextLine(); 코드 작성
        String rcontent=scan.nextLine();
        boolean result=BoardController.getInstance().rWrite(bno,rcontent);
        if (result){
            System.out.println("댓글 입력 성공");
        }
        else{System.out.println("댓글 입력 실패");}
    }
} // class end







