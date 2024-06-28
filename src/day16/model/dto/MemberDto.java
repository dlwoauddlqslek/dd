package day16.model.dto;

import day16.model.dao.MemberDao;

public class MemberDto { // 이동객체

    // 1. 멤버변수 <---> DB 테이블 필드와 동일 , private 권장
    private String mid;
    private String mpwd;
    private String mname;
    private String mphone;
    private String mdate;
    private int mno;

    // 2. 생성자 : 기본생성자,풀생성자
    public MemberDto(){} // 기본생성자
    public MemberDto(String mid, String mpwd, String mname, String mphone, String mdate, int mno) { // 풀생성자
        this.mid = mid; // this는 멤버변수와 매개변수 구별위해 사용
        this.mpwd = mpwd;
        this.mname = mname;
        this.mphone = mphone;
        this.mdate = mdate;
        this.mno = mno;
    }
    // --- 회원가입용 생성자
    public MemberDto(String mid, String mpwd, String mname, String mphone) { // 오버로딩은 매개변수 타입,순서,개수가 달라야 가능
        this.mid = mid;
        this.mpwd = mpwd;
        this.mname = mname;
        this.mphone = mphone;
    }
    // 3. 메소드 : 1. getter/setter 2.toString()

    @Override // 함수 재정의 최우선으로 사용됨, 주소값 대신 진짜값을 알기위해 사용
    public String toString() {
        return "MemberDto{" +
                "mid='" + mid + '\'' +
                ", mpwd='" + mpwd + '\'' +
                ", mname='" + mname + '\'' +
                ", mphone='" + mphone + '\'' +
                ", mdate='" + mdate + '\'' +
                ", mno=" + mno +
                '}';
    }
    // 멤버변수들이 private 상태이기 때문에 접근하기 위해 getter,setter 함수 사용, get은 멤버변수값을 가져오고 set은 멤버변수값에 새로운 값 대입
    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMpwd() {
        return mpwd;
    }

    public void setMpwd(String mpwd) {
        this.mpwd = mpwd;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public String getMdate() {
        return mdate;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    public int getMno() {
        return mno;
    }

    public void setMno(int mno) {
        this.mno = mno;
    }
}
