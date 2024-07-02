package day16.model.dto;

public class ReplyDto {
    private String rcontent;
    private String rdate;
    private int mno;
    private int bno;
    private int rno;
    //+작성자 아이디
    private String mid;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public ReplyDto(){}
    public ReplyDto(String rcontent, String rdate, int mno, int bno, int rno) {
        this.rcontent = rcontent;
        this.rdate=rdate;
        this.mno = mno;
        this.bno = bno;
        this.rno = rno;
    }

    public String getRcontent() {
        return rcontent;
    }

    public void setRcontent(String rcontent) {
        this.rcontent = rcontent;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public int getMno() {
        return mno;
    }

    public void setMno(int mno) {
        this.mno = mno;
    }

    public int getBno() {
        return bno;
    }

    public void setBno(int bno) {
        this.bno = bno;
    }

    public int getRno() {
        return rno;
    }

    public void setRno(int rno) {
        this.rno = rno;
    }

    @Override
    public String toString() {
        return "ReplyDto{" +
                "rcontent='" + rcontent + '\'' +
                ", rdate=" + rdate +
                ", mno=" + mno +
                ", bno=" + bno +
                ", rno=" + rno +
                '}';
    }
}
