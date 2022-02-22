package team.teamspring.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import team.teamspring.domain.Member;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, String>, MemberRepository {

    @Override
    Optional<Member> findByPhone(String phone);

    @Override
    Optional<Member> findByName(String name);

    @Override
    Optional<Member> findByVote(String vote);
}