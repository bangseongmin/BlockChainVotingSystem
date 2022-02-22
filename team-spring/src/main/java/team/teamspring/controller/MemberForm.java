package team.teamspring.controller;


import static team.teamspring.Encryption.Encry.Decryption;
import static team.teamspring.Encryption.Encry.Encryption;

class MemberForm {
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;

    private String name;

    public String getVote(){
        return vote;
    }

    public void setVote(String vote) throws Exception{
        this.vote = vote;
    }

    private String vote;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
