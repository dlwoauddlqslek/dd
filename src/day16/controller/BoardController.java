package day16.controller;

import day16.model.dao.BoardDao;
import day16.model.dto.BoardDto;
import day16.model.dto.ReplyDto;

import java.util.ArrayList;

public class BoardController {

    // =============== 싱글톤 만들기 =================
    // 싱글톤: 프로그램내 해당 클래스의 하나의 객체만 갖는 패턴
        // 1. static 변수에 해당 클래스의 객체 생성해서 대입, private
    private static BoardController bControl=new BoardController();
        // 2. 해당 클래스의 생성자를 private 한다. 다른 클래스에서 new 사용하지 못하게
    private BoardController(){}
        // 3. 해당 객체(싱글톤)를 외부로부터 접근할 수 있도록 간접(추후에 유효성검사) 호출 메소드
    public static BoardController getInstance(){
        return bControl;
    }

    public ArrayList<BoardDto> bPrint(){
        return BoardDao.getInstance().bPrint();
    }

    public boolean bWrite(BoardDto boardDto){
        boardDto.setMno(MemberController.mcontrol.loginMno);
        return BoardDao.getInstance().bWrite(boardDto);
    }

    public BoardDto bView(int bno){

        // 해당 게시물 조회수 처리
        BoardDao.getInstance().viewIncrease(bno);
        return BoardDao.getInstance().bView(bno);
    }

    public boolean bDelete(int bno){
        int mno=MemberController.mcontrol.loginMno;
        return BoardDao.getInstance().bDelete(bno,mno);
    }

    public boolean bUpdate(int bno){
        int mno=MemberController.mcontrol.loginMno;

        return BoardDao.getInstance().bUpdate(bno,mno);
    }

    public boolean bUpdate2(BoardDto boardDto){
        return BoardDao.getInstance().bUpdate2(boardDto);
    }

    public ArrayList<ReplyDto> rPrint(int bno){
        return BoardDao.getInstance().rPrint(bno);
    }

    public boolean rWrite(int bno,String rcontent){
        int mno=MemberController.mcontrol.loginMno;
        return BoardDao.getInstance().rWrite(bno,mno,rcontent);
    }
}
