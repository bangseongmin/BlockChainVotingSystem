package team.teamspring.service;


import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import team.teamspring.domain.Member;
import team.teamspring.repository.MemberRepository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    // 회원가입
    public String join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getPhone();
    }

    //중복 회원 검증
    private void validateDuplicateMember(Member member) {
        memberRepository.findByPhone(member.getPhone())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 투표한 고객입니다.");
                });
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 휴대폰을 통한 멤버 조회
    public Optional<Member> findOne(String memberPhone) {
        return memberRepository.findByPhone(memberPhone);
    }

    // 메세지 인증
    public void certifiedPhoneNumber(String phoneNumber, String cerNum) {

        String api_key = "NCSD9WPR5LQQDFVB";
        String api_secret = "VBQZ22MS9EPTJB5YWRP37ASC9G9BYWEM";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber);    // 수신전화번호
        params.put("from", "01077060986");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "휴대폰인증 테스트 메시지 : 인증번호는" + "["+cerNum+"]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

    }
}