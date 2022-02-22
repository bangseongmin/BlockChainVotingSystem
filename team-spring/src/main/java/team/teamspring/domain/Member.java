package team.teamspring.domain;


import javax.persistence.*;

@Entity
public class Member {
    @Id
    private String phone;

    @Column(name = "username")
    private String name;

    @Column(name = "vote")
    private String vote;

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

