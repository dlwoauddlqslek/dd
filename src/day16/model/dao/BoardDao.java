package day16.model.dao;

import day16.model.dto.BoardDto;
import day16.model.dto.ReplyDto;

import java.security.PublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class BoardDao {



    // --- 싱글톤
    private static BoardDao bDao=new BoardDao();
    private BoardDao(){
        try {// -db연동
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/day06","root","1234"
            );
        }catch (Exception e){System.out.println(e);}
    }
    public static BoardDao getInstance(){return bDao;}

    // --- JDBC 인터페이스
    Connection conn; PreparedStatement ps; ResultSet rs;

    // 4. 게시판(게시물전체출력) 함수: 매개변수x, 리턴값: 여러개 게시물 -> 여러개 레코드 -> 여러개 DTO
    public ArrayList<BoardDto> bPrint(){
        // --여러개 DTO 담을 리스트 선언
        ArrayList<BoardDto> list=new ArrayList<>();
        try{
            String sql="select * from board inner join member on board.mno=member.mno";
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                // rs.next(): 다음 레코드로 이동, 존재하면 true, 없으면 false
                //레코드 1개당 Dto 1개
                // rs.getString(
                String btitle=rs.getString("btitle");
                String bcontent=rs.getString("bcontent");
                String bdate=rs.getString("bdate");
                int bview=rs.getInt("bview");
                int mno=rs.getInt("mno");
                int bno=rs.getInt("bno");
                //Dto 만들기
                BoardDto boardDto=new BoardDto(btitle,bcontent,bdate,bview,mno,bno);
                boardDto.setMid(rs.getString("mid"));
                list.add(boardDto);
            }
        }catch (Exception e){System.out.println(e);}
        return list; // 여러개 담긴 Dto의 리스트를 반환
    }

    public boolean bWrite(BoardDto boardDto){
        try{
            String sql="INSERT INTO board( btitle , bcontent , mno ) VALUES( ? , ? , ? );";
            ps=conn.prepareStatement(sql);
            ps.setString(1,boardDto.getBtitle());
            ps.setString(2,boardDto.getBcontent());
            ps.setInt(3,boardDto.getMno());
            int count =ps.executeUpdate();
            if(count==1){
                return true;
            }
        }catch (Exception e){System.out.println(e);}
        return false;
    }

    public BoardDto bView(int bno){

        try{
            String sql="select * from board where bno=?;";
            ps=conn.prepareStatement(sql);
            ps.setInt(1,bno);
            rs=ps.executeQuery();

            if (rs.next()){
                BoardDto boardDto=new BoardDto(rs.getString("btitle"),rs.getString("bcontent"),
                        rs.getString("bdate"),rs.getInt("bview"),rs.getInt("mno"),rs.getInt("bno"));
                return boardDto;
            }
        }catch (Exception e){System.out.println(e);}
        return null;
    }

    public boolean bDelete(int bno,int mno){
        try {
            String sql = "delete from board where bno=? and mno=?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, bno);
            ps.setInt(2,mno);
            int count=ps.executeUpdate();
            if(count==1) return true;
        }catch (Exception e){System.out.println(e);}
        return false;
    }

    public boolean bUpdate(int bno, int mno){
        try{

            String sql="select * from board where bno=?;";
            ps=conn.prepareStatement(sql);
            ps.setInt(1,bno);
            rs=ps.executeQuery();
            rs.next();
            System.out.println(rs+"1");
            if(rs.getInt("mno")==mno){
                System.out.println(rs.getInt("mno"));
                System.out.println(mno);

                return true;
            }

        }catch (Exception e){System.out.println(e);}
        return false;
    }

    public boolean bUpdate2(BoardDto boardDto){
        try {
            String sql="update board set btitle=?, bcontent=? where bno=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,boardDto.getBtitle());
            ps.setString(2,boardDto.getBcontent());
            ps.setInt(3,boardDto.getBno());
            int count=ps.executeUpdate();
            if(count==1){return true;}

        }catch (Exception e){System.out.println(e);}
        return  false;
    }

    public ArrayList<ReplyDto> rPrint(int bno){
        ArrayList<ReplyDto> list=new ArrayList<>();
        try{
            String sql="select * from reply r inner join member m on r.mno=m.mno where r.bno=?;";
            ps=conn.prepareStatement(sql);
            ps.setInt(1,bno);
            rs=ps.executeQuery();
            while(rs.next()){
                ReplyDto replyDto=new ReplyDto(rs.getString("rcontent"),rs.getString("rdate"),rs.getInt("mno"),rs.getInt("bno"),rs.getInt("rno"));
                replyDto.setMid(rs.getString("mid"));
                list.add(replyDto);
            }

        }catch (Exception e){System.out.println(e);}
        return list;
    }

    public boolean rWrite(int bno,int mno,String rcontent){
        try{
            String sql="INSERT INTO reply( rcontent , mno , bno ) VALUES( ? , ? , ? );";
            ps=conn.prepareStatement(sql);
            ps.setString(1,rcontent);
            ps.setInt(2,mno);
            ps.setInt(3,bno);
            int count=ps.executeUpdate();
            if (count==1){
                return true;
            }
        }catch (Exception e){System.out.println(e);}
        return false;
    }

    // 조회수 증가 처리
    public boolean viewIncrease(int bno){
        try{
            String sql="update board set bview=bview+1 where bno=?";
            ps=conn.prepareStatement(sql);
            ps.setInt(1,bno);
            int count=ps.executeUpdate();
            if(count==1){return true;}
        }catch (Exception e){System.out.println(e);}
        return false;
    }

}
