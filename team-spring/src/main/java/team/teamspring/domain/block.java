package team.teamspring.domain;


import team.teamspring.service.blockservice;

import java.util.Date;

public class block {
    public String hash; // 현재블럭 해쉬값
    public String previousHash; // 이전 해쉬값
    private String data; //현재블럭 데이터
    private long timeStamp; //타임스탬프
    private String idt; //투표자ID(주민번호 중복방지용)

    public block() {

    }

    public String getHash() {
        return hash;
    }

    public block(String data, String previousHash,String idt) { //블록 생성자 data 이전해쉬 주민번호는 사용자지정으로 가져오고 현해쉬값은 calculateHash() 메서드에서 생성후 지정
        blockservice pr = null;
        this.data =data;
        this.previousHash =previousHash; //이전해쉬
        this.timeStamp =new Date().getTime();
        // this.hash = pr.calculateHash(this); //현재해쉬 calculateHash() 메서드에서 계산 블록이 2개이상일시 이전블록과 타임스탬프의 계산값이필요
        this.idt =idt;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getIdt() {
        return idt;
    }

    public void setIdt(String idt) {
        this.idt = idt;
    }
}
