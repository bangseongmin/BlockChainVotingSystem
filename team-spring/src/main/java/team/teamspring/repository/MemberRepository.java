package team.teamspring.repository;


import team.teamspring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByPhone(String id);
    Optional<Member> findByName(String name);
    Optional<Member> findByVote(String vote);
    List<Member> findAll();
}
