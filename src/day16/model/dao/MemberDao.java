package day16.model.dao; //Dao 클래스들이 잇는 패키지

import day16.model.dto.MemberDto; // MemberDto 클래스 호출

import java.sql.Connection;         //sql 패키지에서 Connection 인터페이스 호출
import java.sql.DriverManager;      //sql 패키지에서 DriverManager 클래스 호출
import java.sql.PreparedStatement;  //sql 패키지에서 PreparedStatement 인터페이스 호출
import java.sql.ResultSet;          //sql 패키지에서 ResultSet 인터페이스 호출

public class MemberDao { // class s
    // 0.
    public static MemberDao mdao  = new MemberDao(); // MemberDao 클르스를 이용해 mdao 멤버변수 객체에 MemberDao 인스턴스 주소값 대입
    // 0. DB연동
    // - JDBC 인터페이스 변수  3개
    Connection conn; PreparedStatement ps; ResultSet rs; //각 인터페이스 타입들의 변수 생성
    // - 생성자에 연동 코드
    MemberDao(){ // 생성자 s
        try{ Class.forName("com.mysql.cj.jdbc.Driver"); // 예외처리, DB와 연동
            conn = DriverManager.getConnection( //주소, 사용자, 비밀번호 대입
                    "jdbc:mysql://localhost:3306/day06",
                    "root","1234"
            );
        }catch (Exception e){  System.out.println(">>연동실패"+e);}//예외타입 e변수에 대입,e 출력
    } // 생성자 e

    // 1. 회원가입 화면 함수
    public boolean signup( MemberDto memberDto ){//memberDto 매개변수로 받는 boolean타입 signUp 메소드
        try {//예외처리
            // 1. SQL 작성한다.
            String sql = "INSERT INTO member( mid , mpwd , mname , mphone ) VALUES( ? , ? , ? , ? )";
            // 2. SQL 기재한다
            ps = conn.prepareStatement(sql);
            // 2. SQL 문법의 ?(와일드카드) 매개변수 기재한다
            ps.setString(1, memberDto.getMid() );        // .setString( ?순서번호 , 값또는변수명 )
            ps.setString(2, memberDto.getMpwd() );
            ps.setString(3, memberDto.getMname() );
            ps.setString(4, memberDto.getMphone());
            // 3. SQL 실행한다. // 4. SQL 실행결과
            int count = ps.executeUpdate(); // executeUpdate() 등록된 레코드수 반환 ,
            if (count == 1) {  return true;   } //  1개 레코드 등록되면 성공
        }catch (Exception e ){   System.out.println(e);  }
        return false; // 5. 메소드 반환
    }// m end

    // 2. 로그인 함수 : 로그인 성공시 회원번호 반환 , 로그인 실패하면 0 반환
    public int login(MemberDto memberDto ){//memberDto를 매개변수로 받는 int타입 login함수
        try {
            String sql = "SELECT * FROM member where mid = ? and mpwd = ?";// 1. SQL 작성한다.
            ps = conn.prepareStatement(sql); // 2. DB연동객체에 SQL를 기재
            ps.setString(1, memberDto.getMid() );    // 3. SQL의 ? 매개변수에 변수값 대입
            ps.setString(2, memberDto.getMpwd());
            // 4. 실행
            rs = ps.executeQuery();  // 5. 쿼리 실행후 결과를 rs로 받는다.
            // 6. 다음 레코드 : 로그인성고시 레코드1개 , 로그인실패시 레코드 0개
            if (rs.next()) {
                return rs.getInt("mno");
            } // 다음 레코드가 1개라도 존재하면 회원번호 반환
        }catch (Exception e ){  System.out.println(e); }
        return 0; // 로그인 실패
    } // m end

    // 3. 아이디찾기  함수
    public String findId( MemberDto memberDto ){ // memberDto를 매개변수로 받는 String타입 findId함수
        try{
            String sql = "SELECT * FROM member where mname = ? and mphone = ?"; // sql 작성
            ps = conn.prepareStatement(sql);                                    // DB연동객체에 sql 기재
            ps.setString( 1 , memberDto.getMname() );              // sql ? 매개변수에 변수값 대입
            ps.setString( 2 , memberDto.getMphone() );
            rs = ps.executeQuery(); //쿼리 실행후 결과를 rs로 받는다.
            if( rs.next() ){//조건에 맞는 레코드가 존재하면
                String findId = rs.getString("mid"); // rs.getString("필드명") : 현재 레코드의 해당 필드명에 필드값 반환
                return  findId; // 필드값 리턴
            }
        }catch (Exception e ){System.out.println(e); }
        return null; // null 리턴
    } // m end

    // 4. 비밀번호찾기  함수
    public String findPwd( MemberDto memberDto ){ // memberDto를 매개변수로 받고 반환타입이 String인 findPwd 메소드
        try{
            String sql = "SELECT * FROM member where mid = ? and mphone = ?;"; // sql문 작성
            ps = conn.prepareStatement( sql );  // DB연동객체에 sql 기재
            ps.setString( 1 , memberDto.getMid() ); //sql ? 매개변수에 변수값 대입
            ps.setString( 2 , memberDto.getMphone());
            rs = ps.executeQuery(); // 쿼리 실행 후 결과를 rs로 받는다.
            if( rs.next() ){ //조건에 맞는 레코드가 존재하면
                String findpwd = rs.getString("mpwd"); //findpwd에 조건에 맞는 레코드 mpwd 필드값 대입
                return findpwd; //findpwd값 리턴
            }
        }catch (Exception e ){System.out.println(e); }
        return null;
    } // m end
    // 5. 회원탈퇴 함수
    public boolean mDelete( String confirmPwd , int loginMno ){ //String타입, int타입 변수를 매개변수로 받고 반환타입이 boolean인 mDelete메소드
        try{ String sql = "delete from member where mno = ? and mpwd = ?"; //sql문 작성
            ps = conn.prepareStatement(sql);    ps.setInt( 1  , loginMno );     ps.setString( 2 , confirmPwd ); // sql ? 매개변수에 변수값 대입
            int count = ps.executeUpdate();     if( count == 1 ){ return true; } // 레코드 수 반환해서 count에 대입 count 1이면 true 리턴
        }catch (Exception e ){ System.out.println(e); } return false; // 예외 발생하면 예외타입 e에 저장 false 리턴
    }
    // 6. 회원수정 함수
    public boolean mUpdate( MemberDto memberDto ){ //memberDto를 매개변수로 받고 반환타입이 boolean인 mUpdate메소드
        try{ String sql = "update member " + //sql문 작성
                " set mname = ? , mphone = ? " +
                " where mno = ?";
        ps = conn.prepareStatement(sql);
        ps.setString( 1 , memberDto.getMname() );
        ps.setString( 2 , memberDto.getMphone() );
        ps.setInt( 3 , memberDto.getMno() );
        int count = ps.executeUpdate();
        if( count == 1 ){ return true;}
        }catch( Exception e){System.out.println( e );}
        return false;
    }
}
















