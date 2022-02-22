package team.teamspring.controller;


import org.apache.velocity.util.ArrayListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.teamspring.domain.Member;
import team.teamspring.service.MemberService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import  team.teamspring.domain.block;
import team.teamspring.service.blockservice;

import static team.teamspring.Encryption.Encry.Decryption;
import static team.teamspring.Encryption.Encry.Encryption;


@Controller
public class MemberController {
    private final MemberService memberService;
    private MemberService certificationService;


    private blockservice pr;
    public static String numStr;
    public List<block> block =new ArrayList<block>();

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @GetMapping(value = "/members/List")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();     // 모든 멤버가 다옴
        model.addAttribute("members", members);

        return "members/memberList";
    }

    @GetMapping(value = "/members/result")
    public String result(Model model) {

        List<Member> members = memberService.findMembers();     // 모든 멤버가 다옴
        model.addAttribute("members", members);

        return "members/result";
    }

    @ResponseBody
    @PostMapping(value = "/members/in")
    public String in(MemberForm form) {
        int co=0;
        for (block bl: block) {
            if(bl.getIdt().equals(form.getPhone())) {
                co = 1;
            }
        }
        if(co==0) {
            Member member = new Member();

            member.setPhone(form.getPhone());
            String phone = member.getPhone();

            Random rand = new Random();
            numStr = "";
            for (int i = 0; i < 4; i++) {
                String ran = Integer.toString(rand.nextInt(10));
                numStr += ran;
            }

            System.out.println("수신자 번호 : " + phone);

            System.out.println("인증번호 : " + numStr);
            //memberService.certifiedPhoneNumber(phone, numStr);
            return "1";
        }else{
            return "0";
        }
    }


    @ResponseBody
    @PostMapping(value = "/members/out")
    public String out(String in) {

        if (numStr.equals(in)) {
            System.out.println("인증번호 : " + numStr);
            System.out.println("입력값 : " + in);
            System.out.println("TRUE");

            return "1"; // 성공
        } else {
            System.out.println("인증번호 : " + numStr);
            System.out.println("입력값 : " + in);

            System.out.println("FALSE");

            return "0"; //실패
        }
    }

    @GetMapping(value = "/members/to") //투표결과 view 로보내기
    public void to(Model model) {
        int c1=0;
        int c2=0;
        int c3=0;
        System.out.println("블록체인 사이즈 : "+block.size());
        //전체 데이터 가져올
        for (block bl:block) {
            if(bl.getData().equals("1"))
            {
                c1++;
            }else if(bl.getData().equals("2")){
                c2++;
            }else
                c3++;
            //System.out.println(bl.getData());
        }

        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);
        model.addAttribute("c1",c1);
        model.addAttribute("c2",c2 );
        model.addAttribute("c3",c3 );
    }



    @PostMapping(value = "/members/new")
    public String create(MemberForm form) throws Exception {
        pr= new blockservice();
        Member member = new Member();
        member.setName(form.getName());
        member.setPhone(form.getPhone());
        member.setVote(Encryption(form.getVote()));

        if(block.isEmpty()){
            block block1 = new block(form.getVote(),"0",form.getPhone());
            block1.setPreviousHash(pr.calculateHash(block1));
            block.add(block1);

        }
        else
        {
            block block1 = new block(form.getVote(),block.get(block.size()-1).hash,form.getPhone());
            block1.setPreviousHash(pr.calculateHash(block1));
            block.add(block1);
        }

        memberService.join(member);
        member.setVote(Decryption(form.getVote()));
        System.out.println("저장성공");

        return "redirect:/";

    }

}
