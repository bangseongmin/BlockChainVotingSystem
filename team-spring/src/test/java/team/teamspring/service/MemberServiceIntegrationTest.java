package team.teamspring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.teamspring.domain.Member;
import team.teamspring.repository.MemberRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setName("kkk");
        member.setPhone("22222");
        member.setVote("1");

        //When
        String savePhone = memberService.join(member);
        //Then
        Member findMember = memberRepository.findByPhone(savePhone).get();
        assertEquals(member.getPhone(), findMember.getPhone());

    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");
        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    public void 출력(Member member) throws Exception {
        //Given
        member.setName("kkk");
        member.setPhone("1020200");
        member.setVote("1");

        //When
        String savePhone = memberService.join(member);
        //Then
        Member findMember = memberRepository.findByPhone(savePhone).get();
        assertEquals(member.getName(), findMember.getName());
    }
}