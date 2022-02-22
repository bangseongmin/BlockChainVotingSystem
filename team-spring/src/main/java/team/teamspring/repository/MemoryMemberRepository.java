package team.teamspring.repository;


import team.teamspring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {
    private static Map<String, Member> store = new HashMap<>();

    @Override
    public Member save(Member member) {
        member.setPhone(member.getPhone());
        store.put(member.getPhone(), member);
        return member;
    }

    @Override
    public Optional<Member> findByPhone(String phone) {
        return Optional.ofNullable(store.get(phone));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public Optional<Member> findByVote(String vote) {
        return store.values().stream()
                .filter(member -> member.getVote().equals(vote))
                .findAny();
    }

    public void clearStore() {
        store.clear();
    }

}