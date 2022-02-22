package team.teamspring.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import team.teamspring.domain.Member;
import team.teamspring.repository.MemoryMemberRepository;
import team.teamspring.service.MemberService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberControllerTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository;
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void List(Model model) {
        //Given
        Member member1 = new Member();
        Member member2 = new Member();
        Member member3 = new Member();

        member1.setName("hello");
        member1.setPhone("1234");
        member1.setVote("1");
        member2.setName("asdfsf");
        member2.setPhone("2323");
        member2.setVote("1");
        member3.setName("dsfsdf");
        member3.setPhone("sdff");
        member3.setVote("1");
        //When
        String savePhone = memberService.join(member1);
        Member findMember = memberService.findOne(member1.getPhone()).get();
        assertEquals(member1.getPhone(), findMember.getPhone());

        savePhone = memberService.join(member2);
        findMember = memberService.findOne(member2.getPhone()).get();
        assertEquals(member2.getPhone(), findMember.getPhone());
        savePhone = memberService.join(member3);
        findMember = memberService.findOne(member3.getPhone()).get();
        assertEquals(member3.getPhone(), findMember.getPhone());

        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        System.out.println("List 크기 확인 : " + members.size());
        for (Member member: members)
        {
            System.out.println(member.getName());
            System.out.println(member.getPhone());
            System.out.println(member.getVote());
        }

    }
}