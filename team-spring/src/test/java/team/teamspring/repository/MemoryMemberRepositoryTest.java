package team.teamspring.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import team.teamspring.domain.Member;

import java.util.List;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        repository.save(member);

        //then
        Member result = repository.findByPhone(member.getPhone()).get();
        Assertions.assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);
        //when
        Member result = repository.findByName("spring1").get();
        //then
        Assertions.assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        //given
        Member member1 = new Member();
        member1.setName("ssss");
        member1.setPhone("1234");
        member1.setVote("1");
        repository.save(member1);
        Member member2 = new Member();
        member1.setName("222");
        member1.setPhone("2222");
        member1.setVote("1");
        repository.save(member2);

        //when
        List<Member> result = repository.findAll();

        //then
        Assertions.assertThat(result.size()).isEqualTo(2);

    }
}