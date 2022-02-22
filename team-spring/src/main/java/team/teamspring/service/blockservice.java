package team.teamspring.service;

import java.security.MessageDigest;
import java.util.List;

import team.teamspring.domain.block;

public class blockservice {


    //디지털서명  SHA256 알고리즘 이용
    public  String applySha256(String key){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");        // 형식 지정
            byte[] hashDT = digest.digest(key.getBytes("UTF-8"));
            StringBuffer Stringbuff = new StringBuffer();

            for(int i=0; i<hashDT.length;i++) {
                String hexcod = Integer.toHexString(0xff & hashDT[i]);

                if(hexcod.length()==1) Stringbuff.append('0');
                Stringbuff.append(hexcod);
            }

            return Stringbuff.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);

        }
    }

    //이전 블럭 해쉬값 생성
    public String calculateHash(block block) {
        String calculatehash = applySha256(
                block.getPreviousHash() +
                        Long.toString(block.getTimeStamp())+
                        block.getData() );
        return calculatehash;
    }

    //블록 생성자 data 이전해쉬 휴대폰 번호를 사용자 지정으로 가져오고
    // 현 해쉬값은 calculateHash() 메서드에서 생성후 지정
    public block block(String data, String previousHash,String idt) {
       block block =new block();
       block.setData(data);
       block.setPreviousHash(previousHash);
       block.setIdt(idt);
       return block;
    }

    //작업증명  중간에 데이터 위변조가있나 해쉬값 비교를통해 확인
    public static Boolean isChainValid(List<block> blockList) {
        block currentblock; //현재 블럭
        block previousblock; //이전블럭

        for(int i =1; i<blockList.size();i++){
            currentblock = blockList.get(i);
            previousblock = blockList.get(i-1);

            if(!currentblock.hash.equals(currentblock.hash)) {
                System.out.println("일치하지않음");
                return false;
            }
            if(!previousblock.hash.equals(currentblock.previousHash)) {
                System.out.println("전의블럭해쉬값과 일차하지않음");
                return false;

            }
        }
        return true;

    }
}
